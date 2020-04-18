package shoppingcart.dao;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shoppingcart.model.Category;
import shoppingcart.model.Product;

@Component
@Transactional
public class ProductDaoImp implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	@SuppressWarnings("unchecked")
	public Object[] listAllProducts() {
		try {
			Criteria criteria = getSession().createCriteria(Product.class);
			List<Product> products = (List<Product>) criteria.list();
			List<Category> categories = new ArrayList<Category>();
			for (Product productObj : products) {
				String category = productObj.getCategory();
				Category filteredCategory = categories.stream().filter(v -> v.getName().equals(category)).findAny()
						.orElse(null);
				if (filteredCategory == null) {
					Category objCategory = new Category();
					objCategory.setName(category);
					List<Product> temp = new ArrayList<Product>();
					temp.add(productObj);
					objCategory.setProducts(temp);
					categories.add(objCategory);
				} else {
					filteredCategory.getProducts().add(productObj);
				}
			}
			return new Object[] { categories };

		} catch (Exception e) {
			return new Object[] { e.toString() };
		}
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		Session session = getSession();
		try {
			session.save(product);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteProduct(String code) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> products = (List<Product>) criteria.list();
		Product productToBeDeleted = products.stream().filter(c -> c.getCode().equals(code)).findAny().orElse(null);
		try {
			session.delete(productToBeDeleted);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void updateProduct(String code, Product product) {
		// TODO Auto-generated method stub
		Session session = getSession();
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> products = (List<Product>) criteria.list();
		Product productToBeUpdated = products.stream().filter(c -> c.getCode().equals(code)).findAny().orElse(null);
		Public.printJsonString(product);
		if (product.getCategory() != null)
			productToBeUpdated.setCategory(product.getCategory());
		if (product.getCode() != null)
			productToBeUpdated.setCode(product.getCode());
		if (product.getDescription() != null)
			productToBeUpdated.setDescription(product.getDescription());
		if (product.getImage() != null)
			productToBeUpdated.setImage(product.getImage());
		if (product.getName() != null)
			productToBeUpdated.setName(product.getName());
		if (product.getPrice() != 0)
			productToBeUpdated.setPrice(product.getPrice());
		if (product.getQuantity() != 0)
			productToBeUpdated.setQuantity(product.getQuantity());
		try {
			session.update(productToBeUpdated);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
