package com.nemirovsky.telebot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TelegramBotTestController {

    @GetMapping("test/**")
    public String getTestMessage() {

        return "This is a Telegram Bot API service response test message!";
    }
}
