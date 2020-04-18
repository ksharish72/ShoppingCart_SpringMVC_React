import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import axios from "axios";
import Header from "./components/Header";
import Products from "./components/Products";
import Footer from "./components/Footer";
import QuickView from "./components/QuickView";
import "./scss/style.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
function UserDetails(props) {
  let stateTax = props.stateTax;
  let userInfo = props.userInfo;
  let subTotal = props.totalAmount;
  let totalItems = props.totalItems;
  var tax = (stateTax / 100) * subTotal;
  var delivery = 0.02 * subTotal;
  var rows = [];
  for (var key in userInfo) {
    if (key != "password" && key != "id" && key != "emailAuthenticated") {
      console.log(key + " -> " + userInfo[key]);
      rows.push(
        <tr>
          <td>{key.toUpperCase()}</td>
          <td> {userInfo[key]}</td>
        </tr>
      );
    }
  }
  rows.push(
    <div>
      <tr>
        <td>State Tax</td>
        <td>{stateTax}</td>
      </tr>
      <tr>
        <td>Sub Amount</td>
        <td>{subTotal}</td>
      </tr>
      <tr>
        <td>Total Items</td>
        <td>{totalItems}</td>
      </tr>
      <tr>
        <td>Tax</td>
        <td>{tax}</td>
      </tr>
      <tr>
        <td>Delivery</td>
        <td>{delivery}</td>
      </tr>
      <tr>
        <td>Grand Total</td>
        <td>{subTotal + tax + delivery}</td>
      </tr>
      <tr>
        <td>Credit Card</td>
        <td>
          <input
            type="number"
            required
            onChange={props.handleCreditCardChange}
          />
        </td>
      </tr>
    </div>
  );
  return rows;
  // return <div>harish</div>;
}
class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      taxes: {},
      creditcard: null,
      checkoutShow: false,
      stateTax: 0,
      userInfo: {},
      products: [],
      cart: [],
      totalItems: 0,
      totalAmount: 0,
      term: "",
      category: "",
      cartBounce: false,
      quantity: 1,
      quickViewProduct: {},
      modalActive: false,
    };
    this.handleCreditCardChange = this.handleCreditCardChange.bind(this);
    this.handleSearch = this.handleSearch.bind(this);
    this.handleMobileSearch = this.handleMobileSearch.bind(this);
    this.handleCategory = this.handleCategory.bind(this);
    this.handleAddToCart = this.handleAddToCart.bind(this);
    this.sumTotalItems = this.sumTotalItems.bind(this);
    this.sumTotalAmount = this.sumTotalAmount.bind(this);
    this.checkProduct = this.checkProduct.bind(this);
    this.updateQuantity = this.updateQuantity.bind(this);
    this.handleRemoveProduct = this.handleRemoveProduct.bind(this);
    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleClose = this.handleClose.bind(this);
    this.handleCheckout = this.handleCheckout.bind(this);
    this.placeOrder = this.placeOrder.bind(this);
  }
  componentWillMount() {
    this.getProducts();
  }
  // Fetch Initial Set of Products from external API
  getProducts() {
    let state = this.props.location.state;
    let url = "http://localhost:8080/ShoppingCart/product/list";
    axios.get(url).then((response) => {
      if (state != undefined && state.hasOwnProperty("customerid")) {
        let customerid = state.customerid;
        this.getUserInfo(customerid, response.data[0]);
      }
      this.setState({
        products: response.data[0],
      });
    });
  }

  getStateTaxes(userInfoState) {
    axios
      .get("http://localhost:8080/ShoppingCart/pricing/region")
      .then((response) => {
        var regions = response.data;
        var userInfoAbbr = regions[userInfoState];
        let url = "http://localhost:8080/ShoppingCart/pricing/tax";
        axios.get(url).then((response) => {
          var taxes = response.data;
          this.setState({
            taxes: taxes,
            stateTax: taxes[userInfoAbbr],
          });
        });
      });
  }

  getCartItems(customerid, categoryProducts) {
    let url = `http://localhost:8080/ShoppingCart/cart/getCartItems/${customerid}`;
    axios.get(url).then((response) => {
      var cartItems = [];
      var totalAmount = 0;
      response.data.forEach((cartEntry) => {
        let productcode = cartEntry.code;
        let products = this.getProductListFromCategory(categoryProducts);
        let productObj = products.find((v) => v.code == productcode);
        cartItems.push({
          image: productObj.image,
          id: productObj.id,
          quantity: cartEntry.quantity,
          price: productObj.price,
          name: productObj.name,
          code: productObj.code,
        });
        totalAmount = totalAmount + productObj.price * cartEntry.quantity;
      });
      this.setState({
        cart: cartItems,
        totalItems: cartItems.length,
        totalAmount: totalAmount,
      });
    });
  }

  getProductListFromCategory(category) {
    var list = [];
    category.forEach((obj) => {
      list.push(...obj.products);
    });
    return list;
  }

  getUserInfo(customerid, products) {
    let url = `http://localhost:8080/ShoppingCart/user/getCustomer/${customerid}`;
    axios.get(url).then((response) => {
      this.getStateTaxes(response.data[0].state);
      this.getCartItems(response.data[0].id, products);
      this.setState({
        userInfo: response.data[0],
      });
    });
  }

  // Search by Keyword
  handleSearch(event) {
    this.setState({ term: event.target.value });
  }
  // Mobile Search Reset
  handleMobileSearch() {
    this.setState({ term: "" });
  }
  // Filter by Category
  handleCategory(event) {
    this.setState({ category: event.target.value });
  }

  storeInCart(productCode, quantity, customerid) {
    let url = `http://localhost:8080/ShoppingCart/cart/add/${customerid}`;
    axios
      .post(url, { quantity: quantity, code: productCode })
      .then((response) => {
        alert(response.data);
        this.getProducts();
      });
  }
  // Add to Cart
  handleAddToCart(selectedProducts) {
    const { userInfo } = this.state;
    if (userInfo.id == undefined) {
      alert("Please login first to add to cart!");
      return;
    }
    this.storeInCart(
      selectedProducts.code,
      selectedProducts.quantity,
      userInfo.id
    );
    let cartItem = this.state.cart;
    let productID = selectedProducts.id;
    let productQty = selectedProducts.quantity;
    if (this.checkProduct(productID)) {
      console.log("hi");
      let index = cartItem.findIndex((x) => x.id == productID);
      cartItem[index].quantity = Number(productQty);
      console.log(cartItem);
      this.setState({
        cart: cartItem,
      });
    } else {
      cartItem.push(selectedProducts);
    }
    this.setState({
      cart: cartItem,
      cartBounce: true,
    });
    setTimeout(
      function () {
        this.setState({
          cartBounce: false,
          quantity: 1,
        });
      }.bind(this),
      1000
    );
    this.sumTotalItems(this.state.cart);
    this.sumTotalAmount(this.state.cart);
  }
  handleRemoveProduct(id, e) {
    let cart = this.state.cart;
    let matchedCart = cart.find((v) => v.id == id);
    this.deleteCartItem(matchedCart.code);
    // let index = cart.findIndex((x) => x.id == id);
    // cart.splice(index, 1);
    // this.setState({
    //   cart: cart,
    // });
    // this.sumTotalItems(this.state.cart);
    // this.sumTotalAmount(this.state.cart);
    e.preventDefault();
  }

  deleteCartItem(productCode) {
    let customerid = this.state.userInfo.id;
    axios
      .delete(
        `http://localhost:8080/ShoppingCart/cart/delete/${customerid}?code=${productCode}`
      )
      .then((resp) => {
        alert("Cart item deleted!");
        let products = this.state.products;
        this.getCartItems(customerid, products);
      });
  }
  checkProduct(productID) {
    let cart = this.state.cart;
    return cart.some(function (item) {
      return item.id === productID;
    });
  }
  sumTotalItems() {
    let total = 0;
    let cart = this.state.cart;
    total = cart.length;
    this.setState({
      totalItems: total,
    });
  }
  sumTotalAmount() {
    let total = 0;
    let cart = this.state.cart;
    for (var i = 0; i < cart.length; i++) {
      total += cart[i].price * parseInt(cart[i].quantity);
    }
    this.setState({
      totalAmount: total,
    });
  }

  //Reset Quantity
  updateQuantity(qty) {
    console.log("quantity added...");
    this.setState({
      quantity: qty,
    });
  }
  // Open Modal
  openModal(product) {
    this.setState({
      quickViewProduct: product,
      modalActive: true,
    });
  }
  // Close Modal
  closeModal() {
    this.setState({
      modalActive: false,
    });
  }
  handleCheckout() {
    this.setState({
      checkoutShow: true,
    });
  }
  handleClose() {
    this.setState({
      checkoutShow: false,
    });
  }
  placeOrder() {
    let cartPrice = this.state.totalAmount;
    let taxPrice = (this.state.stateTax / 100) * this.state.totalAmount;
    let deliveryPrice = 0.02 * this.state.totalAmount;
    var postObj = {
      cartPrice: cartPrice,
      taxPrice: taxPrice,
      deliveryPrice: deliveryPrice,
      totalPrice: cartPrice + taxPrice + deliveryPrice,
      creditcard: this.state.creditcard,
    };
    axios
      .post(
        `http://localhost:8080/ShoppingCart/order/place/${this.state.userInfo.id}`,
        postObj
      )
      .then((response) => {
        alert(response.data);
        this.getCartItems(this.state.userInfo.id, this.state.products);
        this.handleClose()
      })
      .catch((err) => {
        alert(err);
      });
  }
  handleCreditCardChange(e) {
    this.setState({
      creditcard: e.target.value,
    });
  }
  render() {
    return (
      <div className="container">
        <Header
          stateTax={this.state.stateTax}
          userInfo={this.state.userInfo}
          cartBounce={this.state.cartBounce}
          total={this.state.totalAmount}
          totalItems={this.state.totalItems}
          cartItems={this.state.cart}
          checkout={this.handleCheckout}
          removeProduct={this.handleRemoveProduct}
          handleSearch={this.handleSearch}
          handleMobileSearch={this.handleMobileSearch}
          handleCategory={this.handleCategory}
          categoryTerm={this.state.category}
          updateQuantity={this.updateQuantity}
          productQuantity={this.state.moq}
        />
        <Modal show={this.state.checkoutShow} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Checkout</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <UserDetails
              handleCreditCardChange={this.handleCreditCardChange}
              userInfo={this.state.userInfo}
              stateTax={this.state.stateTax}
              totalAmount={this.state.totalAmount}
              totalItems={this.state.totalItems}
            />
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              Close
            </Button>
            <Button variant="primary" onClick={this.placeOrder}>
              Checkout
            </Button>
          </Modal.Footer>
        </Modal>
        <Products
          productsList={this.state.products}
          searchTerm={this.state.term}
          addToCart={this.handleAddToCart}
          productQuantity={this.state.quantity}
          updateQuantity={this.updateQuantity}
          openModal={this.openModal}
        />

        <Footer />
        <QuickView
          product={this.state.quickViewProduct}
          openModal={this.state.modalActive}
          closeModal={this.closeModal}
        />
      </div>
    );
  }
}

export default withRouter(Dashboard);
