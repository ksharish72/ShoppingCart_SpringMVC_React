package shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import shoppingcart.dao.CartDao;
import shoppingcart.model.CartItem;
import shoppingcart.model.Product;

@Service
public class CartService {
	CartDao cartDao;

	@Autowired
	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public String addToCart(int customerid, CartItem cartitem) {
		// TODO Auto-generated method stub
		return cartDao.addToCart(customerid, cartitem);
	}

	public void updateCart(int customerid, int quantity, String code) {
		// TODO Auto-generated method stub
		cartDao.updateCart(customerid, quantity, code);
	}

	public void deleteItemInCart(int customerid, String code) {
		cartDao.deleteItemInCart(customerid, code);
	}

	public List<CartItem> getCartItems(int customerid) {
		// TODO Auto-generated method stub
		return cartDao.getCartItems(customerid);
	}

}
