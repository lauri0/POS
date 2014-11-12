package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labeled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

  private static final Logger log = Logger.getLogger(PurchaseTab.class);

  private final SalesDomainController domainController;

  private JButton newPurchase;

  private JButton submitPurchase;

  private JButton cancelPurchase;

  private PurchaseItemPanel purchasePane;

  private SalesSystemModel model;


  public PurchaseTab(SalesDomainController controller,
      SalesSystemModel model)
  {
    this.domainController = controller;
    this.model = model;
  }


  /**
   * The purchase tab. Consists of the purchase menu, current purchase dialog and
   * shopping cart table.
   */
  public Component draw() {
    JPanel panel = new JPanel();

    // Layout
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new GridBagLayout());

    // Add the purchase menu
    panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

    // Add the main purchase-panel
    purchasePane = new PurchaseItemPanel(model);
    panel.add(purchasePane, getConstraintsForPurchasePanel());

    return panel;
  }




  // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
  private Component getPurchaseMenuPane() {
    JPanel panel = new JPanel();

    // Initialize layout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gc = getConstraintsForMenuButtons();

    // Initialize the buttons
    newPurchase = createNewPurchaseButton();
    submitPurchase = createConfirmButton();
    cancelPurchase = createCancelButton();

    // Add the buttons to the panel, using GridBagConstraints we defined above
    panel.add(newPurchase, gc);
    panel.add(submitPurchase, gc);
    panel.add(cancelPurchase, gc);

    return panel;
  }


  // Creates the button "New purchase"
  private JButton createNewPurchaseButton() {
    JButton b = new JButton("New purchase");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPurchaseButtonClicked();
      }
    });

    return b;
  }

  // Creates the "Confirm" button
  private JButton createConfirmButton() {
    JButton b = new JButton("Confirm");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        submitPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }


  // Creates the "Cancel" button
  private JButton createCancelButton() {
    JButton b = new JButton("Cancel");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }





  /* === Event handlers for the menu buttons
   *     (get executed when the buttons are clicked)
   */


  /** Event handler for the <code>new purchase</code> event. */
  protected void newPurchaseButtonClicked() {
    log.info("New sale process started");
    try {
      domainController.startNewPurchase();
      startNewSale();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /**  Event handler for the <code>cancel purchase</code> event. */
  protected void cancelPurchaseButtonClicked() {
    log.info("Order cancelled");
    try {
      domainController.cancelCurrentPurchase();
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /** Event handler for the <code>submit purchase</code> event. */
  protected void submitPurchaseButtonClicked() {
	  drawPaymentWindow();
	  duringSale();
	  log.info("Order confirmed");
  }
  
  // Gets called when the purchase is accepted - Lauri
  protected void closePaymentButtonClicked(JDialog dialog) {
 	  try {
 		  log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
 		  // Finds the number of previous entries in the history tab
 		  // Needed to assign an id to the purchase
 		  long id = model.getHistoryTableModel().getRowCount();
 		  model.getHistoryTableModel().addItem(domainController.submitCurrentPurchase(
 				  model.getCurrentPurchaseTableModel().getTableRows(), id
 				  ));
 		  Purchase pur = domainController.submitCurrentPurchase(
 				  model.getCurrentPurchaseTableModel().getTableRows(), id);
 		  domainController.addPurchase(pur);
 		  endSale();

 		  // Warehouse quantity is reduced -Kristine
 		  List<SoldItem> soldItems = model.getCurrentPurchaseTableModel().getTableRows();
 		  for (SoldItem soldItem : soldItems) {
 			 soldItem.getStockItem().setQuantity(soldItem.getStockItem().getQuantity() - soldItem.getQuantity());
		}

 		  model.getCurrentPurchaseTableModel().clear();
 		  log.info("Payment successful");
 	  } 
 	  catch (VerificationFailedException e1) {
 		  log.error(e1.getMessage());
 		  log.info("Payment failed");
 	  }
 	  dialog.dispose();
  }

  // Gets called when the purchase is cancelled via the payment window - Lauri
  protected void cancelPaymentButtonClicked(JDialog dialog) {
	  log.info("Payment cancelled");
	  dialog.dispose();
	  startNewSale();
  }

  /* === Helper methods that bring the whole purchase-tab to a certain state
   *     when called.
   */

  // switch UI to the state that allows to proceed with the purchase
  private void startNewSale() {
    purchasePane.reset();

    purchasePane.setEnabled(true);
    submitPurchase.setEnabled(true);
    cancelPurchase.setEnabled(true);
    newPurchase.setEnabled(false);
  }

  // switch UI to the state that allows to initiate new purchase
  private void endSale() {
    purchasePane.reset();

    cancelPurchase.setEnabled(false);
    submitPurchase.setEnabled(false);
    newPurchase.setEnabled(true);
    purchasePane.setEnabled(false);
  }
  
  private void duringSale() {
	purchasePane.reset();
	  
	cancelPurchase.setEnabled(false);
	submitPurchase.setEnabled(false);
	newPurchase.setEnabled(false);
	purchasePane.setEnabled(false);
  }
  
  private void allowClosePayment(JButton closeButton, JButton acceptButton) {
	  closeButton.setEnabled(true);
	  acceptButton.setEnabled(false);
  }
  
  private void disallowClosePayment(JButton closeButton) {
	  closeButton.setEnabled(false);
  }  


  /* === Next methods just create the layout constraints objects that control the
   *     the layout of different elements in the purchase tab. These definitions are
   *     brought out here to separate contents from layout, and keep the methods
   *     that actually create the components shorter and cleaner.
   */

  private GridBagConstraints getConstraintsForPurchaseMenu() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    return gc;
  }


  private GridBagConstraints getConstraintsForPurchasePanel() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1.0;

    return gc;
  }


  // The constraints that control the layout of the buttons in the purchase menu
  private GridBagConstraints getConstraintsForMenuButtons() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 0;
    gc.anchor = GridBagConstraints.CENTER;
    gc.gridwidth = GridBagConstraints.RELATIVE;

    return gc;
  }
  
//A window that allows one to pay for their order - Lauri
	
	private void drawPaymentWindow() {
		// Create buttons, a label and a text field
		final JTextField paymentAmountField = new JTextField();
		final JButton acceptPaymentButton = new JButton("Attempt to pay");
		final JButton cancelPaymentButton = new JButton("Cancel");
		final JButton closePaymentButton = new JButton("Finish");
		closePaymentButton.setEnabled(false);
		final JLabel changeLabel = new JLabel("0");
		
		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Payment"));

		// Find the total sum of the order
		List<SoldItem> items = model.getCurrentPurchaseTableModel().getTableRows();
		int totalSum = 0;
		for (SoldItem item : items) {
			totalSum += item.getSum();
		}
		
		final int finalTotalSum = totalSum;
		
		panel.add(new JLabel("Sum:"));
		panel.add(new JLabel(Integer.toString(finalTotalSum)));

		panel.add(new JLabel("Payment:"));
		panel.add(paymentAmountField);
		
		panel.add(new JLabel("Change:"));
		panel.add(changeLabel);
		
		panel.add(acceptPaymentButton);
		panel.add(cancelPaymentButton);
		
		panel.add(closePaymentButton);

		// Create the payment window and add the panel to its
		final JDialog payWindow = new JDialog();
		payWindow.setAlwaysOnTop(true);
		payWindow.setTitle("Pay for your order");
		payWindow.add(panel);
		payWindow.setBounds(550, 350, 400, 300);
		payWindow.setVisible(true);
		
		// Add listeners to buttons
		cancelPaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPaymentButtonClicked(payWindow);
			}
		});
		
		closePaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closePaymentButtonClicked(payWindow);
			}
		});

		acceptPaymentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (Integer.parseInt(paymentAmountField.getText()) >= finalTotalSum) {
						changeLabel.setText(Integer.toString(Integer.parseInt(paymentAmountField.getText()) - finalTotalSum));
						paymentAmountField.setEditable(false);
						allowClosePayment(closePaymentButton, acceptPaymentButton);
					}
					else {
						disallowClosePayment(closePaymentButton);
					}
				}
				catch (Exception f) {
					log.info("An exception occured when trying to accept the payment");
				}
			}
		});
		
		
	}

}
