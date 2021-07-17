import React, { useContext } from 'react';
import { Link, useHistory } from 'react-router-dom';

import { Typography } from '@material-ui/core';
import { AppBar } from '@material-ui/core';
import { Box } from '@material-ui/core';
import { Button } from '@material-ui/core';
import { defaultUser, UserContext } from './context/UserContext';
import { ButtonGroup } from '@material-ui/core';
import { Grid } from '@material-ui/core';

function LogoutButton() {
    const {user, setUser} = useContext(UserContext);
    function handleClick() {
        setUser(defaultUser);
    }
    return (
        <Button onClick={handleClick} variant="text" size="small">Logout</Button>
    )
}

function LoginRegisterButtons() {
    const history = useHistory();
    function handleLogin() {
        history.push("/login")
    }
    function handleRegister() {
        history.push("/register")
    }
    return (
        <ButtonGroup size="small" variant="">
            <Button onClick={handleLogin}>Login</Button>
            <Button onClick={handleRegister}>Register</Button>
        </ButtonGroup>        
    )
}


function Navbar() {
    const {user, setUser} = useContext(UserContext);
    
    return (

            <AppBar position="sticky" elevation={0} color="transparent" variant="outlined" gutterBottom>
                    <Grid container alignItems="flex-end">
                        <Grid item xs></Grid>
                        <Grid item xs={6}>
                            <Link to="/home" style={{textDecoration: "none", color: "black"}}>
                                <Typography variant="h3" gutterBottom>System Status Tracker</Typography>
                            </Link>
                        </Grid>
                        <Grid item xs>
                            {user.userId === "" ? <LoginRegisterButtons/> : <LogoutButton/>}
                        </Grid>

                    </Grid>
                    
                    
                  

            </AppBar>
    )
}

export default Navbar
