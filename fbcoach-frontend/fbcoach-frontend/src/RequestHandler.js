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
            console.error("Error:", error);
            throw error;
        });
    }

    getRequest(address) {
        console.log(address)
        return axios.get(address, {
          headers: {
            'Authorization': 'Bearer ' + JSON.parse(sessionStorage.getItem("user")).token
          }
        })
        .then(response => {
          console.log("API Response:", response.data);  // Log the API response
          return response.data;
        })
        .catch(error => {
          console.error("Error:", error);
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
            console.error("Error:", error);
            throw error;
        });
    }

    deleteRequest(address) {
        console.log(address)
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
            console.error("Error:", error);
            throw error;
        });
    } 
}

export default new RequestHandler();
