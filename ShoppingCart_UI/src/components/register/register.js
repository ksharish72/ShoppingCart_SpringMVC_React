import React from "react";
import { Link, withRouter } from "react-router-dom";
import axios from "axios";
import { DropdownButton, Item, Dropdown } from "react-bootstrap";

class Register extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      state: "",
      address: "",
      countryStates: [],
    };
    this.getRegister = this.getRegister.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.updateCountryState = this.updateCountryState.bind(this);
  }
  getRegister(event) {
    event.preventDefault();
    const { firstName, lastName, email, password, state, address } = this.state;
    axios
      .post("http://localhost:8080/ShoppingCart/user/registerCustomer", {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        state: state,
        homeAddress: address,
      })
      .then((response) => {
        alert(response.data);
        console.log(response.data, "response of register api----");
      })
      .catch((error) => {
        console.log("error-----------", error);
      });
    // // this.props.history.push('/login')
  }
  componentWillMount() {
    axios
      .get("http://localhost:8080/ShoppingCart/pricing/region")
      .then((response) => {
        console.log(response);
        let countryStates = response.data;
        var temp = [];
        for (let state in countryStates) {
          var key = state;
          var value = countryStates[key];
          temp.push({
            stateName: key,
            stateCode: value,
          });
        }
        this.setState({
          countryStates: [...this.state.countryStates, ...temp],
        });
      });
  }
  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }
  updateCountryState(value) {
    this.setState({
      state: value,
    });
  }
  render() {
    const { countryStates, state } = this.state;
    return (
      <div className="col-md-6 col-md-offset-3">
        <h2>Customer Registration</h2>
        <form onSubmit={this.getRegister}>
          <div className="form-group">
            <label>First Name:</label>
            <input
              type="text"
              required
              name="firstName"
              className="form-control"
              onChange={this.handleChange}
              value={this.state.firstName}
            />
          </div>
          <div className="form-group">
            <label>Last Name:</label>
            <input
              required
              type="text"
              name="lastName"
              className="form-control"
              onChange={this.handleChange}
              value={this.state.lastName}
            />
          </div>
          <div className="form-group">
            <label>Email:</label>
            <input
              type="email"
              required
              name="email"
              className="form-control"
              onChange={this.handleChange}
              value={this.state.email}
            />
          </div>
          <div className="form-group">
            <label>Password: </label>
            <input
              required
              type="password"
              name="password"
              className="form-control"
              onChange={this.handleChange}
              value={this.state.password}
            />
          </div>
          <div className="form-group">
            <label>Address: </label>
            <input
              type="address"
              name="address"
              required
              className="form-control"
              onChange={this.handleChange}
              value={this.state.address}
            />
          </div>
          <div className="form-group">
            <label>State: (for tax calculation purpose) </label>
            <DropdownButton
              required
              onSelect={this.updateCountryState}
              id="dropdown-basic-button"
              title={state}
            >
              {countryStates.map((state) => {
                return (
                  <Dropdown.Item eventKey={state.stateName}>
                    {state.stateName}
                  </Dropdown.Item>
                );
              })}
            </DropdownButton>
          </div>
          <button type="submit" className="btn btn-primary">
            Register
          </button>
          <Link to="/login" className="btn btn-link">
            Login
          </Link>
        </form>
      </div>
    );
  }
}

export default withRouter(Register);
