package com.learn.library_management.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.learn.library_management.rabbitconfig.RabbitConstants.*;

@Configuration
public class MailRabbitConfig {

	@Bean
	public TopicExchange mailExchange() {
		return new TopicExchange(MAIL_EXCHANGE);
	}

	@Bean
	public Queue mailResetQueue() {
		return new Queue(MAIL_RESET_QUEUE, true);
	}

	@Bean
	public Queue mailCodeQueue() {
		return new Queue(MAIL_CODE_QUEUE, true);
	}

	@Bean
	public Queue welcomeQueue() {
		return new Queue(WELCOME_QUEUE, true);
	}
	
	@Bean
	public Queue mailBorrowQueue() { return new Queue(RabbitConstants.MAIL_BORROW_QUEUE, true); }

	@Bean
	public Queue mailDueReminderQueue() { return new Queue(RabbitConstants.MAIL_DUE_REMINDER_QUEUE, true); }

	@Bean
	public Queue mailOverdueQueue() { return new Queue(RabbitConstants.MAIL_OVERDUE_QUEUE, true); }


	@Bean
	public Binding passwordResetBinding(TopicExchange mailExchange, Queue mailResetQueue) {
		return BindingBuilder.bind(mailResetQueue).to(mailExchange).with(MAIL_PASSWORD_RESET_KEY);
	}

	@Bean
	public Binding codeGenerateBinding(TopicExchange mailExchange, Queue mailCodeQueue) {
		return BindingBuilder.bind(mailCodeQueue).to(mailExchange).with(MAIL_CODE_GENERATE_KEY);
	}

	@Bean
	public Binding welcomeBinding(TopicExchange mailExchange, Queue welcomeQueue) {
		return BindingBuilder.bind(welcomeQueue).to(mailExchange).with(WELCOME_KEY);
	}
	
	@Bean
	public Binding borrowBinding(TopicExchange mailExchange, Queue mailBorrowQueue) {
	    return BindingBuilder.bind(mailBorrowQueue).to(mailExchange).with(RabbitConstants.MAIL_BORROW_KEY);
	}

	@Bean
	public Binding dueReminderBinding(TopicExchange mailExchange, Queue mailDueReminderQueue) {
	    return BindingBuilder.bind(mailDueReminderQueue).to(mailExchange).with(RabbitConstants.MAIL_DUE_REMINDER_KEY);
	}

	@Bean
	public Binding overdueBinding(TopicExchange mailExchange, Queue mailOverdueQueue) {
	    return BindingBuilder.bind(mailOverdueQueue).to(mailExchange).with(RabbitConstants.MAIL_OVERDUE_KEY);
	}
}
