import React, { useContext } from 'react';

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import { UserContext } from '../context/UserContext';


function HomePage() {

    const {user, setUser} = useContext(UserContext);
    return (
        <Container>
            <Typography variant="h4">Home Page</Typography>
            {user.userId !== "" ? <Typography variant="h6">Welcome {user.username}!</Typography> : <></>}
        </Container>
    )
}

export default HomePage
