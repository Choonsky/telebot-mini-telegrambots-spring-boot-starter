package com.nemirovsky.telebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TelegramBot {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TelegramBotApi.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
