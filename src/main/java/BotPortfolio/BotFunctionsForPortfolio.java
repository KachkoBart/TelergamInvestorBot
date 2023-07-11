package BotPortfolio;

import Services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.core.OperationsService;
import ru.tinkoff.piapi.core.UsersService;
import ru.tinkoff.piapi.core.models.Portfolio;
import ru.tinkoff.piapi.core.models.Position;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotFunctionsForPortfolio {
    @Autowired
    private Service service;

    public SendMessage getPortfolio(Message message){
        String accountId = service.getAccounts().get(0).getId();
        Portfolio portfolio = service.getPortfolio(accountId);
        List<String> parsedInfo = presentationOfPositions(portfolio.getPositions());
        StringBuilder ans = new StringBuilder();
        for (String s : parsedInfo) {
            ans.append(s);
        }
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(ans.toString())
                .build();
    }
    private List<String> presentationOfPositions(List<Position> positions){
        List<String> ans = new ArrayList<>();
        int sizePositions = positions.size();

        for(int i = 0; i < sizePositions;i++){
            Position position = positions.get(i);
            Instrument instrument = service.getInstrumentByFigi(position.getFigi());
            ans.add(
                    (i + 1) + ". " +
                    instrument.getName() + " " +
                    position.getInstrumentType() + " " +
                    position.getCurrentPrice().getValue().doubleValue() + " " +
                    position.getCurrentPrice().getCurrency() + " " +
                    position.getExpectedYield().doubleValue() + " " +
                    position.getQuantity().doubleValue()+ "\n"
            );
        }
        return ans;
    }

}
