import React from "react";
import { Link, withRouter } from "react-router-dom";
import axios from "axios";
class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      email: "",
      password: "",
      goto: false,
    };
    this.handleChange = this.handleChange.bind(this);
    this.getLogin = this.getLogin.bind(this);
  }

  componentWillMount() {
    console.log("Component will mount");
  }

  getLogin(event) {
    event.preventDefault();
    const { email, password } = this.state;
    axios
      .post("http://localhost:8080/ShoppingCart/user/loginCustomer", {
        email: email,
        password: password,
      })
      .then((response) => {
        alert("Welcome!");
        console.log(response, "response of login api----");
        this.props.history.push("/", { customerid: response.data[0].id });
      })
      .catch((error) => {
        console.log("error-----------", error);
        alert(error);
      });
  }

  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }

  render() {
    return (
      <div className="col-md-6 col-md-offset-3">
        <h2>Login Form</h2>
        <form onSubmit={this.getLogin} name="form">
          <div className="form-group">
            <label>Email:</label>
            <input
              required
              type="email"
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
          <button
            type="submit"
            className="btn btn-primary"
          >
            Login
          </button>
          <Link to="/register" className="btn btn-link">
            Register
          </Link>
        </form>
      </div>
    );
  }
}

export default withRouter(Login);
