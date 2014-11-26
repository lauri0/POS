package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {

    /**
     * Load the current state of the warehouse.
     * 
     * @return List of ${link
     *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
     */
	
	public List<SoldItem> loadSoldItems();
	
    public List<StockItem> loadWarehouseState();

    // business processes
    /**
     * Initiate new business transaction - purchase of the goods.
     * 
     * @throws VerificationFailedException
     */
    
    public List<Purchase> loadHistoryState();
    
    public void startNewPurchase() throws VerificationFailedException;

    /**
     * Rollback business transaction - purchase of goods.
     * 
     * @throws VerificationFailedException
     */
    public void cancelCurrentPurchase() throws VerificationFailedException;

    /**
     * Commit business transaction - purchase of goods.
     * 
     * @param goods
     * @param totalPrice
     *            Goods that the buyer has chosen to buy.
     * @throws VerificationFailedException
     */
    public Purchase submitCurrentPurchase(List<SoldItem> goods, long id) 
    		throws VerificationFailedException;

    public void endSession();
    
    public void addStockItem(StockItem stockitem);
    
    public void addPurchase(Purchase purchase);
    
	public void addSoldItem(SoldItem solditem);
    
    public void updateStockItem(Long id, int quantity);
}
