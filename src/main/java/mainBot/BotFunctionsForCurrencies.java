package mainBot;

import Services.Service;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Component
public class BotFunctionsForCurrencies {
    private List<List<String>> currencies;
    @Autowired
    private Service service;
    protected EditMessageText CallBackQueryCurrencyUpdate(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[2]);
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
                            .callbackData("Currency Update " + i)
                            .build()
            );
            if (name.contains("-")) {
                return
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("%s = %s \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                ;
            } else if(name.contains("Золото") || name.contains("Серебро")){
                return
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("%s 1 гр = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                ;
            }else {
                return
                        EditMessageText.builder()
                                .chatId(callbackQuery.getMessage().getChatId())
                                .messageId(callbackQuery.getMessage().getMessageId())
                                .text(String.format("1 %s = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                                .build()
                ;
            }
        } else{
            return null;
        }
    }

    @SneakyThrows
    protected SendMessage CallBackQueryCurrency(CallbackQuery callbackQuery){
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
                        .callbackData("Currency Update " + i)
                        .build()
        );
        if(name.contains("-")) {
            return
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("%s = %s \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            ;
        } else if(name.contains("Золото") || name.contains("Серебро")){
            return
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("%s 1 гр = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            ;
        }else {
            return
                    SendMessage.builder()
                            .chatId(callbackQuery.getMessage().getChatId())
                            .text(String.format("1 %s = %s Руб \nВремя получения последней цены в часовом поясе UTC по времени биржи: %s\nБиржа: %s", name, price, time, market))
                            .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                            .build()
            ;
        }
    }
    @SneakyThrows
    protected SendMessage chooseCurrency(Message message){
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
        return
                SendMessage.builder()
                        .text("Выберите валюту")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                        .build()
        ;
    }
}
