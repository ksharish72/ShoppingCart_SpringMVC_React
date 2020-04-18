package shoppingcart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import shoppingcart.model.CartItem;
import shoppingcart.model.Price;
import shoppingcart.model.Product;
import shoppingcart.model.State;
import shoppingcart.model.Tax;

@Component
@Transactional
public class PricingDaoImp implements PricingDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public HashMap<String, String> getStates() {
		// TODO Auto-generated method stub
		HashMap<String, String> stateAbbreviationMap = new HashMap<String, String>();
		Criteria criteria = getSession().createCriteria(State.class);
		List<State> states = (List<State>) criteria.list();
		for (State stateRow : states) {
			stateAbbreviationMap.put(stateRow.getName(), stateRow.getCode());
		}
		return stateAbbreviationMap;
	}

	@Override
	public HashMap<String, Integer> getStateTax() {
		// TODO Auto-generated method stub
		HashMap<String, Integer> taxMap = new HashMap<String, Integer>();
		Criteria criteria = getSession().createCriteria(Tax.class);
		@SuppressWarnings("unchecked")
		List<Tax> taxes = (List<Tax>) criteria.list();
		for (Tax stateTax : taxes) {
			taxMap.put(stateTax.getCode(), stateTax.getTax());
		}
		return taxMap;
	}

	@Override
	public Price getOrderPrice(int priceid) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession();
			Criteria criteria = getSession().createCriteria(Price.class);
			List<Price> priceList = (List<Price>) criteria.list();
			Price filteredPrice = priceList.stream().filter(v -> v.getPriceid() == priceid).findAny().orElse(null);
			return filteredPrice;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
