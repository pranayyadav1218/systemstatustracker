import React, { useContext, useState } from 'react';

import { Card } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import { UserContext } from '../context/UserContext';
import { TextField } from '@material-ui/core';
import { Button } from '@material-ui/core';
import { useAPIUpdateUser } from '../../custom_hooks/rest_api_hooks';



function ProfileCard(props) {
    const { user, setUser } = useContext(UserContext);
    const [edit, setEdit] = useState(false);
    const [formData, setFormData] = useState({
        username: (user !== undefined ? user.username : ''),
        email: (user !== undefined ? user.email : ''),
        password: '',
        confirmPassword: ''
    });

    // API Calls
    const [userId, setUserId] = useState('');
    const [jwtToken, setJwtToken] = useState('');
    const [userObj, setUserObj] = useState({
        username: '',
        email: '',
        password: ''
    });
    const response = useAPIUpdateUser(userId, jwtToken, userObj);

    function handleEdit() {
        setEdit(!edit);
    }

    function handleClose() {
        props.setHide(!props.hide)
        setFormData({
            username: (user !== undefined ? user.username : ''),
            email: (user !== undefined ? user.email : ''),
            password: '',
            confirmPassword: ''
        });
    }

    function handleUsername(e) {
        setFormData({...formData, username: e.target.value})
    }

    function handleEmail(e) {
        setFormData({...formData, email: e.target.value})
    }

    function handleSubmit(e) {
        e.preventDefault();
        setUserObj({
            username: (formData.username == user.username ? '' : formData.username), 
            email: (formData.email == user.email ? '' : formData.email), 
            password: formData.password
        });
        setUserId(user.userId);
        setJwtToken(user.jwtToken);
    }

    return (
        <>
        {props.hide ? <></> :
            <Card>
                <Typography>Your Profile</Typography>
                <Button onClick={handleEdit}>Edit</Button>
                <Button onClick={handleClose}>Close</Button>
                <form onSubmit={handleSubmit}>
                    <div>
                        <TextField label="Username" defaultValue={formData.username} onChange={handleUsername} disabled={!edit}></TextField>
                    </div>
                    <div>
                        <TextField label="Email" defaultValue={formData.email} onChange={handleEmail} disabled={!edit}></TextField>
                    </div>
                
                    {edit === false ? <></> :
                        <>
                            <div>
                                <TextField label="Password" value={formData.password} disabled={!edit}></TextField>
                            </div>
                            <div>
                                <TextField label="Confirm Password" required={formData.password !== ""} value={formData.confirmPassword} disabled={!edit}></TextField>
                            </div>
                        </>
                    }
                    <Button type="submit">Submit</Button>
                </form>
                {response.status === 200 ? <Typography color="green">Updated!</Typography> : <></>}
            </Card>
        }
        </>
    )
}

export default ProfileCard
