package com.pet.shelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
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
                Long chatId = update.message().chat().id();
                if ("/start".equals(text)) {
                    SendMessage sendMessage = new SendMessage(chatId, """
                            Привет. Это бот приюта для кошек.
                            """);

                    InlineKeyboardButton buttonText = new InlineKeyboardButton(" Наш  Лесной приют ");
                    buttonText.callbackData(" Наш Лесной приют ");
                    InlineKeyboardButton buttonAddress = new InlineKeyboardButton(" Наш адрес ");
                    buttonAddress.callbackData(" Наш адрес ");
                    Keyboard keyboard = new InlineKeyboardMarkup(buttonText, buttonAddress);
                    sendMessage.replyMarkup(keyboard);
                    telegramBot.execute(sendMessage);
                } else if (text != null) {

                    sendMessage(chatId, "Некорректный формат сообщения!");
                } else if (update.callbackQuery() != null && update.callbackQuery().data() != null) {
                    var data = update.callbackQuery().data();
                    if (data.equals("Наш Лесной приют ")) {
                        // ответ на кнопку "Наш лесной приют"

                        SendMessage sendMessage = new SendMessage(chatId, """
                                В нашем приюте живут кошки , которые скучают и ждут своих хозяев
                                """);
                        telegramBot.execute(sendMessage);

                    } else if (data.equals(" Наш адрес ")) {
                        // ответ на кнопку "Наш адрес"
                        SendMessage sendMessage = new SendMessage(chatId, "городской округ Истра, деревня Бодрово" );
                        telegramBot.execute(sendMessage);
                        }
                }
            }

        }


        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }




    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
}
    }









