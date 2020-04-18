package shoppingcart.dao;

import java.sql.SQLIntegrityConstraintViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
//import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.bcel.internal.generic.NEW;

import shoppingcart.model.Category;
import shoppingcart.model.Customer;
import shoppingcart.model.Product;

@Component
@Transactional
public class UserDaoImp implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	public void setTrippleDes(TrippleDes trippleDes) {
		this.trippleDes = trippleDes;
	}

	private TrippleDes trippleDes;

	private org.hibernate.Session getSession() {
		org.hibernate.Session session;

		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		return session;
	}

	@Override
	public String registerCustomer(Customer customer) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = getSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> customers = (List<Customer>) criteria.list();
		Customer existingCustomer = customers.stream().filter(v -> v.getEmail().equals(customer.getEmail())).findFirst()
				.orElse(null);
		if (existingCustomer != null) {
			return "Customer email already exists";
		}
		String passowrd = customer.getPassword();
		String encryptedPassword = trippleDes.encrypt(passowrd);
		customer.setPassword(encryptedPassword);
		try {
			int customerid = (Integer) session.save(customer);
			sendConfirmationLink(customer.getEmail(), customerid);
			return "Confirmation link sent to your email! Please authenticate!";
		} catch (Exception e) {
			// Other error messages
			return e.toString();
		}
	}

	private void sendConfirmationLink(String email, int customerid) {
		// TODO Auto-generated method stub
		// Sender email ID needs to be mentioned.
		String from = "ksharish72@gmail.com";
		String password = "ssnsmhb1996";
		// Recipient's email ID needs to be mentioned
		String to = email;

		// Assuming you are sending email from localhost
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		// Get the default Session object.
		javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(from, password);

			}

		});

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("ShopEasy Email Confirmation Link!");

			// Send the actual HTML message, as big as you like
			String linkString = "http://localhost:8080/ShoppingCart/user/confirmEmail/" + customerid;
			message.setContent("<a href=\"" + linkString + "\">Click on this to confirm email!</a>", "text/html");

			// Send message
			javax.mail.Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	@Override
	public ResponseEntity<Object[]> loginCustomer(Customer customer) {
		// TODO Auto-generated method stub
		String email = customer.getEmail();
		String password = customer.getPassword();
		org.hibernate.Session session = getSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> customers = (List<Customer>) criteria.list();

		String passowrd = customer.getPassword();
		String encryptedPassword = trippleDes.encrypt(passowrd);
		Customer existingCustomer = customers.stream()
				.filter(v -> v.getEmail().equals(customer.getEmail()) && v.getPassword().equals(encryptedPassword))
				.findFirst().orElse(null);
		if (existingCustomer == null) {
			return new ResponseEntity<Object[]>(new Object[] { "Invalid Username/Password" }, HttpStatus.UNAUTHORIZED);
		}
		if (existingCustomer.getEmailAuthenticated().equals("No")) {
			return new ResponseEntity<Object[]>(new Object[] { "Email not authenticated!" }, HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<Object[]>(new Object[] { existingCustomer }, HttpStatus.OK);
	}

	@Override
	public String confirmEmail(int customerid) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = getSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> customers = (List<Customer>) criteria.list();
		Customer existingCustomer = customers.stream().filter(v -> v.getId() == customerid).findAny().orElse(null);
		existingCustomer.setEmailAuthenticated("Yes");
		try {
			session.update(existingCustomer);
			return "Email Authenticated! Please login.";
		} catch (Exception e) {
			// Other error messages
			return e.toString();
		}
	}

	@Override
	public ResponseEntity<Object[]> getCustomer(int customerid) {
		// TODO Auto-generated method stub
		org.hibernate.Session session = getSession();
		Criteria criteria = session.createCriteria(Customer.class);
		List<Customer> customers = (List<Customer>) criteria.list();
		Customer existingCustomer = customers.stream().filter(v -> v.getId() == customerid).findAny().orElse(null);
		if(existingCustomer==null) {
			return new ResponseEntity<Object[]>(new Object[] {"Customer does not exits! Please register!"},HttpStatus.BAD_REQUEST);			
		}else {
			return new ResponseEntity<Object[]>(new Object[] {existingCustomer},HttpStatus.OK);
		}
	}

}
