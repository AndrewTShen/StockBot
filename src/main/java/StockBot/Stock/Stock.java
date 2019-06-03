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
    // private JLabel current_balance;
    private JLabel balance_label;

    private JLabel amountStock;
    private JPanel panel;
    private int num_stock;
    // private double balance;
    private MutableDouble balance;

    // Constructor
  	public Stock(String stockAbv, String url, JFrame frame, int colNum, MutableDouble balance, JLabel balance_label) {
        this.stockAbv = stockAbv.toUpperCase();
        this.url = url;
        this.currPrice = this.getData();
        this.num_stock = 0;
        this.price = new JLabel(stockAbv.toUpperCase() + " Price:  Loading Data...");
        // this.current_balance = new JLabel("Balance:  0");
        this.amountStock = new JLabel("Amount of Stock Owned:  0.00");
        this.balance = balance;
        this.balance_label = balance_label;
        this.panel = new JPanel();

        System.out.println("Initializing + " + this.url);
        makeGUI(frame, colNum);
    }

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
        // c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.panel.add(this.price, c);
        // c.gridx = 1;
        // c.gridy = 1;
        // this.panel.add(this.current_balance, c);

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

    // Access real value
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
        // Elements newsHeadlines = doc.select("span.arial_26.inlineblock.pid-6408-last");
        Elements newsHeadlines = doc.select("span#last_last");

        for (Element headline : newsHeadlines) {
            System.out.printf(headline.ownText() + '\n');
            return Double.parseDouble(headline.ownText()); 
        }

        // if no headline it fails
        System.out.println("FAILED");
        return -1;
    }

    // Accessors
    public double getCurrPrice() {
        return this.currPrice;
    }

    // Mutators
    public void setCurrPrice(double newPrice) {
        this.currPrice = newPrice;
    }

    public void update() {
        this.currPrice = this.getData();
        this.price.setText(stockAbv + " Price: $" + String.format("%.2f", this.currPrice));
    }
}