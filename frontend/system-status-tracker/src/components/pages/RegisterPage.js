import React from 'react';
import RegisterForm from '../forms/RegisterForm';

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';

function RegisterPage() {

    return (
        <Container>
            <Typography variant="h4">Register</Typography>
            <RegisterForm/>
        </Container>
    )
}

export default RegisterPage
