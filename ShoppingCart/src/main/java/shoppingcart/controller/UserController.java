package shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shoppingcart.model.Category;
import shoppingcart.model.Customer;
import shoppingcart.model.Product;
import shoppingcart.service.ProductService;
import shoppingcart.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.POST)
	public String registerCustomer(@RequestBody Customer customer) {
		return userService.registerCustomer(customer);
	}

	@RequestMapping(value = "/loginCustomer", method = RequestMethod.POST)
	public ResponseEntity<Object[]> loginCustomer(@RequestBody Customer customer) {
		return userService.loginCustomer(customer);
	}

	@RequestMapping(value = "/confirmEmail/{customerid}", method = RequestMethod.GET)
	public String confirmEmail(@PathVariable int customerid) {
		return userService.confirmEmail(customerid);
	}

	@RequestMapping(value = "/getCustomer/{customerid}", method = RequestMethod.GET)
	public ResponseEntity<Object[]> getCustomer(@PathVariable int customerid) {
		return userService.getCustomer(customerid);
	}

}
