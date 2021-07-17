import React, { useContext } from 'react';
import { useAPIGetAllComponents } from '../../custom_hooks/rest_api_hooks';

import { Card, Container, CardHeader, CardContent, CardActionArea, Button } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import { UserContext } from '../context/UserContext';

function ComponentCard(props) {
    const { user, setUser } = useContext(UserContext);
    const response = useAPIGetAllComponents(user.userId, user.jwtToken, props.systemId)
    
    return (
    
            <Card>
                <CardContent>
                    <Typography variant="body1" >{props.component.name}</Typography>
                    <Typography variant="caption">Status: {props.component.status ? "OK" : "Problem Detected!"}</Typography>
                </CardContent>
                <Button>Edit</Button>
                
            </Card>
  
       
    )
}

export default ComponentCard
