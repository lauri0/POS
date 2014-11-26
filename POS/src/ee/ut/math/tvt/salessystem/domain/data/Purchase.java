package ee.ut.math.tvt.salessystem.domain.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;


/**
 * Purchase that can be stored and displayed on the history tab. - Lauri
 */
@Entity
@Table(name="PURCHASE")
public class Purchase implements Cloneable, DisplayableItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "PURCHASE_DATE")
    private String pdate;
	
	@Column(name = "PURCHASE_TIME")
    private String ptime;
	
	@Column(name = "TOTAL_PRICE")
    private double totalPrice;
	
	@OneToMany(mappedBy = "purchase")
    private List<SoldItem> soldItems;
    
    public Purchase() {
    	
    }
    
    public Purchase(ArrayList<SoldItem> soldItems, double totalPrice, long id) {
    	this.id = id;
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.pdate = dateFormat.format(date);
        this.ptime = timeFormat.format(date);
        this.totalPrice = totalPrice;
        this.soldItems = soldItems;
    }
    
    public Purchase(long id, String pdate, String ptime, double totalPrice) {
    	this.id = id;
    	this.pdate = pdate;
    	this.ptime = ptime;
    	this.totalPrice = totalPrice;
    }
    
    public Purchase(ArrayList<SoldItem> soldItems, long id) {
    	this.id = id;
    	Date date = new Date();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        this.pdate = dateFormat.format(date);
        this.ptime = timeFormat.format(date);
        
        this.soldItems = soldItems;
     // Find the total sum of the order
        
        double totalSum = 0;
        for (SoldItem item : soldItems) {
                totalSum += item.getSum();
        }
        this.totalPrice = totalSum;
    }
    public Long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
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

    public List<SoldItem> getSoldItems() {
        return soldItems;
    }
    
    public void setSoldItems(ArrayList<SoldItem> soldItems) {
    	this.soldItems = soldItems;
    }
    
    public void addSoldItem(SoldItem soldItem) {
    	this.soldItems.add(soldItem);
    }
}
