package shoppingcart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import shoppingcart.model.CartItem;
import shoppingcart.model.Order;
import shoppingcart.model.Price;

@Repository
public interface OrderDao {
	public String placeOrder(int customerid, Price price);

	public List<Order> getOrders(int customerid);

	public List<CartItem> getOrderCartItems(int orderid);
}
