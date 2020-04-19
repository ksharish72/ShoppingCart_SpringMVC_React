package shoppingcart.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import shoppingcart.model.CartItem;
import shoppingcart.model.Order;
import shoppingcart.model.Price;

@Component
@Transactional
public class OrderDaoImp implements OrderDao {

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

	@SuppressWarnings("unchecked")
	@Override
	public String placeOrder(int customerid, Price price) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(CartItem.class);
		List<CartItem> cartItems = (List<CartItem>) criteria.list();
		List<CartItem> filteredCartItems = cartItems.stream()
				.filter(c -> c.getCustomerid() == customerid && c.getOrderid() == 0).collect(Collectors.toList());
		return placeOrder(customerid, filteredCartItems, price, session);
	}

	private String placeOrder(int customerid, List<CartItem> cartItems, Price price, Session session) {
		// TODO Auto-generated method stub
		Order newOrder = new Order();
		String pattern = "MM/dd/yyyy HH:mm:ss";
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);

		// Get the today date using Calendar object.
		Date today = Calendar.getInstance().getTime();
		// Using DateFormat format method we can create a string
		// representation of a date with the defined format.
		String todayAsString = df.format(today);
		newOrder.setCustomerid(customerid);
		newOrder.setPlaceDate(todayAsString);
		newOrder.setPrice(price);
		newOrder.setCartItems(cartItems);
		try {
			session.save(price);
			session.save(newOrder);
			return "Order saved successfully!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}

	@Override
	public List<Order> getOrders(int customerid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(Order.class);
		List<Order> orders = (List<Order>) criteria.list();
		List<Order> filteredOrders = orders.stream().filter(v -> v.getCustomerid() == customerid)
				.collect(Collectors.toList());
		return filteredOrders;
	}

	public List<CartItem> getOrderCartItems(int orderid) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(CartItem.class);
		List<CartItem> cartItems = (List<CartItem>) criteria.list();
		List<CartItem> filtred = cartItems.stream().filter(v -> v.getOrderid() == orderid).collect(Collectors.toList());
		return filtred;
	}

}
