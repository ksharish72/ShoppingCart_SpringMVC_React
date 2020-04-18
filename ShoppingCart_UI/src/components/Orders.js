import React, { Component } from "react";
import axios from "axios";
import { withRouter } from "react-router-dom";
import BootstrapTable from "react-bootstrap-table-next";

class Orders extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ordersData: [],
      columns: [],
    };
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
        <h2>Orders</h2>{" "}
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
