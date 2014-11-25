import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockTableModelTest {
	private StockItem stockitem;
	private StockItem stockitem2;
	@Before
	public void setUp() {
		stockitem = new StockItem((long) 32, "Kosmonaut",
				"Eesti olu", 4.5);
		stockitem = new StockItem((long) 33, "Kosmonaut",
				"Eesti olu", 4.5, 10);
	}
	// ??
	@Test
	public void testValidateNameUniqueness(){
		try {
			StockTableModel stocktablemodel = new StockTableModel();
			stocktablemodel.addItem(stockitem);
			stocktablemodel.addItem(stockitem2);
			Assert.fail();
		}
		catch (IllegalArgumentException e){
			
		}
	}
	
	// Tegin siia midagi, ei ole kindel, kas see on päris õige. --Markus
	@Test
	public void testHasEnoughInStock(){
		StockTableModel stocktablemodel = new StockTableModel();
		stocktablemodel.addItem(stockitem2);
		int quantity = 10;
		Assert.assertTrue(stocktablemodel.hasEnoughInStock(quantity,stockitem2));
		
	}
	
	@Test
	public void testGetItemByIdWhenItemExists(){
		StockTableModel stocktablemodel = new StockTableModel();
		stocktablemodel.addItem(stockitem);
		StockItem returnedItem = stocktablemodel.getItemById(32);
		Assert.assertEquals(returnedItem, stockitem);
	}
	
	@Test
	public void testGetItemByIdWhenThrowsException(){
		StockTableModel stocktablemodel = new StockTableModel();
		try {
			stocktablemodel.getItemById(100);
			Assert.fail();
		}
		catch (NoSuchElementException e){
			
		}
	}
}
