import axios from 'axios';

class RequestHandler {

    postRequest(address, data) {
        return axios.post(address, data, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("user")).token
            }
        })
        .then(response => {
            return response.data;
        })
        .catch(error => {
            throw error;
        });
    }

    getRequest(address) {
        return axios.get(address, {
          headers: {
            'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("user")).token
          }
        })
        .then(response => {
          return response.data;
        })
        .catch(error => {
          throw error;
        });
    }

    putRequest(address, data) {
        return axios.put(address, data, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("user")).token
            }
        })
        .then(response => {
            return response.data;
        })
        .catch(error => {
            throw error;
        });
    }

    deleteRequest(address) {
        return axios.delete(address, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("user")).token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            return response.data;
        })
        .catch(error => {
            throw error;
        });
    } 
}

export default new RequestHandler();
