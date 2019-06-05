package StockList;

import Stock.Stock;

import java.util.*;
import java.util.List;
import java.util.ArrayList;

// GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StockList {

	private List<Stock> stocks = new ArrayList<Stock>();
	private int num_stocks;

	/**
	 * Constructor for the StockList.
	 * @param frame The frame to add GUI to.
	 */
	public StockList(JFrame frame) {
		this.num_stocks = 0;
	}

	/**
	 * Add stock to the stocklist.
	 * @param toAdd Stock to add.
	 */
	public void addStock(Stock toAdd) {
		this.stocks.add(toAdd);
		num_stocks += 1;
	}

	/**
	 * Returns the stock at specified index.
	 * @param idx Index of stock to get.
	 * @return Stock at specified index.
	 */
	public Stock getStockAt(int idx) {
        return this.stocks.get(idx);
    }

    public List<Stock> getStocks() {
    	return this.stocks;
    }

    public void updateAllStocks() {
    	for (Stock stock : stocks) {
    		stock.update();
    	}
    }


}