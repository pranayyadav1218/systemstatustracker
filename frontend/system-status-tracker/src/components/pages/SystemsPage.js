import React, { useContext, useState } from 'react'
import { useAPIGetAllSystems } from '../../custom_hooks/rest_api_hooks';
import SystemCard from '../Cards/SystemCard';
import { UserContext } from '../context/UserContext';
import SystemForm from '../forms/SystemForm';
import { Container, Typography } from '@material-ui/core';
import { Grid } from '@material-ui/core';
import { Button } from '@material-ui/core';

function SystemsPage() {

    const { user, setUser } = useContext(UserContext);

    const response = useAPIGetAllSystems(user.userId, user.jwtToken);

    const [hideForm, setHideForm] = useState(true);

    function handleAddSystem() {
        setHideForm(!hideForm);
    }

    return (
        <Container>
            <Grid container>
                <Grid container justify="center">
                    <Grid item xs={9}>
                        <Typography variant="h5">Your Systems</Typography>
                    </Grid>
                </Grid>
            
                <Grid container>
                    <SystemForm hide={hideForm}></SystemForm>
                </Grid>
                <Grid container direction="row" justify="flex-start" alignItems="center" spacing={4}>
                    {response.status === 200 ? 
                        response.systems.map((sys) => {
                            return (
                                <Grid item xs={12} sm={12} md={4} lg={4}>
                                    <SystemCard key={sys.id} system={sys}/>
                                </Grid>                    
                            )
                        })               
                        :
                        <></>
                    }
                    <Grid item xs={12} md={4}>
                        <Button onClick={handleAddSystem} variant="outlined" size="small">+</Button>
                    </Grid>
                </Grid>
            
            </Grid>
        </Container>
    )
}

export default SystemsPage
