package shoppingcart.dao;

import java.util.Collection;
import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.bouncycastle.jcajce.provider.symmetric.SEED;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shoppingcart.model.CartItem;
import shoppingcart.model.Product;

@Component
@Transactional
public class CartDaoImp implements CartDao {

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
	public String addToCart(int customerid, CartItem cartitem) {
		// TODO Auto-generated method stub
		cartitem.setCustomerid(customerid);
		Session session = getSession();
		Criteria criteria = session.createCriteria(CartItem.class);
		List<CartItem> cartItems = (List<CartItem>) criteria.list();
		CartItem matchedProduct = cartItems.stream().filter(
				v -> v.getCode().equals(cartitem.getCode()) && v.getCustomerid() == customerid && v.getOrderid() == 0)
				.findAny().orElse(null);
		if (matchedProduct != null) {
			/// update cart
			updateCart(customerid, cartitem.getQuantity(), cartitem.getCode());
			return "Product quantity updated!";
		} else {
			try {
				session.save(cartitem);
				reduceProductQuantity(cartitem.getCode(), cartitem.getQuantity(), session);
				return "Added item to cart!";
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}

		}
	}

	@SuppressWarnings("unchecked")
	private void reduceProductQuantity(String code, int quantity, Session session) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> products = (List<Product>) criteria.list();
		Public.printJsonString(products);
		Product matchedProduct = products.stream().filter(v -> v.getCode().equals(code)).findAny().orElse(null);
		float diff = matchedProduct.getQuantity() - quantity;
		System.out.println("Difference " + diff);
		matchedProduct.setQuantity(diff);
		try {
			session.update(matchedProduct);
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	private void addProductQuantity(String code, int quantity, Session session) {
		// TODO Auto-generated method stub
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> products = (List<Product>) criteria.list();
		Public.printJsonString(products);
		Product matchedProduct = products.stream().filter(v -> v.getCode().equals(code)).findAny().orElse(null);
		float diff = matchedProduct.getQuantity() + quantity;
		matchedProduct.setQuantity(diff);
		try {
			session.update(matchedProduct);
		} catch (Exception e) {

		}
	}

	@Override
	public void updateCart(int customerid, int quantity, String code) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = getSession().createCriteria(CartItem.class);
		List<CartItem> cartItemsList = (List<CartItem>) criteria.list();
		CartItem item = cartItemsList.stream().filter(v -> v.getCustomerid() == customerid && v.getCode().equals(code))
				.findAny().orElse(null);
		int quantityDiff = quantity - item.getQuantity();
		item.setQuantity(quantity);

		try {
			session.update(item);
			reduceProductQuantity(code, quantityDiff, session);
		} catch (Exception e) {
			// Other error messages
		}
	}

	@Override
	public void deleteItemInCart(int customerid, String code) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = getSession().createCriteria(CartItem.class);
		List<CartItem> cartItemsList = (List<CartItem>) criteria.list();
		CartItem item = cartItemsList.stream().filter(v -> v.getCustomerid() == customerid && v.getCode().equals(code))
				.findAny().orElse(null);
		int cartQuantity = item.getQuantity();
		addProductQuantity(code, cartQuantity, session);
		try {
			session.delete(item);
		} catch (Exception e) {
			// Other error messages
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CartItem> getCartItems(int customerid) {
		// TODO Auto-generated method stub
		try {
			Session session = getSession();
			Criteria criteria = getSession().createCriteria(CartItem.class);
			List<CartItem> cartItemsList = (List<CartItem>) criteria.list();
			List<CartItem> filteredCartItems = cartItemsList.stream()
					.filter(v -> v.getCustomerid() == customerid && v.getOrderid() == 0).collect(Collectors.toList());
			return filteredCartItems;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
