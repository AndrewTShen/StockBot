package Stock;

import org.patriques.*;
import java.util.*;

// Time series
import org.patriques.input.Symbol;
import org.patriques.input.timeseries.Interval;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.JsonParser;
import org.patriques.output.timeseries.*;
import org.patriques.output.timeseries.data.StockData;
import org.patriques.output.AlphaVantageException;

// Sectors
import org.patriques.output.sectorperformances.Sectors;
import org.patriques.output.sectorperformances.data.SectorData;


public class Stock {
	private final String apiKey = "YX492WT6UCYA5FS6";
  	private final int timeout = 3000;
  	private String stockAbv;

  	public Stock(String stockAbv) {
  		this.stockAbv = stockAbv;
  	}

	public void getData() {
		AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
		TimeSeries stockTimeSeries = new TimeSeries(apiConnector);

		try {
			IntraDay response = stockTimeSeries.intraDay(this.stockAbv, Interval.ONE_MIN, OutputSize.COMPACT);
			Map<String, String> metaData = response.getMetaData();
			System.out.println("Information: " + metaData.get("1. Information"));
			System.out.println("Stock: " + metaData.get("2. Symbol"));

			List<StockData> stockData = response.getStockData();

			StockData stock = stockData.get(0);
			System.out.println("date:   " + stock.getDateTime());
			System.out.println("open:   " + stock.getOpen());
			System.out.println("high:   " + stock.getHigh());
			System.out.println("low:    " + stock.getLow());
			System.out.println("close:  " + stock.getClose());
			System.out.println("volume: " + stock.getVolume());    
		} catch (AlphaVantageException e) {
			System.out.println("something went wrong");
		}
	}
}