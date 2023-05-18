package com.nemirovsky.telebot.service;

import com.nemirovsky.telebot.data.TempCache;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.List;

@Component
public class Telebot extends TelegramLongPollingBot {

    String botName;
    String botToken;

    Telebot(@Value("${telegram.botToken}") String botToken, @Value("${telegram.botName}") String botName) {
        super(botToken);
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            var msg = update.getMessage();
            var chatId = String.valueOf(msg.getChatId());
            var userName = msg.getFrom().getUserName();
            TempCache.chatIds.put(userName, chatId);
            if (msg.getText().contains("users")) {
                try {
                    sendNotification(chatId, TempCache.chatIds.toString());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    var reply = msg.getText().contains("zhopa") ? "No zhopa in serious chat!" : "Regular bot response on " +
                            "\"" + msg.getText() + "\" text.";
                    sendNotification(chatId, reply);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    public void sendNotification(String chatId, String msg) throws TelegramApiException {
        var response = new SendMessage(chatId, msg);
        execute(response);
    }

    @PostConstruct
    public void start() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
