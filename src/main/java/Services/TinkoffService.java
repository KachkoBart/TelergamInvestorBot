package Services;

import com.google.protobuf.GeneratedMessageV3;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Share;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.core.InstrumentsService;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;
import static ru.tinkoff.piapi.core.utils.DateUtils.timestampToString;

import java.util.*;

@Data
@AllArgsConstructor
@Component
@EnableAsync
public class TinkoffService implements Service {
    private final InvestApi api = InvestApi.createSandbox(System.getenv("token"));
    private final MarketDataService marketDataService = api.getMarketDataService();
    private final InstrumentsService instrumentsService = api.getInstrumentsService();

    public List<String> getStockMarketForCurrencies(List<Currency> currencies){
        List<String> market = new ArrayList<>();
        for(Currency curr:currencies){
            market.add(curr.getExchange());
        }
        return market;
    }
    @Override
    @SneakyThrows
    public List<String> getTimeByFigies(List<String> figies){
        var curr = getMarketDataService().getLastPrices(figies).get();
        List<String> time = new ArrayList<>();
        for(LastPrice prices:curr){
            time.add(timestampToString(prices.getTime()).replace("T", " ").replace("Z", " "));
        }
        return time;
    }

    @SneakyThrows
    public List<String> getPricesByFigies(List<String> figies){
        var curr = getMarketDataService().getLastPrices(figies).get();
        List<String> prices = new ArrayList<>();
        for(LastPrice lp:curr){
            prices.add(String.valueOf(String.format("%4.3f",lp.getPrice().getUnits() + Double.parseDouble(String.valueOf(lp.getPrice().getNano()))*0.000000001)));
        }
        return prices;
    }

    @Override
    public List<String> getFigiesByCurrencies(@NotNull List<Currency> currencies){
        List<String> figi = new ArrayList<>();
        for (ru.tinkoff.piapi.contract.v1.Currency currency : currencies) {
            figi.add(currency.getFigi());
        }
        return figi;
    }
    @Override
    @SneakyThrows
    public List<Currency> getAllCurrenciesList(){
        return instrumentsService.getAllCurrencies().get();
    }


    @Override
    public List<List<String>> getAllNotEmptyCurrencies(){
        var allcurr = getAllCurrenciesList();
        var figies = getFigiesByCurrencies(allcurr);
        var prices = getPricesByFigies(figies);
        var times = getTimeByFigies(figies);
        var market = getStockMarketForCurrencies(allcurr);
        List<List<String>> curr = new LinkedList<>();
        int len = allcurr.size();
        double price;
        for(int i = 0; i < len;i++){
            if(!prices.get(i).equals("0,000")){
                curr.add(
                        Arrays.asList(
                                allcurr.get(i).getName(),
                                figies.get(i),
                                allcurr.get(i).getTicker(),
                                prices.get(i),
                                times.get(i),
                                market.get(i)
                        )
                );
            }
        }
        return curr;
    }


}
