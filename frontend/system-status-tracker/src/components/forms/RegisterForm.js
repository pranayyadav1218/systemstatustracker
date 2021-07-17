import React, { useState } from 'react';
import { useAPIRegister } from '../../custom_hooks/rest_api_hooks';

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import { TextField } from '@material-ui/core';
import { Button } from '@material-ui/core';

function RegisterForm() {

    // Form data
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        confirmPassword: ''
    });

    const [confirmPasswordError, setConfirmPasswordError] = useState(false);

    // API call
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const response = useAPIRegister(email, username, password);

    function handleUsername(e) {
        setFormData({...formData, username: e.target.value});
    }
    
    function handlePassword(e) {
        setFormData({...formData, password: e.target.value});
        if (e.target.value === formData.confirmPassword) {
            setConfirmPasswordError(false);
        } 
        else {
            setConfirmPasswordError(true);
        }
    }

    function handleConfirmPassword(e) {
        setFormData({...formData, confirmPassword: e.target.value});
        if (formData.password === e.target.value) {
            setConfirmPasswordError(false);
        } 
        else {
            setConfirmPasswordError(true);
        }
    }

    function handleEmail(e) {
        setFormData({...formData, email: e.target.value});
    }


    function handleSubmit(e) {        
        e.preventDefault();
        if (confirmPasswordError === false) {
            setUsername(formData.username);
            setPassword(formData.password);
            setEmail(formData.email);
        }
    }

    return (
        <Container>
             <form onSubmit={handleSubmit}>
                    <div>
                        <TextField label="Email" type="email" required onChange={handleEmail} />
                    </div>
                    <div>
                        <TextField label="Username" required onChange={handleUsername} />
                    </div>
                    <div>
                        <TextField label="Password" type="password" required onChange={handlePassword} />
                    </div>
                    <div>
                        <TextField label="Confirm Password" type="password" required onChange={handleConfirmPassword} error={confirmPasswordError} />
                    </div>     
                    <div>
                        <Button type="submit" disabled={confirmPasswordError}>Submit</Button>
                    </div> 
            </form>
            
            {response.status >= 400 ? <Typography variant="overline" color="error">{response.message}</Typography> : <></>}

            {response.status === 200 ? <Typography variant="button" color="textSecondary">Account Registered</Typography> : <></>}
        </Container>
    )
}

export default RegisterForm
