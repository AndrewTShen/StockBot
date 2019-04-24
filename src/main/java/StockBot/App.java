/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package StockBot;
import org.patriques.*;

import java.util.*;
import org.patriques.input.Symbol;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.JsonParser;
import org.patriques.output.timeseries.*;
import org.patriques.output.timeseries.data.StockData;
import org.patriques.output.AlphaVantageException;

public class App {
  public static void main(String[] args) {
  	System.out.println("Hello World!");
    String apiKey = "YX492WT6UCYA5FS6";
    int timeout = 3000;
    AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
    TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
    
    try {
      IntraDay response = stockTimeSeries.intraDay("MSFT", Interval.ONE_MIN, OutputSize.COMPACT);
      Map<String, String> metaData = response.getMetaData();
      System.out.println("Information: " + metaData.get("1. Information"));
      System.out.println("Stock: " + metaData.get("2. Symbol"));
      
      List<StockData> stockData = response.getStockData();
      stockData.forEach(stock -> {
        System.out.println("date:   " + stock.getDateTime());
        System.out.println("open:   " + stock.getOpen());
        System.out.println("high:   " + stock.getHigh());
        System.out.println("low:    " + stock.getLow());
        System.out.println("close:  " + stock.getClose());
        System.out.println("volume: " + stock.getVolume());
      });
    } catch (AlphaVantageException e) {
      System.out.println("something went wrong");
    }
  }
}
