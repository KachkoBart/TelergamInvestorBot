package Services;

import Model.Stock;

public interface Service {
    Stock getStockByTicker(String ticker);
}
