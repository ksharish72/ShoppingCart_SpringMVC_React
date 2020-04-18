import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import Dashboard from "./index";
import Register from "./components/register";
import Login from "./components/login";
import Orders from "./components/Orders"
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';

export default function App() {
  return (
    <Router>
      {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
      <Switch>
        <Route path="/register">
          <Register />
        </Route>
        <Route path="/login">
          <Login />
        </Route>
        <Route path="/" exact={true}>
          <Dashboard />
        </Route>
        <Route path="/orders">
          <Orders />
        </Route>
      </Switch>
    </Router>
  );
}
ReactDOM.render(<App />, document.getElementById("root"));
