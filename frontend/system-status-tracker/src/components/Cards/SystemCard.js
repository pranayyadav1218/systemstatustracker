import React, { useContext, useState } from 'react';

import { Card, Container, CardHeader, CardContent, CardActions, Collapse, Button, ButtonGroup, Grid } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import ComponentCard from './ComponentCard';
import { useAPIGetAllComponents } from '../../custom_hooks/rest_api_hooks';
import { UserContext } from '../context/UserContext';
import ComponentForm from '../forms/ComponentForm';

function SystemCard(props) {
    const styles = {
        statusColor: (props.system.status ? "primary" : "error"),
    }

    const [showDetails, setShowDetails] = useState(false);
    const [showForm, setShowForm] = useState(false);

    // API Stuff
    const {user, setUser} = useContext(UserContext);

    const response = useAPIGetAllComponents(user.userId, user.jwtToken, props.system.id);

    function handleExpand() {
        setShowDetails(!showDetails);
    }

    return (
            <Card>
                <CardHeader title={props.system.name}></CardHeader>
                <CardContent>
                    <Typography variant="body2">Status: {props.system.status ? "OK" : "Problem detected!"}</Typography>
                </CardContent>
                
                <ButtonGroup size="small">
                    <Button>Edit</Button>
                    <Button onClick={() => setShowDetails(!showDetails)}>More</Button>
                </ButtonGroup>
                <Collapse in={showDetails} timeout="auto" unmountOnExit>
                    <Typography variant="h6">Components</Typography>
                    
                    <Collapse in={showForm} timeout="auto" unmountOnExit>
                        <ComponentForm system={props.system}></ComponentForm>
                    </Collapse>
                    <Grid container alignItems="center" justify="space-evenly" spacing={2}>
                        {response.status === 200 ? response.components.map((comp) => {
                                                        return (
                                                            <Grid item xs={12} sm={6} md={4}>
                                                                <ComponentCard component={comp}/>
                                                            </Grid>
                                                            )
                                                    })
                            :
                            <></>
                        }
                        <Grid item xs={12} sm={6} md={4}>
                            <Button onClick={() => setShowForm(!showForm)} variant="outlined">+</Button>
                        </Grid>
                    </Grid>
                    
                    
                </Collapse>
                
            </Card>
    )
}

export default SystemCard
