import React from 'react';

import { Card } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import ComponentsCard from './ComponentsCard';

function SystemCard(props) {
    const styles = {
        statusColor: (props.system.status ? "primary" : "secondary"),
    }
    return (
        <div>
            <Card>
                <Typography variant="body1" color={styles.statusColor}>{props.system.name}</Typography>
                <ComponentsCard systemId={props.system.id}/>
            </Card>
        </div>
    )
}

export default SystemCard
