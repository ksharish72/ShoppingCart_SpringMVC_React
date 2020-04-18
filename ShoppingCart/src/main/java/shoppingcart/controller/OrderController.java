package shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shoppingcart.model.CartItem;
import shoppingcart.model.Order;
import shoppingcart.model.Price;
import shoppingcart.service.CartService;
import shoppingcart.service.OrderService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/order")
public class OrderController {
	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/place/{customerid}", method = RequestMethod.POST)
	public String placeOrder(@PathVariable int customerid, @RequestBody Price price) {
		return orderService.placeOrder(customerid, price);
	}

	@RequestMapping(value = "/getOrders/{customerid}", method = RequestMethod.GET)
	public List<Order> GetOrders(@PathVariable int customerid) {
		return orderService.getOrders(customerid);
	}
	@RequestMapping(value = "/cartitems/{orderid}", method = RequestMethod.GET)
	public List<CartItem> GetCartItems(@PathVariable int orderid) {
		return orderService.getOrderCartItems(orderid);
	}

}
