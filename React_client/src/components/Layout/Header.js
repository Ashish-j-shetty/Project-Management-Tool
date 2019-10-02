import React, { Component } from "react";

import { Link } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";

import { logout } from "../../actions/securityActions";

class Header extends Component {
  logout = () => {
    this.props.logout();
    window.location.href = "/";
  };
  render() {
    const { validToken, user } = this.props.security;

    const userAuthenticate = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/dashboard">
              Dashboard
            </Link>
          </li>
        </ul>

        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link to={"/dashboard"} className="nav-link ">
              <i className="fas fa-user-circle mr-l" />
              {user.fullname}
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/logout" className="nav-link" onClick={this.logout}>
              Logout
            </Link>
          </li>
        </ul>
      </div>
    );

    const userNotAuthenticated = (
      <div className="collapse navbar-collapse" id="mobile-nav">
        <ul className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link to={"/register"} className="nav-link ">
              SignUp
            </Link>
          </li>
          <li className="nav-item">
            <Link to="/login" className="nav-link">
              Login
            </Link>
          </li>
        </ul>
      </div>
    );

    return (
      <nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
        <div className="container">
          <Link className="navbar-brand" to="/">
            Easy Project Management
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#mobile-nav"
          >
            <span className="navbar-toggler-icon" />
          </button>
          {validToken && user ? userAuthenticate : userNotAuthenticated}
        </div>
      </nav>
    );
  }
}

Header.propTypes = {
  logout: PropTypes.func.isRequired,
  security: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  security: state.security
});

export default connect(
  mapStateToProps,
  { logout }
)(Header);
