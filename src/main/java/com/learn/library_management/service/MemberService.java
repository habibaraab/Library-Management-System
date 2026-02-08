package com.learn.library_management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.learn.library_management.dto.MemberCreateDTO;
import com.learn.library_management.dto.MemberResponseDTO;
import com.learn.library_management.dto.MemberUpdateDTO;
import com.learn.library_management.entities.Member;
import com.learn.library_management.events.WelcomeEmailEvent;
import com.learn.library_management.exception.MemberAlreadyExistsException;
import com.learn.library_management.exception.MemberNotFoundException;
import com.learn.library_management.mapper.MemberMapper;
import com.learn.library_management.rabbitconfig.RabbitConstants;
import com.learn.library_management.repository.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Validated
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final RabbitTemplate rabbitTemplate;

	@Transactional
	@CachePut(value = "members", key = "#result.memberId")
	public MemberResponseDTO createMember(@Valid MemberCreateDTO dto) {
		memberRepository.findByEmail(dto.getEmail()).ifPresent(existing -> {
			throw new MemberAlreadyExistsException();
		});

		Member member = memberMapper.toEntity(dto);
		Member saved = memberRepository.save(member);
		
		WelcomeEmailEvent event = new WelcomeEmailEvent(
				member.getName(),
			    member.getEmail(),
			    "Member"
			);

			rabbitTemplate.convertAndSend(
			    RabbitConstants.MAIL_EXCHANGE,
			    RabbitConstants.WELCOME_KEY,
			    event
			);
		return memberMapper.toDTO(saved);
	}

	@Transactional
	@CachePut(value = "members", key = "#memberId")
	public MemberResponseDTO updateMember(Long memberId, @Valid MemberUpdateDTO dto) {
		Member existing = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

		memberRepository.findByEmail(dto.getEmail()).filter(m -> !m.getMemberId().equals(memberId)).ifPresent(m -> {
			throw new MemberAlreadyExistsException();
		});

		existing.setName(dto.getName());
		existing.setEmail(dto.getEmail());
		existing.setPhone(dto.getPhone());
		existing.setAddress(dto.getAddress());

		Member updated = memberRepository.save(existing);
		return memberMapper.toDTO(updated);
	}

	@Transactional
	@Cacheable(value = "members", key = "#memberId")
	public MemberResponseDTO findById(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
		return memberMapper.toDTO(member);
	}

	@Transactional
	@Cacheable(value = "members")
	public Page<MemberResponseDTO> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Member> membersPage = memberRepository.findAll(pageable);
		return membersPage.map(memberMapper::toDTO);
	}

	@Transactional
	@CacheEvict(value = "members", key = "#memberId")
	public void deleteMember(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());
		memberRepository.delete(member);
	}
	
	@Transactional
	@Cacheable(value = "members", key = "'title_' + #title")
	public List<MemberResponseDTO> searchMemberByName(String title) {
		return memberRepository.findByNameContainingIgnoreCase(title).stream().map(memberMapper::toDTO)
				.collect(Collectors.toList());
	}
}
