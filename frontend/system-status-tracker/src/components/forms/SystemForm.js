import React, { useContext, useState } from 'react'
import { useAPIAddSystem } from '../../custom_hooks/rest_api_hooks';
import ComponentForm from './ComponentForm';
import { TextField } from '@material-ui/core';
import { Container } from '@material-ui/core';
import { Button } from '@material-ui/core';
import { UserContext } from '../context/UserContext';
import { Typography } from '@material-ui/core';
import { Card } from '@material-ui/core';
function SystemForm(props) {

    const {user, setUser} = useContext(UserContext);

    
    const [formData, setFormData] = useState({
        name: '',
        userId: '',
        status: true,
        componentIds: []
    });

    const [newSystem, setNewSystem] = useState({});
    const [userId, setUserId] = useState("");
    const [jwtToken, setJwtToken] = useState("");
    const response = useAPIAddSystem(userId, jwtToken, newSystem);

    function handleName(e) {
        setFormData({...formData, name: e.target.value});
    }

    function handleSubmit(e) {
        e.preventDefault();
        setJwtToken(user.jwtToken);
        setUserId(user.userId);
        setNewSystem({...formData, userId: user.userId})
    }

    return (
        <>
            {props.hide ? <></> :

            <Container>
                <Card>
                    <Typography variant="h6">Add System</Typography>
                    <form onSubmit={handleSubmit}>
                        <div>
                            <TextField label="Name" onChange={e => handleName(e)}></TextField>
                        </div>
                        <Button type="submit" disabled={formData.name === ''}>Submit</Button>
                    </form>
                    {response.status === 200 ? <Typography variant="overline">System added!</Typography> : <></>}
                </Card>
            </Container>
            }
        </>
      
    )
}

export default SystemForm
