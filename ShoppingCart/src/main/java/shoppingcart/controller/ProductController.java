package shoppingcart.controller;

import java.util.HashMap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shoppingcart.model.CartItem;
import shoppingcart.model.Category;
import shoppingcart.model.Customer;
import shoppingcart.model.Product;
import shoppingcart.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object[] listAllProducts() {
		Object[] products = productService.listAllProducts();
		return products;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addProduct(@RequestBody Product product) {
		productService.addProduct(product);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public void deleteProduct(@RequestParam(value = "code") String code) {
		productService.deleteProduct(code);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void updateProduct(@RequestParam(value = "code") String code, @RequestBody Product product) {
		productService.updateProduct(code, product);
	}
}
