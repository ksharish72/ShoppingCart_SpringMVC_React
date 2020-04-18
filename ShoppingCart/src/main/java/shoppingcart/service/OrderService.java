package shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoppingcart.dao.CartDao;
import shoppingcart.dao.OrderDao;
import shoppingcart.model.CartItem;
import shoppingcart.model.Order;
import shoppingcart.model.Price;

@Service
public class OrderService {
	OrderDao orderDao;

	@Autowired
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public String placeOrder(int customerid, Price price) {
		// TODO Auto-generated method stub
		return orderDao.placeOrder(customerid, price);
	}

	public List<Order> getOrders(int customerid) {
		// TODO Auto-generated method stub
		return orderDao.getOrders(customerid);
	}

	public List<CartItem> getOrderCartItems(int orderid) {
		// TODO Auto-generated method stub
		return orderDao.getOrderCartItems(orderid);
	}

}
