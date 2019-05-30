package Stock;

import java.util.*;

// jsoup
import org.jsoup.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//IOException for jsoup
import java.io.IOException;


public class Stock {
    private String stockAbv;
    private String url;
    private double currPrice;
    


    // Constructor
  	public Stock(String stockAbv, String url) {
        this.stockAbv = stockAbv;
        this.url = url;
        this.currPrice = this.getData();
        System.out.println("Initializing + " + this.url);
    }

    // Access real value
    public double getData() {
        System.out.println("Getting Data From " + this.url);
        Document doc = null;
        try {  
            doc = Jsoup.connect(this.url).get();
        } catch (IOException e){
            System.out.println("Connect Failed.");
        }
        
        System.out.printf(doc.title());
        System.out.println("\n\nHere");
        Elements newsHeadlines = doc.select("span.arial_26.inlineblock.pid-6408-last");

        for (Element headline : newsHeadlines) {
            System.out.printf(headline.ownText());
            System.out.println();
            return Double.parseDouble(headline.ownText()); 
        }

        // if no headline it fails
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
}