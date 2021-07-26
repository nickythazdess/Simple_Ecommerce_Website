import axios from "axios";
import { getCookie } from "./Cookie";
const endpoint = "http://localhost:8080/api";
//const token = getCookie("token");
const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyNzI5MjExOCwiZXhwIjoxNjI3Mzc4NTE4fQ.lYHtOu3Pv4koB6_lR-wmvm3-HE0XjURmgeG4rUyx389AnKTSx3_GFESP1kAb4JVuYrIXdRtDP0eouUvApSPbiA";

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
