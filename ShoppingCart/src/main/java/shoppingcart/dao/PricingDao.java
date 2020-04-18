package shoppingcart.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import shoppingcart.model.Price;


@Repository
public interface PricingDao {
	public HashMap<String, String> getStates();

	public HashMap<String, Integer> getStateTax();

	public Price getOrderPrice(int priceid);
}
