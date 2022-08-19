package mainBot;

import Services.Service;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@AllArgsConstructor
@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private BotFunctionsForCurrencies functionsForCurrencies;


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
        if(callbackQuery.getData().contains("Update")){
            CallBackQueryCurrencyUpdate(callbackQuery);
        } else if(callbackQuery.getData().contains("Currency")){
            CallBackQueryCurrency(callbackQuery);
        }
    }
    private void CallBackQueryCurrencyUpdate(CallbackQuery callbackQuery){
        Optional<EditMessageText> messageText = Optional.ofNullable(functionsForCurrencies.CallBackQueryCurrencyUpdate(callbackQuery));
        if(messageText.isPresent()) {
            try {
                execute(messageText.get());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void CallBackQueryCurrency(CallbackQuery callbackQuery){
        try {
            execute(functionsForCurrencies.CallBackQueryCurrency(callbackQuery));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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
        execute(
                functionsForCurrencies.chooseCurrency(message)
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
