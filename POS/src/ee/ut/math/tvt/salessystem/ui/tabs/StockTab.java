package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

public class StockTab {

	// Two different buttons -Kristine
	private JButton addItem;
	private JButton addExistingItem;

	private SalesSystemModel model;

	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	// warehouse stock tab - consists of a menu and a table
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

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = new JButton("Add new item");
		addExistingItem = new JButton("Add existing item");
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);
		panel.add(addExistingItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Open a new Add new item window if Add Item button is pressed -Kristine
		addItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				drawAddItemWindow();

			}
		});
		// Open a new Add existing item if the corresponding button is pressed -Kristine
		addExistingItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				drawAddExistingItemWindow();

			}
		});
		return panel;
	}
// Adding existing item to the warehouse -Kristine
	
	private void drawAddExistingItemWindow() {
		final JTextField barCodeField;
		final JTextField quantityField;
		JButton addItemButton = new JButton("Add to warehouse");
		
		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		

		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Check whether a product with the inserted barcode exists
					model.getWarehouseTableModel().getItemById(Long
							.parseLong(barCodeField.getText()));
					// Adds items
					StockItem stockItem = new StockItem(Long
							.parseLong(barCodeField.getText()), "", "",1, Integer
							.parseInt(quantityField.getText()));
					model.getWarehouseTableModel().addItem(stockItem);
					// If incorrect data is inserted
				} catch (Exception _) {
					JDialog warningMessageBox = new JDialog();
					warningMessageBox.setAlwaysOnTop(true);
					warningMessageBox.setTitle("Warning");
					warningMessageBox.add(new JLabel("<html><center>Oops<br>"
							+ "Incorrect data!</center></html>"));
					warningMessageBox.setBounds(550, 350, 180, 100);
					warningMessageBox.setVisible(true);
				}

				barCodeField.setText("");
				quantityField.setText("1");
				
			}
		});
		panel.add(addItemButton);

		JDialog addProductWindow = new JDialog();
		addProductWindow.setAlwaysOnTop(true);
		addProductWindow.setTitle("Add product");
		addProductWindow.add(panel);
		addProductWindow.setBounds(550, 350, 250, 150);
		addProductWindow.setVisible(true);
	}

	// This should be somehow reorganized -Kristine
	// Adding new item to the warehouse -Kristine
	private void drawAddItemWindow() {
		final JTextField barCodeField;
		final JTextField quantityField;
		final JTextField nameField;
		final JTextField priceField;
		final JTextField descriptionField;

		JButton addItemButton;

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));

		// Initialize the textfields
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();
		descriptionField = new JTextField();

		// - bar code
		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);
		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);
		// - description
		panel.add(new JLabel("Description:"));
		panel.add(descriptionField);
		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		addItemButton = new JButton("Add to warehouse");

		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					StockItem stockItem = new StockItem(Long
							.parseLong(barCodeField.getText()), nameField
							.getText(), descriptionField.getText(), Double
							.parseDouble(priceField.getText()), Integer
							.parseInt(quantityField.getText()));
					model.getWarehouseTableModel().addItem(stockItem);
					// If incorrect data is inserted
				} catch (Exception _) {
					JDialog warningMessageBox = new JDialog();
					warningMessageBox.setAlwaysOnTop(true);
					warningMessageBox.setTitle("Warning");
					warningMessageBox.add(new JLabel("<html><center>Oops<br>"
							+ "Incorrect data!</center></html>"));
					warningMessageBox.setBounds(550, 350, 180, 100);
					warningMessageBox.setVisible(true);
				}

				barCodeField.setText("");
				quantityField.setText("1");
				nameField.setText("");
				priceField.setText("");
				descriptionField.setText("");

			}
		});

		panel.add(addItemButton);

		JDialog addProductWindow = new JDialog();
		addProductWindow.setAlwaysOnTop(true);
		addProductWindow.setTitle("Add product");
		addProductWindow.add(panel);
		addProductWindow.setBounds(550, 350, 250, 200);
		addProductWindow.setVisible(true);

	}

	// table of the warehouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

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

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

}
