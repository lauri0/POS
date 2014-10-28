package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

/**
 * Purchase history details model. - Lauri
 */
public class HistoryTableModel extends SalesSystemTableModel<Purchase> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);
	
	public HistoryTableModel() {
		super(new String[] {"Id", "Date", "Time", "Total price"});
	}

	@Override
	protected Object getColumnValue(Purchase purchase, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return purchase.getId();
		case 1:
			return purchase.getDate();
		case 2:
			return purchase.getTime();
		case 3:
			return purchase.getTotalPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Purchase purchase : rows) {
			buffer.append(purchase.getId() + "\t");
			buffer.append(purchase.getDate() + "\t");
			buffer.append(purchase.getTime() + "\t");
			buffer.append(purchase.getTotalPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
    /**
     * Add new Purchase to table.
     */
    public void addItem(final Purchase purchase) {
        rows.add(purchase);
        log.info("Added a new purchase to history");
        fireTableDataChanged();
    }
}
