package mainBot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;


public class Bot extends TelegramLongPollingBot {

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
                }
            }
        }
    }
    @SneakyThrows
    protected void choosePromotion(Message message){
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        list.add(
                List.of(InlineKeyboardButton.builder()
                        .text("SBER")
                        .callbackData("11")
                        .build())
        );
        list.add(
                List.of(InlineKeyboardButton.builder()
                        .text("2")
                        .callbackData("11")
                        .build())
        );
        execute(
                SendMessage.builder()
                        .text("1234")
                        .chatId(message.getChatId().toString())
                        .replyMarkup(InlineKeyboardMarkup.builder().keyboard(list).build())
                        .build()
        );
    }
}
