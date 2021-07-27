import axios from "axios";
import { getCookie } from "./Cookie";
const endpoint = "http://localhost:8080/api";
//const token = getCookie("token");
const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNzQwMzM1NSwiZXhwIjoxNjI3NDg5NzU1fQ.4YKfsG5L4MmRrcyrxb69Ad91M4JcSeHidCQlP-kDrz_XPld9oglaihiUPQ14jXixEoh1i1Wjr_HQbRwQi7_fJA";

export function get(url) {
  return axios.get(endpoint + url, {
    headers: { 
      "Access-Control-Allow-Origin": "*",
    },
  });
}

export function getWithAuth(url, body) {
  return axios.get(endpoint + url, {
    headers: { 
      Authorization: `Bearer ${token}`,
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}

export function post(url, body) {
  return axios.post(endpoint + url, body, {
    headers: {
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}

export function postWithAuth(url, body) {
  return axios.post(endpoint + url, body, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}

export function put(url, body) {
  return axios.put(endpoint + url, body, {
    headers: { 
      Authorization: `Bearer ${token}`,
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}

export function del(url) {
  return axios.delete(endpoint + url, {
    headers: { 
      Authorization: `Bearer ${token}` ,
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}
