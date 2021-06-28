import React, { createContext, useState } from 'react'

export const defaultUser = {
    username: '',
    email: '',
    userId: '',
    systemIds: [],
    jwtToken: ''
}


export const UserContext = createContext({ user: defaultUser, setUser: () => {} });

export default function UserContextProvider(props) {
    const [user, setUser] = useState(defaultUser);

    return (
        <UserContext.Provider value={{user: user, setUser: setUser}}>
            {props.children}
        </UserContext.Provider>
    )
}


