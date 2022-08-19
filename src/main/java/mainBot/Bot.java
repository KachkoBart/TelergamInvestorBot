package mainBot;

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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@AllArgsConstructor
@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {
    @Autowired
    private BotFunctionsForCurrencies functionsForCurrencies;
    @Autowired
    private BotFunctionsForShares functionsForShares;


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
        String data = callbackQuery.getData();
        if(data.contains("Currency Update")){
            CallBackQueryCurrencyUpdate(callbackQuery);
        } else if(callbackQuery.getData().contains("Currency")){
            CallBackQueryCurrency(callbackQuery);
        } else if(data.contains("Share Update")){

        } else if(data.contains("Share")){
            CallBackQueryShare(callbackQuery);
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
    private void CallBackQueryShareUpdate(CallbackQuery callbackQuery){
        Optional<EditMessageText> messageText = Optional.ofNullable(functionsForShares.CallBackQueryShareUpdate(callbackQuery));
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
    }@SneakyThrows
    private void CallBackQueryShare(CallbackQuery callbackQuery){
        execute(
                functionsForShares.CallBackQueryShare(callbackQuery)
        );
    }
    @SneakyThrows
    private void handleMessage(Message message) {
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntities = message.getEntities().stream()
                    .filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntities.isPresent()) {
                String command = message.getText().substring(commandEntities.get().getOffset(), commandEntities.get().getLength());
                switch (command) {
                    case "/choose_a_share":
                        chooseShare(message);
                        break;
                    case "/choose_a_currency":
                        chooseCurrency(message);
                        break;
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
    private void chooseShare(Message message){
        execute(
                functionsForShares.chooseShare(message)
        );
    }
}
