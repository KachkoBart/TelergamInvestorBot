package BotLastPrices;

import Services.Service;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
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
    private final int sizeOnePageShares = 20;
    private int sizeShares;

    public SendMessage CallBackQueryShare(CallbackQuery callbackQuery){
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
    public EditMessageReplyMarkup CallBackQuerySharePage(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        if(data.contains("Current"))return null;
        int page = Integer.parseInt(data.split(" ")[2]);
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        for (int i = (page-1)*sizeOnePageShares; i < page*sizeOnePageShares && i < sizeShares;i++){
            list.add(
                    List.of(InlineKeyboardButton.builder()
                            .text(shares.get(i).get(0))
                            .callbackData("Share " + i)
                            .build())
            );
        }
        list.add(
                getPageOfShares(page)
        );
        return EditMessageReplyMarkup.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                .build();
    }
    private List<InlineKeyboardButton> getPageOfShares(int page){
        int sizePages;
        if(sizeShares%20 == 0){
            sizePages = sizeShares/20;
        } else{
            sizePages = (sizeShares/20)+1;
        }

        List<InlineKeyboardButton> list = List.of(
                InlineKeyboardButton.builder()
                        .text("1<<")
                        .callbackData("Share Page 1")
                        .build(),
                InlineKeyboardButton.builder()
                        .text((page-1) + "<")
                        .callbackData("Share Page " + (page-1))
                        .build(),
                InlineKeyboardButton.builder()
                        .text("•" + page + "•")
                        .callbackData("Share Page Current " + page)
                        .build(),
                InlineKeyboardButton.builder()
                        .text(">" + (page+1))
                        .callbackData("Share Page " + (page+1))
                        .build(),
                InlineKeyboardButton.builder()
                        .text(">>" + sizePages)
                        .callbackData("Share Page " + sizePages)
                        .build()
        );
        if(page == 1){
            return  List.of(
                    InlineKeyboardButton.builder()
                            .text("•1•")
                            .callbackData("Share Page Current 1")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(">2")
                            .callbackData("Share Page 2")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("3")
                            .callbackData("Share Page 3")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("4")
                            .callbackData("Share Page 4")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(">>" + sizePages)
                            .callbackData("Share Page " + sizePages)
                            .build()
            );
        } else if (page == 2) {
            return List.of(
                    InlineKeyboardButton.builder()
                            .text("1<")
                            .callbackData("Share Page 1")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("•2•")
                            .callbackData("Share Page Current 2")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(">3")
                            .callbackData("Share Page 3")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("4")
                            .callbackData("Share Page 4")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(">>" + sizePages)
                            .callbackData("Share Page " + sizePages)
                            .build()
            );
        } else if(page == sizePages-1){
            return List.of(
                    InlineKeyboardButton.builder()
                            .text("1<<")
                            .callbackData("Share Page 1")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(String.valueOf(page-2))
                            .callbackData("Share Page " + (page-2))
                            .build(),
                    InlineKeyboardButton.builder()
                            .text((page-1) + "<")
                            .callbackData("Share Page " + (page-1))
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("•" + page + "•")
                            .callbackData("Share Page Current " + page)
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(">" + sizePages)
                            .callbackData("Share Page " + sizePages)
                            .build()
            );
        } else if(page == sizePages){
            return List.of(
                    InlineKeyboardButton.builder()
                            .text("1<<")
                            .callbackData("Share Page 1")
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(String.valueOf(page-2))
                            .callbackData("Share Page " + (page-2))
                            .build(),
                    InlineKeyboardButton.builder()
                            .text(String.valueOf(page-2))
                            .callbackData("Share Page " + (page-2))
                            .build(),
                    InlineKeyboardButton.builder()
                            .text((page-1) + "<")
                            .callbackData("Share Page " + (page-1))
                            .build(),
                    InlineKeyboardButton.builder()
                            .text("•" + page + "•")
                            .callbackData("Share Page Current " + page)
                            .build()
            );
        }
        return list;
    }
    public EditMessageText CallBackQueryShareUpdate(CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        List<InlineKeyboardButton> update = new ArrayList<>();
        int i = Integer.parseInt(data.split(" ")[2]);
        List<String> tekShare = shares.get(i);
        String name = tekShare.get(0);
        String time = service.getTimeByFigies(List.of(tekShare.get(1))).get(0);
        if(tekShare.get(4).equals(time))return null;
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
                        .messageId(callbackQuery.getMessage().getMessageId())
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


    public SendMessage chooseShare(Message message){
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        this.shares = service.getAllNotEmptyShares();
        this.sizeShares = shares.size();
        for (int i = 0;i < sizeOnePageShares;i++) {
            list.add(
                    List.of(InlineKeyboardButton.builder()
                            .text(shares.get(i).get(0))
                            .callbackData("Share " + i)
                            .build())
            );
        }
        list.add(
                getPageOfShares(1)
        );
        return
                SendMessage.builder()
                        .text("Выберите акцию")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                        .build()
                ;
    }
}
