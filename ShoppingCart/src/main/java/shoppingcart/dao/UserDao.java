package shoppingcart.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import shoppingcart.model.Customer;

@Repository
public interface UserDao {
	public String registerCustomer(Customer customer);
	public ResponseEntity<Object[]> loginCustomer(Customer customer);

	public String confirmEmail(int customerid);
	public ResponseEntity<Object[]> getCustomer(int customerid);
}
