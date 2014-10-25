package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Purchase that can be stored and displayed on the history tab. - Lauri
 */
public class Purchase implements Cloneable, DisplayableItem {

	private long id;
    private String date;
    private String time;
    private double totalPrice;
    private ArrayList<SoldItem> soldItems;
    
    // Placeholder implementation, needs something for soldItems list and totalPrice
    public Purchase() {
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.date = dateFormat.format(date);
        this.time = timeFormat.format(time);
        // Placeholder for testing
        this.totalPrice = 30;
        // Placeholder for testing
        this.soldItems = null;
        
    }
    public Long getId() {
    	return id;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ArrayList<SoldItem> getSoldItems() {
        return soldItems;
    }

    public void setStockItem(ArrayList<SoldItem> soldItems) {
        this.soldItems = soldItems;
    }
    
}
