package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Purchase that can be stored and displayed on the history tab. - Lauri
 */
public class Purchase implements Cloneable, DisplayableItem {

	private long id;
    private String pdate;
    private String ptime;
    private double totalPrice;
    private ArrayList<SoldItem> soldItems;
    
    public Purchase(ArrayList<SoldItem> soldItems, double totalPrice) {
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.pdate = dateFormat.format(date);
        this.ptime = timeFormat.format(date);
        this.totalPrice = totalPrice;
        this.soldItems = soldItems;
    }
    
    public Long getId() {
    	return id;
    }
    
    public String getDate() {
        return pdate;
    }
    
    public void setDate(String pdate) {
        this.pdate = pdate;
    }
    
    public String getTime() {
        return ptime;
    }
    
    public void setTime(String time) {
        this.ptime = ptime;
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
