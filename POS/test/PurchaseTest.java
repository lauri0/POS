import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;



public class PurchaseTest {
	private SoldItem solditem1;
	private SoldItem solditem2;
	private StockItem item1;
	private StockItem item2;
	final SalesDomainController domainController = new SalesDomainControllerImpl();
	
	@Before
	public void setUp() {
		item1 = new StockItem((long) 32, "Kosmonaut",
				"Eesti olu", 4.5);
		item2 = new StockItem((long) 33, "Kosmonaut2",
				"Eesti olu", 4.5);
		solditem1 = new SoldItem(item1, 4);
		solditem2 = new SoldItem(item2, 1);
		
		
	}
	// POOLIK!!!!
	@Test
	public void testAddSoldItem(){
		ArrayList<SoldItem> solditems = new ArrayList<SoldItem>();
		solditems.add(solditem1);
		solditems.add(solditem2);
		for (SoldItem item: solditems){
			domainController.addSoldItem(item);
		}
		List<SoldItem> solditems2 = domainController.loadSoldItems();
		Assert.assertTrue(solditems2.contains(solditem1) && solditems2.contains(solditem2));
	}
	@Test
	public void testGetSumWithNoItems(){
		ArrayList<SoldItem> solditems = new ArrayList<SoldItem>();
		Purchase purchase = new Purchase(solditems, 1);
		double sum = purchase.getTotalPrice();
		Assert.assertEquals(sum, 0.0, 0.001);
	}
	@Test
	public void testGetSumWithOneItem(){
		ArrayList<SoldItem> solditems = new ArrayList<SoldItem>();
		solditems.add(solditem1);
		Purchase purchase = new Purchase(solditems, 1);
		double sum = purchase.getTotalPrice();
		Assert.assertEquals(sum, 18.0, 0.001);
		
		
	}
	@Test
	public void testGetSumWithMultipleItems(){
		ArrayList<SoldItem> solditems = new ArrayList<SoldItem>();
		solditems.add(solditem1);
		solditems.add(solditem2);
		Purchase purchase = new Purchase(solditems, 1);
		double sum = purchase.getTotalPrice();
		Assert.assertEquals(sum, 22.5, 0.001);
		
		
	}
}
