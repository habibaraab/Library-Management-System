package com.learn.library_management.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.learn.library_management.config.Messages;
import com.learn.library_management.dto.BasicResponse;
import com.learn.library_management.dto.MemberCreateDTO;
import com.learn.library_management.dto.MemberResponseDTO;
import com.learn.library_management.dto.MemberUpdateDTO;
import com.learn.library_management.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Member Controller", description = "API for managing library members.")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Create a new member", description = "Allows admin to create a new library member")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> createMember(@RequestBody MemberCreateDTO dto) {
        MemberResponseDTO response = memberService.createMember(dto);
        return ResponseEntity.ok(new BasicResponse(Messages.ADD_MEMBER, response));
    }

    @Operation(summary = "Update member", description = "Allows admin to update member information")
    @PutMapping("/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> updateMember(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateDTO dto) {
        MemberResponseDTO response = memberService.updateMember(memberId, dto);
        return ResponseEntity.ok(new BasicResponse(Messages.UPDATE_MEMBER, response));
    }

    @Operation(summary = "Find member by ID", description = "Retrieve details of a specific member")
    @GetMapping("/{memberId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getMemberById(@PathVariable Long memberId) {
        MemberResponseDTO response = memberService.findById(memberId);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_MEMBER, response));
    }

    @Operation(summary = "Get all members", description = "Retrieve a paginated list of all members")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<MemberResponseDTO> response = memberService.findAll(page, size);
        return ResponseEntity.ok(new BasicResponse(Messages.FETCH_MEMBERS, response));
    }

    @Operation(summary = "Delete member", description = "Allows admin to delete a member by ID")
    @DeleteMapping("/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ResponseEntity<BasicResponse> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(new BasicResponse(Messages.DELETE_MEMBER));
    }
    
    @Operation(summary = "Search members by name", description = "Find members that contain a given name")
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BasicResponse> searchMemberByName(@RequestParam String title) {
        List<MemberResponseDTO> response = memberService.searchMemberByName(title);
        return ResponseEntity.ok(new BasicResponse(Messages.SEARCH_MEMBERS, response));
    }
}
