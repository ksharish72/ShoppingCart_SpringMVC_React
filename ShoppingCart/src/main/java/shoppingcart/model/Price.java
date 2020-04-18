package shoppingcart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "priceid")
	private int priceid;
	@Column(name = "cartprice")
	private int cartPrice;
	@Column(name = "taxprice")
	private int taxPrice;
	@Column(name = "deliveryprice")
	private int deliveryPrice;
	@Column(name = "totalprice")
	private int totalPrice;
	@Column(name = "creditcard")
	private int creditcard;


	public int getCreditcard() {
		return creditcard;
	}

	public void setCreditcard(int creditcard) {
		this.creditcard = creditcard;
	}

	public int getPriceid() {
		return priceid;
	}

	public void setPriceid(int priceid) {
		this.priceid = priceid;
	}

	public int getCartPrice() {
		return cartPrice;
	}

	public void setCartPrice(int cartPrice) {
		this.cartPrice = cartPrice;
	}

	public int getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(int taxPrice) {
		this.taxPrice = taxPrice;
	}

	public int getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(int deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
