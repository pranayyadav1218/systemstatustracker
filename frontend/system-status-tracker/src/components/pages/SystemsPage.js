import React, { useContext } from 'react'
import { useAPIGetAllSystems } from '../../custom_hooks/rest_api_hooks';
import SystemCard from '../Cards/SystemCard';
import { UserContext } from '../context/UserContext';

function SystemsPage() {

    const { user, setUser } = useContext(UserContext);

    const response = useAPIGetAllSystems(user.userId, user.jwtToken);
    console.log(user.jwtToken)
    return (
        <div>
            {response.status === 200 ? 
                response.systems.map((sys) => {
                    return <SystemCard key={sys.id} system={sys}/>
                })               
                :
                <></>
            }
        </div>
    )
}

export default SystemsPage
