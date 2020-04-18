package shoppingcart.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({  "cartItems" })
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderid")
	private int orderid;
	@Column(name = "customerid")
	private int customerid;

	@Column(name = "placedate")
	private String placeDate;
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "priceid")
	//@JsonProperty("price")
	private Price price;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "orderid")
	@JsonProperty("cartItems")
	private List<CartItem> cartItems;

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getPlaceDate() {
		return placeDate;
	}

	public void setPlaceDate(String placeDate) {
		this.placeDate = placeDate;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
