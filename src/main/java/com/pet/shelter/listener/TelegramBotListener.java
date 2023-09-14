package com.pet.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
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
                    SendMessage sendMessage = getSendMessage(chatId);
                    telegramBot.execute(sendMessage);

                    //  if ("Наш Лесной приют".equals(text)) {
               //     telegramBot.execute(new SendMessage(chatId, "В нашем приюте живут кошки , которые скучают и ждут своих хозяев"));
               // }
               // if ("Наш адрес".equals(text)) {
                //    telegramBot.execute(new SendMessage(chatId, "городской округ Истра, деревня Бодрово"));

                 }
                //sendMessage.replyMarkup(keyboard);


            }



}
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @NotNull
    private static SendMessage getSendMessage(Long chatId) {
        SendMessage sendMessage=new SendMessage(chatId, "Добро пожаловать в приют для кошек!");
        EditMessageReplyMarkup editMessageReplyMarkup= new EditMessageReplyMarkup("1");
        InlineKeyboardButton buttonText = new InlineKeyboardButton(" Наш  Лесной приют ");
        buttonText.callbackData(" Наш Лесной приют ");
        InlineKeyboardButton buttonAddress = new InlineKeyboardButton(" Наш адрес ");
        buttonAddress.callbackData(" Наш адрес ");
        Keyboard keyboard = new InlineKeyboardMarkup(buttonText, buttonAddress);
        sendMessage.replyMarkup(keyboard);
        return sendMessage;
    }

    private void sendMessage(Long chatId, String text) {
       SendMessage sendMessage = new SendMessage(chatId, text);
        SendResponse sendResponse = telegramBot.execute(sendMessage);
       // if (!sendResponse.isOk()) {
           // logger.error("Произошла ошибка!!! {}",sendResponse.description());
        }
    }




