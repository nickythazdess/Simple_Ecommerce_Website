import axios from "axios";
import { getCookie } from "./Cookie";
const endpoint = "http://localhost:8080/api";
const token = getCookie("token");

export function get(url) {
  return axios.get(endpoint + url, {
    headers: { 
      "Access-Control-Allow-Origin": "*",
    },
  });
}

export function getWithAuth(url) {
  return axios.get(endpoint + url, {
    headers: { 
      Authorization: `Bearer ${token}`,
      "Access-Control-Allow-Origin": "*",
      'Content-Type': 'application/json; charset=utf-8',
    },
  });
}

export function getWithParam(url, param) {
  return axios.get(endpoint + url, {
    headers: {
      "Access-Control-Allow-Origin": "*",
    },
    params: param,
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
