package ee.ut.math.tvt.salessystem.ui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

/**
 * Graphical user interface of the order details window.
 */
public class OrderDetailsUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(OrderDetailsUI.class);

	private SalesSystemModel model;
	private Purchase purchase;

  /**
   * Constructs the order details window.
   * @param model Sales system model.
   * @param purchase Order that is being viewed in detail.
   */
	public OrderDetailsUI(SalesSystemModel model, Purchase purchase) {
		this.model = model;
		this.purchase = purchase;

		setTitle("Order details");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// set L&F to the nice Windows style
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}

		drawTable();

		// size & location
		int width = 500;
		int height = 300;
		setSize(width, height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - width) / 2, (screen.height - height) / 2);
		
		this.setVisible(true);
	}

	private void drawTable() {
		try {
			final PurchaseInfoTableModel tableModel = new PurchaseInfoTableModel();
			for (SoldItem item : purchase.getSoldItems()) {
				tableModel.addItem(item);
			}
			final JTable table = new JTable(tableModel);
			final JScrollPane scrollPane = new JScrollPane(table);
			this.add(scrollPane);
			
		}
		catch (Exception e) {
			log.info("Encountered an exception while attempting to draw order info table.");
		}
	}
}


