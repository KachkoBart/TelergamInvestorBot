package Services;

import ru.tinkoff.piapi.contract.v1.Currency;

import java.util.List;

public interface Service {
    List<String> getTimeByFigies(List<String> figies);
    List<String> getPricesByFigies(List<String> figies);
    List<String> getFigiesByCurrencies(List<Currency> currencies);
    List<Currency> getAllCurrenciesList();
    List<List<String>> getAllNotEmptyCurrencies();
}
