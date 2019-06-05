/**  
 * StockBot - a real-time stock simulator
 * @author  Andrew Shen
 * @version 1.0 
 * @see Stock
 * @see StockList
 */ 
package StockBot;

import java.util.*;

// StockList
import StockList.StockList;

// Mutable Double
import MutableDouble.MutableDouble;

// Stocks information
import Stock.Stock;

// GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class App {

    public static JFrame frame = new JFrame();

    final static int REFRESH_RATE = 20;

    private static MutableDouble balance;
    private static JLabel balance_label;
    private static JTextField add_stocks_field;
    private static int numStocks;
    private static JLabel title;
    private static StockList stocklist;

    /**
    * Constructor for the GUI
    */
    public App() {
        // set up the frame and display it
        frame.setLayout(new GridBagLayout());

        // Set up the balance bar
        balance = new MutableDouble(0.00);
        balance_label = new JLabel("Balance: $0.00");
        balance_label.setFont(balance_label.getFont ().deriveFont (24.0f));

        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("The StockBot");
        c.fill = GridBagConstraints.HORIZONTAL;
        title.setFont(title.getFont ().deriveFont (64.0f));
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(title, c);

        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        frame.add(balance_label, c);

        add_stocks_field = new JTextField(" Enter Stock to Add (from investing.com)");

        c.ipady = 10;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        frame.add(add_stocks_field, c);

        add_stocks_field.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                String text = add_stocks_field.getText();
                System.out.println(text);
                add_stocks_field.selectAll();
                if (stocklist != null) {
                    // Try adding the new stock
                    Stock toAdd = new Stock(text.substring(text.lastIndexOf('/')+1), text, frame, numStocks, balance, balance_label);
                    stocklist.addStock(toAdd);
                    numStocks++;
                }
                add_stocks_field.setText("");
            }
        });

        add_stocks_field.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                add_stocks_field.selectAll();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("StockBot");
        frame.pack();
        frame.setSize(1000, 1500);
        frame.setVisible(true);
    }

    /**
     * Main Function
     * @param args Argumuents passed into main.
     */
    public static void main(String[] args) {
        new App();

        stocklist = new StockList(frame);

        /* Testing */

        // Stock appl = new Stock("apple-computer-inc", "https://www.investing.com/equities/apple-computer-inc", frame, 0, balance, balance_label);
        // numStocks++;
        // Stock msft = new Stock("microsoft-corp", "https://www.investing.com/equities/microsoft-corp", frame, 1, balance, balance_label);
        // numStocks++;

        // stocklist.addStock(appl);
        // stocklist.addStock(msft);
        

        try {  
            while (true) {
                stocklist.updateAllStocks();

                // Delay so we aren't overloading the website.
                Thread.sleep(REFRESH_RATE * 1000);
            }    
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished.");
    }
}
