package shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shoppingcart.model.CartItem;
import shoppingcart.model.Product;
import shoppingcart.service.CartService;
import shoppingcart.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@RequestMapping(value = "/add/{customerid}", method = RequestMethod.POST)
	public String addCart(@PathVariable int customerid, @RequestBody CartItem cartitem) {
		return cartService.addToCart(customerid, cartitem);
	}

	@RequestMapping(value = "/getCartItems/{customerid}", method = RequestMethod.GET)
	public List<CartItem> getCartItems(@PathVariable int customerid) {
		return cartService.getCartItems(customerid);
	}

	@RequestMapping(value = "/update/{customerid}", method = RequestMethod.PUT)
	public void updateCart(@PathVariable int customerid, @RequestParam(value = "quantity") int quantity,
			@RequestParam(value = "code") String code) {
		cartService.updateCart(customerid, quantity, code);
	}

	@RequestMapping(value = "/delete/{customerid}", method = RequestMethod.DELETE)
	public void deleteItemInCart(@PathVariable int customerid, @RequestParam(value = "code") String code) {
		cartService.deleteItemInCart(customerid, code);
	}
}
