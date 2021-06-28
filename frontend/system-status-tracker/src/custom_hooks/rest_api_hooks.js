import { useState, useEffect, useContext } from 'react';
import API from '../api/api';
import { UserContext } from '../components/context/UserContext';
import { useHistory } from 'react-router-dom';
// Authentication

function useAPIRegister(email, username, password) {
    const [response, setResponse] = useState({
        jwtToken: '',
        id: '',
        message: '',
        status: 0
    });

    const history = useHistory();

    useEffect(() => {

        function postData() {
            const request = {
                username: username,
                password: password,
                email: email
            };

            const url = '/auth/register';

            API.post(url, request).then(res => {
                console.log(res);
                let obj = { id: '', jwtToken: '', message: ''}
                obj.id = res.data.id;
                obj.jwtToken = res.data.jwtToken;
                obj.status = res.status;
                obj.message = res.statusText;
                setResponse(obj);
                history.push("/home");
            }).catch(err => {
                console.log(err.response);
                let obj = { id: '', jwtToken: '', status: 0, message: ''};
                obj.status = err.response.status;
                if (err.response.status === 400) {
                    obj.message = err.response.data;
                }
                else {
                    obj.message = "Unable to register, please try again!";
                }
                setResponse(obj);
            });
        }

        if (username !== "" & password !== "" && email !== "") {
            postData();
        }

    }, [email, username, password]);
    
    useAPIGetUser(response.id, response.jwtToken);

    return response;
    
}

function useAPILogin(username, password) {
    const [response, setResponse] = useState({
        jwtToken: '',
        id: '',
        status: 0,
        message: '',
    });

    const history = useHistory();

    useEffect(() => {
        
        function postData() {

            const request = {
                username: username,
                password: password
            };

            const url = '/auth/login';

            API.post(url, request).then(res => {
                console.log(res);//
                let obj = { id: '', jwtToken: '', message: '', status: 0}
                obj.id = res.data.id;
                obj.jwtToken = res.data.jwtToken;
                obj.status = res.status;
                obj.message = res.statusText;
                setResponse(obj);
                history.push("/home");
            }).catch(err => {
                console.log(err.response);
                let obj = { id: '', jwtToken: '', status: 0, message: ''};
                obj.status = err.response.status;
                if (err.response.status === 400) {
                    obj.message = err.response.data;
                }
                else {
                    obj.message = "Invalid username or password!"
                }
                setResponse(obj);
            });

        }

        if (username !== "" & password !== "" ) {
            postData();
        }

    }, [username, password]);
    
    useAPIGetUser(response.id, response.jwtToken);
    
    return response;
}

// Users

function useAPIGetUser(userId, jwtToken) {
    const [response, setResponse] = useState({
        status: 0,
        message: ''
    });
    const {user, setUser} = useContext(UserContext);

    useEffect(() => {

        function getData() {
            const url = `/users/${userId}`;
            API.get(url, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res)//
                let obj = {userId: '', email: '', username: '', systemIds: []}
                obj.userId = res.data.id;
                obj.email = res.data.email;
                obj.password = res.data.password;
                obj.username = res.data.username;
                if (res.data.systemIds !== null) {
                    obj.systemIds = res.data.systemIds;
                }
                setResponse({ status: res.status, message: res.statusText });
                setUser(obj);
            }).catch(err => {
                setResponse({ status: err.response.status, message: err.response.data});
                console.log(err);
            });
        }
        
        if (userId !== "") {
            getData();
        }

    }, [userId, jwtToken]);

    return response;
}


export {
    useAPIRegister,
    useAPILogin,
    useAPIGetUser,
}