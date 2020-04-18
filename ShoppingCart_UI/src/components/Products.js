import React, { Component } from "react";
import Product from "./Product";
import LoadingProducts from "../loaders/Products";
import NoResults from "../empty-states/NoResults";
import CSSTransitionGroup from "react-transition-group/CSSTransitionGroup";
import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
class Products extends Component {
  constructor() {
    super();
  }
  render() {
    const { productsList } = this.props;
    let term = this.props.searchTerm;
    let x;

    function searchingFor(term) {
      return function (x) {
        return x.name.toLowerCase().includes(term.toLowerCase()) || !term;
      };
    }

    const categoryProductsData = (categoryProducts) =>
      categoryProducts.filter(searchingFor(term)).map((product) => {
        return (
          <Product
            availableQuantity={product.quantity}
            code={product.code}
            key={product.id}
            price={product.price}
            name={product.name}
            image={product.image}
            id={product.id}
            addToCart={this.props.addToCart}
            productQuantity={this.props.productQuantity}
            updateQuantity={this.props.updateQuantity}
            openModal={this.props.openModal}
          />
        );
      });

    // Empty and Loading States
    let view;
    if (productsList.length <= 0 && !term) {
      view = <LoadingProducts />;
    } else if (productsList.length <= 0 && term) {
      view = <NoResults />;
    } else {
      view = (
        <CSSTransitionGroup
          transitionName="fadeIn"
          transitionEnterTimeout={500}
          transitionLeaveTimeout={300}
          component="div"
          className="products"
        >
          <Tabs className="category" defaultActiveKey="Electronics" id="uncontrolled-tab-example">
            {productsList.map((category) => {
              return (
                <Tab
                  //style={{ display: "flex" }}
                  eventKey={category.name}
                  title={category.name}
                >
                  {categoryProductsData(category.products)}
                </Tab>
              );
            })}
          </Tabs>
        </CSSTransitionGroup>
      );
    }
    return <div className="products-wrapper">{view}</div>;
  }
}

export default Products;
