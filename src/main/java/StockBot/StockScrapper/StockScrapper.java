package StockScrapper;

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


public class StockScrapper {
	private final String apiKey = "YX492WT6UCYA5FS6";
  	private final int timeout = 3000;
  	private String stockAbv;

  	public StockScrapper(String stockAbv) {
  		this.stockAbv = stockAbv;
  	}

	public double getData() {

		Document doc = null;
        try {  
            doc = Jsoup.connect("https://www.investing.com/equities/apple-computer-inc").get();
        } catch (IOException e){
            System.out.println("Connect Failed.");
        }
        
        System.out.printf(doc.title());
        System.out.println("\n\nHere");
        // Elements newsHeadlines = doc.select("class.instrumentDataFlex");
        // Elements newsHeadlines = doc.select("div.instrumentDataFlex");
        Elements newsHeadlines = doc.select("span.arial_26.inlineblock.pid-6408-last");

        for (Element headline : newsHeadlines) {
          // System.out.printf("%s\n\t%s", headline.attr("title"), headline.absUrl("href"));
        	System.out.printf(headline.ownText());
        	System.out.println();
        }

		return 0.0;
	}
}