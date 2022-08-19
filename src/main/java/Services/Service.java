package Services;

import org.jetbrains.annotations.NotNull;
import ru.tinkoff.piapi.contract.v1.Currency;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.List;

public interface Service {
    List<String> getTimeByFigies(List<String> figies);
    List<String> getPricesByFigies(List<String> figies);
    List<String> getFigiesByCurrencies(List<Currency> currencies);
    List<Currency> getAllCurrenciesList();
    List<Share> getAllSharesList();
    public List<String> getFigiesByShares(@NotNull List<Share> shares);
    List<List<String>> getAllNotEmptyCurrencies();
    List<List<String>> getAllNotEmptyShares();
}
