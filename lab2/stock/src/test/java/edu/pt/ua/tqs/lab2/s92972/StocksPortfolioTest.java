package edu.pt.ua.tqs.lab2.s92972;

import static org.junit.jupiter.api.Assertions.*;

class StocksPortfolioTest {

    private StocksPortfolio portfolio;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.portfolio = new StocksPortfolio();
        this.portfolio.setMarketService(new IStockMarket());
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        this.portfolio = null;
    }

    @org.junit.jupiter.api.Test
    void getTotalValue() {
        // Add stocks to portfolio
        this.portfolio.addStock(new Stock("CTT", 20)); // 3,29
        assertEquals(65.8, this.portfolio.getTotalValue(), "getTotalValue: Value does not match expected");

        this.portfolio.addStock(new Stock("Corticeira Amorim", 500)); // 10,14
        assertEquals(572.8, this.portfolio.getTotalValue(), "getTotalValue: Value does not match expected");
    }
}