package shoppingcart.model;

import java.util.List;

public class Category {
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	private String name;
	private List<Product> products;
}
