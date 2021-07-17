import React, { useState } from 'react';
import { useAPILogin } from '../../custom_hooks/rest_api_hooks';

import Container from '@material-ui/core/Container';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

function LoginForm() {
    // Form data
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    // API call
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const response = useAPILogin(username, password);
   
    function handleUsername(e) {
        setFormData({...formData, username: e.target.value});
    }
    
    function handlePassword(e) {
        setFormData({...formData, password: e.target.value});
    }

    function handleSubmit(e) {
        e.preventDefault();
        setUsername(formData.username);
        setPassword(formData.password);
    }

    return (
        <Container>
             <form onSubmit={handleSubmit}>
                    <div>
                        <TextField label="Username" onChange={handleUsername} />
                    </div>
                    <div>
                        <TextField label="Password" type="password" onChange={handlePassword} />
                    </div>     
                    <div>
                        <Button type="submit">Submit</Button>
                    </div> 
            </form>
            
            {response.status >= 400 ? <Typography variant="overline" color="error">{response.message}</Typography> : <></>}

        </Container>
    )
}

export default LoginForm
