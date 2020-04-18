package shoppingcart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import shoppingcart.dao.ProductDao;
import shoppingcart.dao.UserDao;
import shoppingcart.model.Category;
import shoppingcart.model.Customer;
import shoppingcart.model.Product;

@Service
public class UserService {
	UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String registerCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return userDao.registerCustomer(customer);
	}

	public ResponseEntity<Object[]> loginCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return userDao.loginCustomer(customer);
	}

	public String confirmEmail(int customerid) {
		// TODO Auto-generated method stub
		return userDao.confirmEmail(customerid);
	}

	public ResponseEntity<Object[]> getCustomer(int customerid) {
		// TODO Auto-generated method stub
		return userDao.getCustomer(customerid);
	}

}
