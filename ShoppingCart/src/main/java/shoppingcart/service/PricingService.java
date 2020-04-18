package shoppingcart.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoppingcart.dao.PricingDao;
import shoppingcart.dao.ProductDao;
import shoppingcart.model.Price;

@Service
public class PricingService {
	PricingDao pricingDao;
	@Autowired
	public void setPricingDao(PricingDao pricingDao) {
		this.pricingDao = pricingDao;
	}

	public HashMap<String, String> getStates() {
		// TODO Auto-generated method stub
		return pricingDao.getStates();
	}

	public HashMap<String, Integer> getStateTax() {
		// TODO Auto-generated method stub
		return pricingDao.getStateTax();
	}

	public Price getOrderPrice(int priceid) {
		// TODO Auto-generated method stub
		return pricingDao.getOrderPrice(priceid);
	}

}
