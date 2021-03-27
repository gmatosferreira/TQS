package edu.pt.ua.tqs.lab2.s92972;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    // Prepare mock
    @Mock
    IStockMarket market;

    // Create SUT and inject mock (IoC)
    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    void getTotalValue() {
        // Mockito stubbing
        when(market.getPrice("CTT")).thenReturn(3.29);
        when(market.getPrice("Corticeira Amorim")).thenReturn(10.14);

        // Add stocks to portfolio
        this.portfolio.addStock(new Stock("CTT", 20));

        // Verify  getValue
        assertEquals(65.8, this.portfolio.getTotalValue(), "getTotalValue: Value does not match expected");
        assertThat(this.portfolio.getTotalValue(), is(65.8));

        this.portfolio.addStock(new Stock("Corticeira Amorim", 50));
        assertEquals(572.8, this.portfolio.getTotalValue(), "getTotalValue: Value does not match expected");
        assertThat(this.portfolio.getTotalValue(), is(572.8));
    }
}