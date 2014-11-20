import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class StockItemTest {

@Test
public void testClone(){
	StockItem stockitem = new StockItem((long)32, "Kosmonaut", "Eesti olu", 4.5);
	StockItem clone = (StockItem) stockitem.clone();
	Assert.assertEquals(clone.getId(), stockitem.getId());
	Assert.assertEquals(clone.getName(), stockitem.getName());
	Assert.assertEquals(clone.getDescription(), stockitem.getDescription());
	Assert.assertEquals(clone.getPrice(), stockitem.getPrice(), 0.001);
}

@Test
public void testGetColumn(){
	StockItem stockitem = new StockItem((long)32, "Kosmonaut", "Eesti olu", 4.5);
	Object id = stockitem.getColumn(0);
	Assert.assertEquals(id, stockitem.getId());
	
	String name = stockitem.getColumn(1).toString();
	Assert.assertArrayEquals("nimed klapivad", name.toCharArray(), stockitem.getName().toCharArray());
	
	Object price = stockitem.getColumn(2);
	Assert.assertEquals(price, stockitem.getPrice());
	
	Object quantity = stockitem.getColumn(3);
	Assert.assertEquals(quantity, stockitem.getQuantity());
}
}
