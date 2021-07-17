import React from 'react';
import RegisterForm from '../forms/RegisterForm';

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import { Card } from '@material-ui/core';
function RegisterPage() {

    return (
        <Container>
            <Card>
                <Typography variant="h4">Register</Typography>
                <RegisterForm/>
            </Card>
        </Container>
    )
}

export default RegisterPage
