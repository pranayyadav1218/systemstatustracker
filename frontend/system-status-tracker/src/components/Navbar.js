import React, { useContext } from 'react';
import { Link, useHistory } from 'react-router-dom';

import { Typography } from '@material-ui/core';
import { AppBar } from '@material-ui/core';
import { Box } from '@material-ui/core';
import { Button } from '@material-ui/core';
import { defaultUser, UserContext } from './context/UserContext';

function LogoutButton(props) {
    const {user, setUser} = useContext(UserContext);
    function handleClick() {
        setUser(defaultUser);
    }
    return (
        <>
            {props.hide ? <></> : <Button onClick={handleClick}>Logout</Button>}
        </>
    )
}

function LoginRegisterButtons(props) {
    const history = useHistory();
    function handleLogin() {
        history.push("/login")
    }
    function handleRegister() {
        history.push("/register")
    }
    return (
        <>
            {props.hide ? <></> : 
                <Box display="inline" justifyContent="flex-end" left={100}>
                    <Button onClick={handleLogin} size="small">Login</Button>
                    <Button onClick={handleRegister} size="small">Register</Button>
                </Box>
            }
        </>
    )
}

function Navbar() {
    const {user, setUser} = useContext(UserContext);

    return (
       
            <AppBar position="sticky" elevation={0} color="transparent">
                <Box borderTop={5} borderBottom={5} justifyContent="flex-start">
                    <Box display="inline" justifyContent="flex-start" alignItems="flex-start">
                        
                        <Link to="/home" style={{textDecoration: "none"}}>
                            <Typography variant="overline" style={{fontSize: "1.5rem", color: "black"}} display="inline">System Status Tracker</Typography>
                        </Link>
                    </Box>
                    <LoginRegisterButtons hide={user.userId !== ""}/>
                    <LogoutButton hide={user.userId === ""}/>
                </Box>
            </AppBar>
    )
}

export default Navbar
