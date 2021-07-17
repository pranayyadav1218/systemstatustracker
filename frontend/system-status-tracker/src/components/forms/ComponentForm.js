import { Button, TextField, Typography, Container } from '@material-ui/core';
import React, { useContext, useState } from 'react'
import { useAPIAddComponent } from '../../custom_hooks/rest_api_hooks';
import { UserContext } from '../context/UserContext';

function ComponentForm(props) {
    const {user, setUser} = useContext(UserContext);
    const [formData, setFormData] = useState({
        name: '',
        status: true,
        systemId: '',
        userId: ''
    })

    const [component, setComponent] = useState({});

    const [userId, setUserId] = useState('');
    const [jwtToken, setJwtToken] = useState('');

    const response = useAPIAddComponent(userId, jwtToken, props.system.id, component)

    function handleName(e) {
        setFormData({...formData, name: e.target.value});
    }

    function handleSubmit(e) {
        e.preventDefault();
        setJwtToken(user.jwtToken);
        setUserId(user.userId);
        setComponent({...formData, userId: user.userId, systemId: props.system.id});
    }

    return (
        <Container>
            <Typography variant="h6">Add Component</Typography>
            <form onSubmit={handleSubmit}>
                <div>
                    <TextField label="Name" onChange={e => handleName(e)}></TextField>
                </div>
                <Button type="submit" size="small" disabled={formData.name === ""}>Submit</Button>
            </form>
        </Container>
    )
}

export default ComponentForm
