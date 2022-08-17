package Services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.LastPrice;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.MarketDataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
public class TinkoffService implements Service {
    private final InvestApi api = InvestApi.createSandbox(System.getenv("token"));
    private final MarketDataService marketDataService = api.getMarketDataService();

    @SneakyThrows
    public List<String> getPricesByFigies(List<String> figies){
        var curr = getMarketDataService().getLastPrices(figies).get();
        List<String> prices = new ArrayList<>();
        for(LastPrice lp:curr){
            prices.add(String.valueOf(lp.getPrice().getUnits() + Double.parseDouble(String.valueOf(lp.getPrice().getNano()))*0.000000001));
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
        return api.getInstrumentsService().getAllCurrencies().get();
    }


    @Override
    public List<List<String>> getAllNotEmptyCurrencies(){
        var allcurr = getAllCurrenciesList();
        var figies = getFigiesByCurrencies(allcurr);
        var prices = getPricesByFigies(figies);
        List<List<String>> curr = new LinkedList<>();
        int len = allcurr.size();
        double price;
        for(int i = 0; i < len;i++){
            price = Double.parseDouble(prices.get(i));
            if(price != 0.0){
                curr.add(
                        Arrays.asList(
                                allcurr.get(i).getName(),
                                figies.get(i),
                                allcurr.get(i).getTicker(),
                                String.valueOf(price)
                        )
                );
            }
        }
        return curr;
    }


}
