package shoppingcart.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shoppingcart.model.Category;
import shoppingcart.model.Price;
import shoppingcart.service.PricingService;
import shoppingcart.service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/pricing")
public class PricingController {

	@Autowired
	PricingService pricingService;

	// gets all the states and corresponding abbreviations in US
	@RequestMapping(value = "/region", method = RequestMethod.GET)
	public HashMap<String, String> getStates() {
		HashMap<String, String> states = pricingService.getStates();
		return states;
	}

	// gets all the state taxes and corresponding abbreviations in US
	@RequestMapping(value = "/tax", method = RequestMethod.GET)
	public HashMap<String, Integer> getStateTax() {
		HashMap<String, Integer> tax = pricingService.getStateTax();
		return tax;
	}
	
}
