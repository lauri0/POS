package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.Purchase;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	private static final Logger log = Logger.getLogger(SalesDomainControllerImpl.class);
	private static SessionFactory factory;
	
	// A method that returns a Purchase type object - Lauri
	public Purchase submitCurrentPurchase(List<SoldItem> goods, long id) 
			throws VerificationFailedException {
		// XXX - Save purchase
		// Find the total price of bought goods
		double sum = 0;
		for (SoldItem item : goods) {
			sum += item.getSum();
		}
		// Using a cast
		ArrayList<SoldItem> goodsList = (ArrayList<SoldItem>) goods;
		Purchase purchase = new Purchase(goodsList, sum, id);
		return purchase;
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}
	

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
		// XXX mock implementation
		List<StockItem> dataset = new ArrayList<StockItem>();
		
		Transaction tx = null;
		try {
			tx = HibernateUtil.currentSession().beginTransaction();
			List stockItemList = HibernateUtil.currentSession().createQuery("from StockItem").list();
			for (Iterator iterator = stockItemList.iterator(); iterator.hasNext();) {
				StockItem stockItem = (StockItem) iterator.next();
				dataset.add(stockItem);
			}
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		}

		return dataset;
	}
	
	public List<SoldItem> loadSoldItems() {
		// XXX mock implementation
		List<SoldItem> dataset = new ArrayList<SoldItem>();
		
		Transaction tx = null;
		try {
			tx = HibernateUtil.currentSession().beginTransaction();
			List soldItemList = HibernateUtil.currentSession().createQuery("from SoldItem").list();
			for (Iterator iterator = soldItemList.iterator(); iterator.hasNext();) {
				SoldItem solditem = (SoldItem) iterator.next();
				dataset.add(solditem);
			}
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		}

		return dataset;
	}
	
	public List<Purchase> loadHistoryState() {
		List<Purchase> dataset = new ArrayList<Purchase>();
		
		Transaction tx = null;
		try {
			tx = HibernateUtil.currentSession().beginTransaction();
			List purchaseList = HibernateUtil.currentSession().createQuery("from Purchase").list();
			for (Iterator iterator = purchaseList.iterator(); iterator.hasNext();) {
				Purchase purchase = (Purchase) iterator.next();
				dataset.add(purchase);
			}
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
			}
		}
		
		return dataset;
	}
	
	public void endSession() {
		
	    HibernateUtil.closeSession();
	    log.info("Session closed.");
	}
	
	public void addStockItem(StockItem stockitem){
		Session sess = HibernateUtil.currentSession(); 
				/*HibernateUtil.currentSession();*/
		Transaction tx = null;
		try{
			tx = sess.beginTransaction();
			sess.save(stockitem);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			log.error(e);
		}
	}
	
	public void addPurchase(Purchase purchase) {
		Session sess = HibernateUtil.currentSession();
		Transaction tx = null;
		try{
			tx = sess.beginTransaction();
			sess.save(purchase);
			tx.commit();
		}catch(HibernateException e){
			if (tx!=null) tx.rollback();
			log.error(e);
		}
	}
	
	public void addSoldItem(SoldItem solditem) {
		Session sess = HibernateUtil.currentSession();
		Transaction tx = null;
		try{
			tx = sess.beginTransaction();
			sess.save(solditem);
			tx.commit();
		}catch(HibernateException e){
			if (tx!=null) tx.rollback();
			log.error(e);
		}
	}
	
	public void updateStockItem(Long id, int quantity){
		Session sess = HibernateUtil.currentSession();
		Transaction tx = null;
		try{
			tx = sess.beginTransaction();
			StockItem stockitem = (StockItem)sess.get(StockItem.class, id);
			int olemasolevkvant = stockitem.getQuantity();
			stockitem.setQuantity(olemasolevkvant + quantity);
			sess.update(stockitem);
			tx.commit();
		}catch(HibernateException e){
			if (tx!=null) tx.rollback();
			log.error(e);
		}
	}
}
