package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.OrderDetailsUI;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

/**
 * Encapsulates everything that has to do with the history tab (the tab
 * labeled "History" in the menu).
 */
public class HistoryTab {
    
    // TODO - implement!
	
	private SalesSystemModel model;

    public HistoryTab(SalesSystemModel model) {
    	this.model = model;
    } 
    
    public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

//		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawHistoryMainPane(), gc);
		return panel;
    }
    
	// History table
	private Component drawHistoryMainPane() {
		JPanel panel = new JPanel();

		try {
			final JTable table = new JTable(model.getHistoryTableModel());
			
			// Adds a MouseListener to the table
			// This is necessary to display additional information about the specific order in history
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// Only row (not the column) is needed to identify the order
					int row = table.getSelectedRow();
					// Creates the window with additional information about the order
					OrderDetailsUI orderUI = new OrderDetailsUI(model,
							model.getHistoryTableModel().getItemById((long) row));
				}
			});

			JTableHeader header = table.getTableHeader();
			header.setReorderingAllowed(false);

			JScrollPane scrollPane = new JScrollPane(table);

			GridBagConstraints gc = new GridBagConstraints();
			GridBagLayout gb = new GridBagLayout();
			gc.fill = GridBagConstraints.BOTH;
			gc.weightx = 1.0;
			gc.weighty = 1.0;

			panel.setLayout(gb);
			panel.add(scrollPane, gc);

		}
		catch (Exception e) {
			// TODO
		}
		
		panel.setBorder(BorderFactory.createTitledBorder("History"));
		return panel;
	}
}