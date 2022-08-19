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
public class BotFunctionsForShares {
    private List<List<String>> shares;
    @Autowired
    private Service service;

    protected SendMessage CallBackQueryShare(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[1]);
        List<String> tekShare = shares.get(i);
        String name = tekShare.get(0);
        String time = tekShare.get(4);
        String market = tekShare.get(5);
        String price = tekShare.get(3);
        String curr = tekShare.get(6);
        String sector = tekShare.get(7);
        String type = tekShare.get(8);
        String country = tekShare.get(9);
        String lot = tekShare.get(10);
        update.add(
                InlineKeyboardButton.builder()
                        .text("Обновить♻️")
                        .callbackData("Share Update " + i)
                        .build()
        );
        return
                SendMessage.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(String.format("%s = %s %s \n" +
                                        "Время получения последней цены в часовом поясе UTC по времени биржи: %s\n" +
                                        "Биржа: %s\n" +
                                        "Сектор экономики: %s\n" +
                                        "Тип акции: %s\n" +
                                        "Наименование страны, в которой компания ведёт основной бизнес: %s\n" +
                                        "Лотность инструмента. Возможно совершение операций только на количества ценной бумаги, кратные лоту: %s\n",
                                name,
                                price,
                                curr,
                                time,
                                market,
                                sector,
                                type,
                                country,
                                lot
                                ))
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                        .build()
                ;
    }
    protected EditMessageText CallBackQueryShareUpdate(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[1]);
        List<String> tekShare = shares.get(i);
        String name = tekShare.get(0);
        String time = service.getTimeByFigies(List.of(tekShare.get(1))).get(0);
        String market = tekShare.get(5);
        String price = service.getPricesByFigies(List.of(tekShare.get(1))).get(0);
        String curr = tekShare.get(6);
        String sector = tekShare.get(7);
        String type = tekShare.get(8);
        String country = tekShare.get(9);
        String lot = tekShare.get(10);
        update.add(
                InlineKeyboardButton.builder()
                        .text("Обновить♻️")
                        .callbackData("Share Update " + i)
                        .build()
        );
        return
                EditMessageText.builder()
                        .chatId(callbackQuery.getMessage().getChatId())
                        .text(String.format("%s = %s %s \n" +
                                        "Время получения последней цены в часовом поясе UTC по времени биржи: %s\n" +
                                        "Биржа: %s\n" +
                                        "Сектор экономики: %s\n" +
                                        "Тип акции: %s\n" +
                                        "Наименование страны, в которой компания ведёт основной бизнес: %s\n" +
                                        "Лотность инструмента. Возможно совершение операций только на количества ценной бумаги, кратные лоту: %s\n",
                                name,
                                price,
                                curr,
                                time,
                                market,
                                sector,
                                type,
                                country,
                                lot
                        ))
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboardRow(update).build())
                        .build()
                ;
    }

    @SneakyThrows
    protected SendMessage chooseShare(Message message){
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        this.shares = service.getAllNotEmptyShares();
        int len = shares.size();
        for (int i = 0;i < len;i++) {
            list.add(
                    List.of(InlineKeyboardButton.builder()
                            .text(shares.get(i).get(0))
                            .callbackData("Share " + i)
                            .build())
            );
        }
        return
                SendMessage.builder()
                        .text("Выберите акцию")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                        .build()
                ;
    }
}
