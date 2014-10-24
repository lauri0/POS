package ee.ut.math.tvt.salessystem.ui.panels;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemTableModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Drop-down menu
    private JComboBox<String> dropDownMenu;
    
    // Text field on the dialogPane
    private JTextField barCodeField;
    private JTextField quantityField;
    private JTextField nameField;
    private JTextField priceField;

    private JButton addItemButton;

    // Warehouse model
    private SalesSystemModel model;

    /**
     * Constructs new purchase item panel.
     * 
     * @param model
     *            composite model of the warehouse and the shopping cart.
     */
    public PurchaseItemPanel(SalesSystemModel model) {
        this.model = model;

        setLayout(new GridBagLayout());

        add(drawDialogPane(), getDialogPaneConstraints());
        add(drawBasketPane(), getBasketPaneConstraints());

        setEnabled(false);
    }

    // shopping cart pane
    private JComponent drawBasketPane() {

        // Create the basketPane
        JPanel basketPane = new JPanel();
        basketPane.setLayout(new GridBagLayout());
        basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

        // Create the table, put it inside a scrollPane,
        // and add the scrollPane to the basketPanel.
        JTable table = new JTable(model.getCurrentPurchaseTableModel());
        JScrollPane scrollPane = new JScrollPane(table);

        basketPane.add(scrollPane, getBacketScrollPaneConstraints());

        return basketPane;
    }

    // purchase dialog
    private JComponent drawDialogPane() {

        // Create the panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Product"));
        
        // Initlialize the drop-down menu -Kristine
        dropDownMenu = new JComboBox<String>();
        
        // Fill the drop-down menu -Kristine
        StockTableModel stockModel = model.getWarehouseTableModel();
    	List<StockItem> items = stockModel.getTableRows();
    	for (StockItem stockItem : items) {
			dropDownMenu.addItem(stockItem.getName());
		}
    	
        // Initialize the textfields
        barCodeField = new JTextField();
        quantityField = new JTextField("1");
        nameField = new JTextField();
        priceField = new JTextField();

        // Fill the dialog fields if an item is selected from the drop-down menu -Kristine 
        dropDownMenu.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                fillDialogFields();
            }
        });
        
        
    	
        barCodeField.setEditable(false);
        nameField.setEditable(false);
        priceField.setEditable(false);

        // == Add components to the panel
        
        // - drop-down menu -Kristine
        panel.add(new JLabel("Choose:"));
        panel.add(dropDownMenu);
        
        // - bar code
        panel.add(new JLabel("Bar code:"));
        panel.add(barCodeField);

        // - amount
        panel.add(new JLabel("Amount:"));
        panel.add(quantityField);

        // - name
        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        // - price
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        // Create and add the button
        addItemButton = new JButton("Add to cart");
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemEventHandler();
            }
        });

        panel.add(addItemButton);

        return panel;
    }
    // Method to add items to the drop-down menu, just in case
    // Perhaps it would be better to use the function to fill the menu
    // -Kristine
    public void fillDropDownMenu(){
    	StockTableModel stockModel = model.getWarehouseTableModel();
    	List<StockItem> items = stockModel.getTableRows();
    	for (StockItem stockItem : items) {
			dropDownMenu.addItem(stockItem.getName());
		}
		  	
    }
    
    // Fill dialog with data from the "database".
    public void fillDialogFields() {
        StockItem stockItem = getStockItemByBarcode();

        if (stockItem != null) {
        	barCodeField.setText(stockItem.getId().toString());
            nameField.setText(stockItem.getName());
            String priceString = String.valueOf(stockItem.getPrice());
            priceField.setText(priceString);
        } else {
            reset();
        }
    }
    
    // Search the id of the item selected, then find the item
    // using the barcode, changed this function -Kristine
    private StockItem getStockItemByBarcode() {
        try {
        	String name = (String) dropDownMenu.getSelectedItem();
        	long id = 1;
        	StockTableModel stockModel = model.getWarehouseTableModel();
        	List<StockItem> items = stockModel.getTableRows();
        	
        	for (StockItem stockItem : items) {
    			if (stockItem.getName() == name){
    				id = stockItem.getId();
    			}
    		}
            int code = (int) id;
            
            return model.getWarehouseTableModel().getItemById(code);
        } catch (NumberFormatException ex) {
            return null;
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * Add new item to the cart.
     */
    public void addItemEventHandler() {
        // add chosen item to the shopping cart.
        StockItem stockItem = getStockItemByBarcode();
        if (stockItem != null) {
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                quantity = 1;
            }
            //if inserted quantity is higher than in the warehouse
            //product is not added to the cart, warning is shown
            if (quantity > stockItem.getQuantity()) {
            	showQuantityWarning();
            	reset();	
            }
            else {
	            model.getCurrentPurchaseTableModel()
	                .addItem(new SoldItem(stockItem, quantity));
	            // Warehouse quantity is reduced
	            stockItem.setQuantity(stockItem.getQuantity()-quantity);
            }
        }
    }

    public void showQuantityWarning() {
    	JDialog warningMessageBox = new JDialog();
    	warningMessageBox.setAlwaysOnTop(true);
    	warningMessageBox.setTitle("Warning");
    	warningMessageBox.add(new JLabel("<html><center>There is not enough "
    			+ "product<br> in the warehouse.</center></html>"));
    	warningMessageBox.setBounds(550, 350, 180, 100);
    	warningMessageBox.setVisible(true);
    	
    }
    /**
     * Sets whether or not this component is enabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.addItemButton.setEnabled(enabled);
        this.barCodeField.setEnabled(enabled);
        this.quantityField.setEnabled(enabled);
    }

    /**
     * Reset dialog fields.
     */
    public void reset() {
        barCodeField.setText("");
        quantityField.setText("1");
        nameField.setText("");
        priceField.setText("");
    }

    /*
     * === Ideally, UI's layout and behavior should be kept as separated as
     * possible. If you work on the behavior of the application, you don't want
     * the layout details to get on your way all the time, and vice versa. This
     * separation leads to cleaner, more readable and better maintainable code.
     * 
     * In a Swing application, the layout is also defined as Java code and this
     * separation is more difficult to make. One thing that can still be done is
     * moving the layout-defining code out into separate methods, leaving the
     * more important methods unburdened of the messy layout code. This is done
     * in the following methods.
     */

    // Formatting constraints for the dialogPane
    private GridBagConstraints getDialogPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 0d;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.NONE;

        return gc;
    }

    // Formatting constraints for the basketPane
    private GridBagConstraints getBasketPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.anchor = GridBagConstraints.WEST;
        gc.weightx = 0.2;
        gc.weighty = 1.0;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.fill = GridBagConstraints.BOTH;

        return gc;
    }

    private GridBagConstraints getBacketScrollPaneConstraints() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.weighty = 1.0;

        return gc;
    }

}
