import React, { useContext, useState } from 'react';
import SystemsPage from './SystemsPage'

import Container from '@material-ui/core/Container';
import Typography from '@material-ui/core/Typography';
import { UserContext } from '../context/UserContext';
import ProfileCard from '../Cards/ProfileCard';
import { Button } from '@material-ui/core';

function HomePage() {

    const {user, setUser} = useContext(UserContext);
    const [hideProfile, setHideProfile] = useState(true);

    return (
        <Container>
           
            {user.userId === "" ? <></> : 
                <div>
                    <Typography variant="h4">Welcome {user.username}!</Typography>
                    <SystemsPage></SystemsPage>
                </div>
            }
        </Container>
    )
}

export default HomePage
