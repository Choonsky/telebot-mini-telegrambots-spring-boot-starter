package com.nemirovsky.telebot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/**")
public class TelegramBotTestController {

    @GetMapping
    public String getTestMessage() {

        return "This is a Telegram Bot service response test message!";
    }
}
