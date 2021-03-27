package edu.pt.ua.tqs.lab2.s92972;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {

    private String name;
    private IStockMarket marketService;
    private List<Stock> stocks;

    public StocksPortfolio() {
        this.stocks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStockMarket getMarketService() {
        return marketService;
    }

    public void setMarketService(IStockMarket marketService) {
        this.marketService = marketService;
    }

    public Double getTotalValue() {
        Double total = 0.0;
        for(Stock s:stocks) {
            total += s.getQuantity() * marketService.getPrice(s.getName());
        }
        return total;
    }

    public void addStock(Stock s) {
        this.stocks.add(s);
    }
}
