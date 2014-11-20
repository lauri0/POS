import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class SoldItemTest {
	private StockItem stockitem;
	@Before
	public void setUp() {
		stockitem = new StockItem((long) 32, "Kosmonaut",
				"Eesti olu", 4.5);
	}

	@Test
	public void testGetSum() {
		SoldItem solditem = new SoldItem(stockitem, 3);
		double sum = solditem.getSum();
		Assert.assertEquals(sum, 13.5, 0.001);
	}
	
	@Test
	public void testSumWithZeroQuantity(){
		SoldItem solditem = new SoldItem(stockitem, 0);
		double sum = solditem.getSum();
		Assert.assertEquals(sum, 0.0, 0.001);
	}
}
