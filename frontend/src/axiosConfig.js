import axios from 'axios';

axios.defaults.headers.common["Access-Control-Allow-Credentials"] = true;
axios.defaults.headers.common["withCredentials"] = true;
axios.defaults.headers.common["Access-Control-Allow-Origin"] = "http://localhost:8080";
