import Configurations.SpringConfig;
import Services.TinkoffService;
import com.google.protobuf.Timestamp;
import com.google.protobuf.TimestampProto;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mainBot.Bot;
import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
public class Main{
    @SneakyThrows
    public static void main(String[] args) {
        String log4jConfPath = "C:/Users/shv-0/IdeaProjects/TelergamInvestorBot/src/main/java/Properties/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                SpringConfig.class
        );
        log.info("Start");
        Bot bot = context.getBean("bot", Bot.class);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        context.close();
    }







}
