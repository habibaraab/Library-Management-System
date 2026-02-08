package com.learn.library_management.rabbitconfig;

public class RabbitConstants {
	
    // Exchange
    public static final String MAIL_EXCHANGE = "mail.exchange";
    

    
    // Queues 
    public static final String MAIL_RESET_QUEUE = "mail.reset.queue";
    public static final String MAIL_CODE_QUEUE = "mail.code.queue";
    public static final String WELCOME_QUEUE = "mail.welcome.queue";
    public static final String MAIL_BORROW_QUEUE = "mail.borrow.queue";
    public static final String MAIL_DUE_REMINDER_QUEUE = "mail.due.reminder.queue";
    public static final String MAIL_OVERDUE_QUEUE = "mail.overdue.queue";


    // Routing Keys
    public static final String MAIL_PASSWORD_RESET_KEY = "mail.password.reset";
    public static final String MAIL_CODE_GENERATE_KEY = "mail.code.generate";
    public static final String WELCOME_KEY = "mail.welcome.message";
    public static final String MAIL_BORROW_KEY = "mail.borrow.confirmation";
    public static final String MAIL_DUE_REMINDER_KEY = "mail.due.reminder";
    public static final String MAIL_OVERDUE_KEY = "mail.overdue.notice";

}