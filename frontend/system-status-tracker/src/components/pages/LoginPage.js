import React from 'react';
import LoginForm from '../forms/LoginForm';

import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';
import { Box } from '@material-ui/core';
import { Card } from '@material-ui/core';

function LoginPage() {
    return (
        <Container>
            <Card>
            <Typography variant="h4">Login</Typography>

           
            <LoginForm/>    
            </Card>
                  
        </Container>
    )
}

export default LoginPage
