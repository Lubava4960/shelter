package com.pet.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotListener implements UpdatesListener {

    private final TelegramBot telegramBot;

    public TelegramBotListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    private void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        // В этом методе делаем обработку всех событий телеграмма. Например, сделаем ответ на сообщение /start

        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                var text = update.message().text();
                var chatId = update.message().chat().id();
                if ("/start".equals(text)) {
                    telegramBot.execute(new SendMessage(chatId, "Добро пожаловать в бот!"));
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
