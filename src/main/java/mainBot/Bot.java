package mainBot;

import Services.Service;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

@AllArgsConstructor
@Component
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private Service service;
    private List<List<String>> currencies;


    @Override
    public String getBotUsername() {
        return "@MyNewInvedtorBot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("botToken");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            handleMessage(update.getMessage());
        } else if(update.hasCallbackQuery()){
            CallbackQuery(update.getCallbackQuery());
        }
    }
    @SneakyThrows
    private void CallbackQuery(CallbackQuery callbackQuery){
        if(callbackQuery.getData().contains("Currency")){
            CallBackQueryCurrency(callbackQuery);
        } else if(callbackQuery.getData().contains("Update")){
            CallBackQueryCurrencyUpdate(callbackQuery);
        }
    }
    @SneakyThrows
    private void CallBackQueryCurrency(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[1]);
        var name = currencies.get(i).get(0);
        String time = currencies.get(i).get(4);
        String market = currencies.get(i).get(5);
        String price = currencies.get(i).get(3);
        update.add(
                InlineKeyboardButton.builder()
                        .text("Обновить♻️")
                        .callbackData("Update " + i)
                        .build()
        );
        if(name.contains("-")) {
            execute(
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("%s = %s \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            );
        } else if(name.contains("Золото") || name.contains("Серебро")){
            execute(
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("%s 1 гр = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            );
        }else {
            execute(
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("1 %s = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            );
        }
    }
    @SneakyThrows
    private void CallBackQueryCurrencyUpdate(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[1]);
        var price = service.getPricesByFigies(List.of(currencies.get(i).get(1))).get(0);
        var name = currencies.get(i).get(0);
        String time = service.getTimeByFigies(List.of(currencies.get(i).get(1))).get(0);
        String market = currencies.get(i).get(5);
        if(!time.equals(currencies.get(i).get(4))) {
            currencies.get(i).set(3, price);
            currencies.get(i).set(4, time);
            update.add(
                    InlineKeyboardButton.builder()
                            .text("Обновить♻️")
                            .callbackData("Update " + i)
                            .build()
            );
            if (name.contains("-")) {
                execute(
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("%s = %s \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                );
            } else if(name.contains("Золото") || name.contains("Серебро")){
                execute(
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("%s 1 гр = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                );
            }else {
                execute(
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("1 %s = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                );
            }
        }
    }
    @SneakyThrows
    private void handleMessage(Message message) {
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntities = message.getEntities().stream()
                    .filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntities.isPresent()) {
                String command = message.getText().substring(commandEntities.get().getOffset(), commandEntities.get().getLength());
                switch (command) {
                    case "/choose_a_promotion":
                        choosePromotion(message);
                    case "/choose_a_currency":
                        chooseCurrency(message);
                }
            }
        }
    }
    @SneakyThrows
    private void chooseCurrency(Message message){
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        this.currencies = service.getAllNotEmptyCurrencies();
        int len = currencies.size();
        for (int i = 0;i < len;i++) {
            list.add(
                    List.of(InlineKeyboardButton.builder()
                            .text(currencies.get(i).get(0))
                            .callbackData("Currency " + i)
                            .build())
            );
        }
        execute(
                SendMessage.builder()
                        .text("Выберите валюту")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                        .build()
        );
    }
    @SneakyThrows
    private void choosePromotion(Message message){
        execute(
                SendMessage.builder()
                        .text("1234")
                        .chatId(message.getChatId().toString())
                        .build()
        );
    }
}
