import React, { useContext } from 'react';
import { useAPIGetAllComponents } from '../../custom_hooks/rest_api_hooks';

import { Card } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import { UserContext } from '../context/UserContext';

function ComponentsCard(props) {
    const { user, setUser } = useContext(UserContext);
    const response = useAPIGetAllComponents(user.userId, user.jwtToken, props.systemId)
    
    return (
        <Card>
            {response.status !== 200 ? <></> :
                response.components.map((comp) => {
                    return <Card key={comp.id}><Typography variant="caption">{comp.name}</Typography></Card>
                })            
            }
        </Card>
    )
}

export default ComponentsCard
