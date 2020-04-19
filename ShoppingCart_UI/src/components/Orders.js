import React, { Component } from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import BootstrapTable from "react-bootstrap-table-next";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ordersData: [],
      columns: [],
      cartShow: false,
      cartItemsColumnMap: [],
      cartItems: [],
    };
    this.cartButtonFormatter = this.cartButtonFormatter.bind(this);
    this.handleViewCart = this.handleViewCart.bind(this);
    this.handleClose = this.handleClose.bind(this);
  }
  getColumns(obj, columns, parent) {
    for (let key in obj) {
      if (typeof obj[key] === "object") {
        this.getColumns(obj[key], columns, `${key}.`);
      } else {
        columns.push({
          dataField: `${parent}${key}`,
          text: key,
        });
      }
    }
    return columns;
  }
  handleViewCart(event, orderid) {
    event.preventDefault();
    axios
      .get(`http://localhost:8080/ShoppingCart/order/cartitems/${orderid}`)
      .then((response) => {
        console.log(response.data);
        var columnsMap = this.getColumns(response.data[0], [], "");
        console.log(columnsMap);

        this.setState({
          cartItems: response.data,
          cartItemsColumnMap: columnsMap,
          cartShow: true,
        });
      });
  }
  handleClose() {
    this.setState({
      cartShow: false,
    });
  }
  cartButtonFormatter(cell, row) {
    return (
      <button onClick={(event) => this.handleViewCart(event, row.orderid)}>
        View Cart
      </button>
    );
  }
  componentWillMount() {
    console.log(this.props);
    if (this.props.location.state == undefined) {
      this.props.history.push("/login");
    } else {
      let customerid = this.props.location.state.customerid;

      axios
        .get(`http://localhost:8080/ShoppingCart/order/getOrders/${customerid}`)
        .then((response) => {
          console.log(response.data);
          let firstOrder = response.data[0];
          var columnsMap = this.getColumns(firstOrder, [], "");
          console.log(columnsMap);
          columnsMap.push({
            dataField: "view",
            text: "View Cart",
            formatter: this.cartButtonFormatter,
          });
          this.setState({
            ordersData: response.data,
            columns: columnsMap,
          });
        });
    }
  }
  render() {
    console.log(this.state.columns);
    return (
      <div>
        <h2 style={{ textAlign: "center" }}>Orders</h2>{" "}
        <Modal show={this.state.cartShow} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Cart Items</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {this.state.cartItems.length > 0 ? (
              <BootstrapTable
                keyField="cartid"
                data={this.state.cartItems}
                columns={this.state.cartItemsColumnMap}
              />
            ) : (
              <h4>No Orders</h4>
            )}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
        {this.state.ordersData.length > 0 ? (
          <BootstrapTable
            keyField="orderid"
            data={this.state.ordersData}
            columns={this.state.columns}
          />
        ) : (
          <h4>No Orders</h4>
        )}
      </div>
    );
  }
}

export default withRouter(Orders);
