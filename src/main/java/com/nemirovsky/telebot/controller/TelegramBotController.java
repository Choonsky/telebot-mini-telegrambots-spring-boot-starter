package com.nemirovsky.telebot.controller;

import com.nemirovsky.telebot.data.TempCache;
import com.nemirovsky.telebot.service.Telebot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@RestController
@RequestMapping("/")
public class TelegramBotController {

    private final Telebot bot;

    @Autowired
    TelegramBotController(Telebot bot) {
        this.bot = bot;
    }

    @GetMapping
    public ResponseEntity<String> getMessage(@RequestParam(defaultValue = "unknown") String msgType,
                                             @RequestParam(defaultValue = "ALL") String userGroup) {

        TempCache.chatIds.forEach((k, v) -> {
            try {
                bot.sendNotification(v, "HAPPENED " + msgType + "!!!");
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        });

        return new ResponseEntity<>("Telegram Bot API successfully sent a message about: " + msgType
                + " for a group: " + userGroup + " of users!", HttpStatus.OK);
    }
}