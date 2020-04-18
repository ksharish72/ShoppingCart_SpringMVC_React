import React, { Component } from "react";
import CartScrollBar from "./CartScrollBar";
import Counter from "./Counter";
import EmptyCart from "../empty-states/EmptyCart";
import CSSTransitionGroup from "react-transition-group/CSSTransitionGroup";
import { findDOMNode } from "react-dom";
import { withRouter } from "react-router-dom";

class Header extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showCart: false,
      cart: this.props.cartItems,
      mobileSearch: false,
    };
    this.getOrders = this.getOrders.bind(this);
  }
  handleCart(e) {
    e.preventDefault();
    this.setState({
      showCart: !this.state.showCart,
    });
  }
  componentWillReceiveProps(nextProps) {
    const { cartItems } = nextProps;
    this.setState({
      cart: cartItems,
    });
  }
  handleSubmit(e) {
    e.preventDefault();
  }
  handleMobileSearch(e) {
    e.preventDefault();
    this.setState({
      mobileSearch: true,
    });
  }
  handleSearchNav(e) {
    e.preventDefault();
    this.setState(
      {
        mobileSearch: false,
      },
      function () {
        this.refs.searchBox.value = "";
        this.props.handleMobileSearch();
      }
    );
  }
  handleClickOutside(event) {
    const cartNode = findDOMNode(this.refs.cartPreview);
    const buttonNode = findDOMNode(this.refs.cartButton);
    if (cartNode.classList.contains("active")) {
      if (!cartNode || !cartNode.contains(event.target)) {
        this.setState({
          showCart: false,
        });
        event.stopPropagation();
      }
    }
  }
  componentDidMount() {
    document.addEventListener(
      "click",
      this.handleClickOutside.bind(this),
      true
    );
  }
  componentWillUnmount() {
    document.removeEventListener(
      "click",
      this.handleClickOutside.bind(this),
      true
    );
  }
  getOrders() {
    const { userInfo } = this.props;
    this.props.history.push("/orders", { customerid: userInfo.id });
  }
  render() {
    const { userInfo } = this.props;
    console.log(this.state.cart);
    let cartItems;
    cartItems = this.state.cart.map((product) => {
      return (
        <li className="cart-item" key={product.name}>
          <img className="product-image" src={product.image} />
          <div className="product-info">
            <p className="product-name">{product.name}</p>
            <p className="product-price">{product.price}</p>
          </div>
          <div className="product-total">
            <p className="quantity">
              {product.quantity} {product.quantity > 1 ? "Nos." : "No."}{" "}
            </p>
            <p className="amount">{product.quantity * product.price}</p>
          </div>
          <a
            className="product-remove"
            href="#"
            onClick={this.props.removeProduct.bind(this, product.id)}
          >
            ×
          </a>
        </li>
      );
    });
    let view;
    if (cartItems.length <= 0) {
      view = <EmptyCart />;
    } else {
      view = (
        <CSSTransitionGroup
          transitionName="fadeIn"
          transitionEnterTimeout={500}
          transitionLeaveTimeout={300}
          component="ul"
          className="cart-items"
        >
          {cartItems}
        </CSSTransitionGroup>
      );
    }
    return (
      <header>
        <div className="container">
          <div className="brand">
            <h2 style={{ color: "green" }}>Shop Easy</h2>
          </div>

          <div className="search">
            <a
              className="mobile-search"
              href="#"
              onClick={this.handleMobileSearch.bind(this)}
            >
              <img
                src="https://res.cloudinary.com/sivadass/image/upload/v1494756966/icons/search-green.png"
                alt="search"
              />
            </a>
            <form
              action="#"
              method="get"
              className={
                this.state.mobileSearch ? "search-form active" : "search-form"
              }
            >
              <a
                className="back-button"
                href="#"
                onClick={this.handleSearchNav.bind(this)}
              >
                <img
                  src="https://res.cloudinary.com/sivadass/image/upload/v1494756030/icons/back.png"
                  alt="back"
                />
              </a>
              <input
                type="search"
                ref="searchBox"
                placeholder="Search products"
                className="search-keyword"
                onChange={this.props.handleSearch}
              />
              <button
                className="search-button"
                type="submit"
                onClick={this.handleSubmit.bind(this)}
              />
            </form>
          </div>
          {userInfo == null || Object.keys(userInfo).length == 0 ? (
            <div style={{ display: "inline-flex" }}>
              {" "}
              <div style={{ marginLeft: "10" }} className="product-action">
                <button>
                  <a href="/login" style={{ color: "white" }}>
                    Login
                  </a>
                </button>
              </div>
              <div style={{ marginLeft: "10" }} className="product-action">
                <button>
                  <a href="/register" style={{ color: "white" }}>
                    Register
                  </a>{" "}
                </button>
              </div>
            </div>
          ) : (
            <div style={{ display: "inherit" }}>
              Welcome {userInfo.firstName} {userInfo.lastName}
              <br />
              {userInfo.homeAddress} <br />
              {userInfo.state}
              <div style={{ marginLeft: "10" }} className="product-action">
                <button>
                  <a href="/login" style={{ color: "white" }}>
                    Logout
                  </a>
                </button>
              </div>
              <div style={{ marginLeft: "10" }} className="product-action">
                <button onClick={this.getOrders}>
                  <a style={{ color: "white" }}>Orders</a>
                </button>
              </div>
            </div>
          )}

          <div className="cart">
            <div className="cart-info">
              <table>
                <tbody>
                  <tr>
                    <td>State tax</td>
                    <td>:</td>
                    <td>
                      <strong>{this.props.stateTax}%</strong>
                    </td>
                  </tr>
                  <tr>
                    <td>No. of items</td>
                    <td>:</td>
                    <td>
                      <strong>{this.props.totalItems}</strong>
                    </td>
                  </tr>
                  <tr>
                    <td>Sub Total</td>
                    <td>:</td>
                    <td>
                      <strong>{this.props.total}</strong>
                    </td>
                  </tr>
                  <tr>
                    <td>Tax</td>
                    <td>:</td>
                    <td>
                      <strong>
                        {(this.props.stateTax / 100) * this.props.total}
                      </strong>
                    </td>
                  </tr>
                  <tr>
                    <td>Delivery</td>
                    <td>:</td>
                    <td>
                      <strong>{0.02 * this.props.total}</strong>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <a
              className="cart-icon"
              href="#"
              onClick={this.handleCart.bind(this)}
              ref="cartButton"
            >
              <img
                className={this.props.cartBounce ? "tada" : " "}
                src="https://res.cloudinary.com/sivadass/image/upload/v1493548928/icons/bag.png"
                alt="Cart"
              />
              {this.props.totalItems ? (
                <span className="cart-count">{this.props.totalItems}</span>
              ) : (
                ""
              )}
            </a>
            <div
              className={
                this.state.showCart ? "cart-preview active" : "cart-preview"
              }
              ref="cartPreview"
            >
              <CartScrollBar>{view}</CartScrollBar>
              <div className="action-block">
                <button
                  type="button"
                  className={this.state.cart.length > 0 ? " " : "disabled"}
                  onClick={() => this.props.checkout()}
                >
                  PROCEED TO CHECKOUT
                </button>
              </div>
            </div>
          </div>
        </div>
      </header>
    );
  }
}

export default withRouter(Header);
