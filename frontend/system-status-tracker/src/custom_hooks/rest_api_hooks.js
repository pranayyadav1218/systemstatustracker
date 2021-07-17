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
                let obj = {userId: '', email: '', username: '', jwtToken: '', systemIds: []}
                obj.userId = res.data.id;
                obj.email = res.data.email;
                obj.password = res.data.password;
                obj.username = res.data.username;
                obj.jwtToken = jwtToken;
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

function useAPIUpdateUser(userId, jwtToken, newUser) {
    const [response, setResponse] = useState({
        status: 0,
        message: ''
    });
    
    const {user, setUser} = useContext(UserContext);
   
    useEffect(() => {

        function putData() {
            console.log(newUser);
            console.log(jwtToken !== '');
            const url = `/users/${userId}`;
            let body = {id: userId, username: newUser.username, email: newUser.email, password: newUser.password};

            API.put(url, body, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res)//
                if (newUser.password === '') {
                    setUser({...user});
                }
                else {
                    setUser({...user, userId: res.data.id, jwtToken: res.data.jwtToken});
                }
                setResponse({ status: res.status, message: res.statusText });
            }).catch(err => {
                setResponse({ status: err.response.status, message: err.response.data});
                console.log(err);
            });
        }
        
        if (userId !== "") {
            putData();
        }

    }, [userId, jwtToken, newUser]);

    useAPIGetUser(user.userId, user.jwtToken);

    return response;
}

// Systems

function useAPIGetAllSystems(userId, jwtToken) {
    const [response, setResponse] = useState({
        status: 0,
        message: '',
        systems: []
    });

    useEffect(() => {
        
        function getData() {
            const url = `/users/${userId}/systems`;
            API.get(url, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res);
                setResponse({status: res.status, message: res.statusText, systems: res.data});
            }).catch(err => {
                console.log(err);
                setResponse({status: err.status, message: err.response.data, systems: [] });
            });
        }

        if (userId !== "") {
            getData();
        }

    }, [userId, jwtToken])

    return response;
}

function useAPIAddSystem(userId, jwtToken, system) {
    const [response, setResponse] = useState({
        status: 0,
        message: '',
        
    });

    useEffect(() => {
        
        function getData() {
            const url = `/users/${userId}/systems/new-system`;
            API.post(url, system, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res);
                setResponse({status: res.status, message: res.statusText});
            }).catch(err => {
                console.log(err.response.data);
                setResponse({status: err.status, message: err.response.data});
            });
        }

        if (userId !== "") {
            getData();
        }

    }, [userId, jwtToken])

    return response;


}


// Components

function useAPIGetAllComponents(userId, jwtToken, systemId) {
    const [response, setResponse] = useState({
        status: 0,
        message: '',
        components: []
    });

    useEffect(() => {
        
        function getData() {
            const url = `/users/${userId}/systems/${systemId}/components`;
            API.get(url, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res);
                setResponse({status: res.status, message: res.statusText, components: res.data});
            }).catch(err => {
                console.log(err);
                setResponse({status: err.status, message: err.response.data, components: [] });
            });
        }

        if (userId !== "" && systemId !== "") {
            getData();
        }

    }, [userId, jwtToken, systemId])

    return response;
}

function useAPIAddComponent(userId, jwtToken, systemId, component) {
    const [response, setResponse] = useState({
        status: 0,
        message: '',
        
    });

    useEffect(() => {
        
        function getData() {
            const url = `/users/${userId}/systems/${systemId}/components/new-component`;
            console.log(systemId)
            API.post(url, component, { headers: { Authorization: `Bearer ${jwtToken}` } }).then(res => {
                console.log(res);
                setResponse({status: res.status, message: res.statusText});
            }).catch(err => {
                console.log(err.response.data);
                setResponse({status: err.status, message: err.response.data});
            });
        }

        if (userId !== "" && systemId !== "") {
            getData();
        }

    }, [userId, jwtToken])

    return response;

}

export {
    useAPIRegister,
    useAPILogin,
    useAPIGetUser,
    useAPIUpdateUser,
    useAPIGetAllSystems,
    useAPIGetAllComponents,
    useAPIAddSystem,
    useAPIAddComponent
}