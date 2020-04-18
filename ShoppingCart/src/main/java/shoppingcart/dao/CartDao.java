package shoppingcart.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import shoppingcart.model.CartItem;
import shoppingcart.model.Product;

@Repository
public interface CartDao {
	public String addToCart(int customerid, CartItem cartitem);

	public void updateCart(int cartId, int quantity, String code);

	public void deleteItemInCart(int customerid, String code);

	public List<CartItem> getCartItems(int customerid);
}
