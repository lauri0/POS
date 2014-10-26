package ee.ut.math.tvt.salessystem.ui;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.tabs.HistoryTab;
import ee.ut.math.tvt.salessystem.ui.tabs.PurchaseTab;
import ee.ut.math.tvt.salessystem.ui.tabs.StockTab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
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
	
	// Instances of tab classes
	private PurchaseTab purchaseTab;
	private HistoryTab historyTab;
	private StockTab stockTab;

  /**
   * Constructs sales system GUI.
   * @param domainController Sales domain controller.
   */
	public OrderDetailsUI(SalesSystemModel model, Purchase purchase) {
		this.model = model;
		this.purchase = purchase;

		// Create singleton instances of the tab classes
		historyTab = new HistoryTab(model);
		stockTab = new StockTab(model);
//		purchaseTab = new PurchaseTab(domainController, model);

		setTitle("Order details");

		// set L&F to the nice Windows style
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());

		} catch (UnsupportedLookAndFeelException e1) {
			log.warn(e1.getMessage());
		}

		drawWidgets();

		// size & location
		int width = 400;
		int height = 300;
		setSize(width, height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - width) / 2, (screen.height - height) / 2);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}

	private void drawWidgets() {
/*		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.add("Point-of-sale", purchaseTab.draw());
		tabbedPane.add("Warehouse", stockTab.draw());
		tabbedPane.add("History", historyTab.draw());

		getContentPane().add(tabbedPane);*/
	}

}


