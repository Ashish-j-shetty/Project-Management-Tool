import axios from "axios";
import jwt_decode from "jwt-decode";

import { GET_ERRORS, SET_CURRENT_USER } from "../actions/types";
import setJWTToken from "../securityUtils/setJWTToken";

export const createNewUser = (newUser, history) => async dispatch => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");

    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const login = (loginRequest, history) => async dispatch => {
  try {
    const res = await axios.post("/api/users/login", loginRequest);

    //extract the token from the response
    const { token } = res.data;
    //store the token in the local storage
    localStorage.setItem("jwtToken", token);
    //setting the token in the header
    setJWTToken(token);

    //decode the token
    const decoded = jwt_decode(token);
    //dispatch to our security reducer

    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded
    });
    dispatch({
      type: GET_ERRORS,
      payload: {}
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data
    });
  }
};

export const logout = () => dispatch => {
  localStorage.removeItem("jwtToken");
  console.log("inside logout", localStorage.jwtToken);
  setJWTToken(false);
  dispatch({
    type: SET_CURRENT_USER,
    payload: {}
  });
};
