import React from 'react';
import LoginForm from '../forms/LoginForm';

import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { Box } from '@material-ui/core';

function LoginPage() {
    return (
        <Container>
            <Box>
                <Typography variant="h4">Login</Typography>
            </Box>
           
            <LoginForm/>            
        </Container>
    )
}

export default LoginPage
