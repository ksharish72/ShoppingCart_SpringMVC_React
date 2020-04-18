package shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import shoppingcart.dao.ProductDao;
import shoppingcart.model.Category;
import shoppingcart.model.Product;

@Service
public class ProductService {

	ProductDao productDao;

	@Autowired
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public Object[] listAllProducts() {
		return productDao.listAllProducts();
	}

	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		productDao.addProduct(product);
	}

	public void deleteProduct(String code) {
		// TODO Auto-generated method stub
		productDao.deleteProduct(code);
	}

	public void updateProduct(String code, Product product) {
		// TODO Auto-generated method stub
		productDao.updateProduct(code, product);
	}

}
