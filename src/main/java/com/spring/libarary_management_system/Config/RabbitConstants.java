package com.spring.libarary_management_system.Config;

public class RabbitConstants {

    // Exchange
    public static final String MAIL_EXCHANGE = "mail.exchange";


    // Queues
    public static final String MAIL_RESET_QUEUE = "mail.reset.queue";
    public static final String MAIL_CODE_QUEUE = "mail.code.queue";

    // Routing Keys
    public static final String MAIL_PASSWORD_RESET_KEY = "mail.password.reset";
    public static final String MAIL_CODE_GENERATE_KEY = "mail.code.generate";
    public static final String WELCOME_KEY = "mail.welcome.message";


}