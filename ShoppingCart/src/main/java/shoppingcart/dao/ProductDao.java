package shoppingcart.dao;

import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import shoppingcart.model.Category;
import shoppingcart.model.Product;

@Repository
public interface ProductDao {
	public Object[] listAllProducts();

	public void addProduct(Product product);

	public void deleteProduct(String code);

	public void updateProduct(String code, Product product);

}
