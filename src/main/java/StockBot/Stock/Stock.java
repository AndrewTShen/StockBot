package Stock;

import java.util.*;

// MutableDouble
import MutableDouble.MutableDouble;

// jsoup
import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//IOException for jsoup
import java.io.IOException;

// GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Stock {
    private String stockAbv;
    private String url;
    private double currPrice;
    private JLabel price;
    private JLabel balance_label;
    private JLabel amountStock;
    private JPanel panel;
    private int num_stock;
    private MutableDouble balance;

    /**
     * Stock class for holding all associated stock information and GUI.
     * @param stockAbv Company abreviation.
     * @param url URL that the stock information is requested from.
     * @param frame Frame that the GUI is added to.
     * @param colNum Column number the stock is added to.
     * @param balance Current balance shared by all of the stocks.
     * @param balance_label The balance label that is shared by all of the stocks.
     */
  	public Stock(String stockAbv, String url, JFrame frame, int colNum, MutableDouble balance, JLabel balance_label) {
        this.stockAbv = stockAbv.toUpperCase();
        this.url = url;
        this.currPrice = this.getData();
        this.num_stock = 0;
        this.price = new JLabel(stockAbv.toUpperCase() + " Price:  Loading Data...");
        this.update();
        this.amountStock = new JLabel("Amount of Stock Owned:  0");
        this.balance = balance;
        this.balance_label = balance_label;
        this.panel = new JPanel();

        System.out.println("Initializing + " + this.url);
        makeGUI(frame, colNum);
    }

    /**
     * Sets up the GUI for the Stock class.
     * @param frame Frame that the GUI is added to.
     * @param colNum Column number the stock is added to.
     */
    public void makeGUI(JFrame frame, int colNum) {
        System.out.println("Adding GUI");
        // the clickable button
        JButton buy_button = new JButton( new AbstractAction("Buy Stock") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (currPrice == -1.0) return;
                num_stock++;
                amountStock.setText("Amount of Stock Owned:  " + num_stock);
                
                double price = currPrice;
                // balance -= price;
                balance.setValue(balance.getValue() - price);
                System.out.println("New Balance: $" + String.format("%.2f", balance.getValue()));
                balance_label.setText("Balance:  $" + String.format("%.2f", balance.getValue()));
            }
        });

        JButton sell_button = new JButton( new AbstractAction("Sell Stock") {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if (currPrice == -1) return;
                if (num_stock >= 1) {
                    num_stock--;
                    amountStock.setText("Amount of Stock Owned:  " + num_stock);
                    
                    double price = currPrice;
                    // balance += price;
                    balance.setValue(balance.getValue() + price);
                    System.out.println("New Balance: $" + String.format("%.2f", balance.getValue()));
                    balance_label.setText("Balance:  $" + String.format("%.2f", balance.getValue()));
                }
            }
        });

        // the panel with the button and text
        this.panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        this.panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 1;
        c.gridy = 0;
        this.panel.add(this.price, c);

        c.gridx = 1;
        c.gridy = 2;
        this.panel.add(this.amountStock, c);

        c.gridx = 1;
        c.gridy = 3;
        this.panel.add(buy_button, c);

        c.gridx = 1;
        c.gridy = 4;
        this.panel.add(sell_button, c);

        GridBagConstraints cf = new GridBagConstraints();        
        cf.gridx = colNum;
        cf.gridy = 2;
        frame.add(this.panel, cf);

        frame.setVisible(true);
    }

    /**
     * Returns the real-time stock prices.
     * @return Real-time stock prices.
     */
    public double getData() {
        System.out.println("Getting Data From " + this.url);
        Document doc = null;
        try {
            doc = Jsoup.connect(this.url).get();
        } catch (IOException e){
            System.out.println("Connect Failed.");
            return -1;
        }
        
        System.out.printf(doc.title() + ": ");
        Elements newsHeadlines = doc.select("span#last_last");

        for (Element headline : newsHeadlines) {
            System.out.printf(headline.ownText() + '\n');
            String input = headline.ownText();
            String cleanInput = "";
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) != ',') {
                    cleanInput += input.charAt(i);
                }
            }
            return Double.parseDouble(cleanInput); 
        }

        // if no headline it fails
        System.out.println("FAILED");
        return -1;
    }

    /**
     * Get the current price stored locally for faster purchases and less
     * web scrapping.
     * @return Current price stored locally.
     */
    public double getCurrPrice() {
        return this.currPrice;
    }

    /**
     * Set the current price of the stock to a new value.
     * @param newPrice The new price of the stock.
     */
    public void setCurrPrice(double newPrice) {
        this.currPrice = newPrice;
    }

    /**
     * Updates the price of the stock to the real-time value and stores it
     * to a local variable for faster access.
     */
    public void update() {
        this.currPrice = this.getData();
        this.price.setText(stockAbv + " Price: $" + String.format("%.2f", this.currPrice));
    }
}