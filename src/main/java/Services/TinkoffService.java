package Services;

import Model.Stock;
import lombok.AllArgsConstructor;
import ru.tinkoff.piapi.core.InvestApi;

import java.util.List;

@AllArgsConstructor
public class TinkoffService implements Service {
    private final InvestApi api = InvestApi.createSandbox(System.getenv("token"));


    @Override
    public Stock getStockByTicker(String ticker) {

        return null;
    }

}
