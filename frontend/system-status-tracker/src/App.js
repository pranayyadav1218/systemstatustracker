import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';

import './App.css';

import HomePage from './components/pages/HomePage';
import LoginPage from './components/pages/LoginPage';
import RegisterPage from './components/pages/RegisterPage';
import Navbar from './components/Navbar';
import UserContextProvider from './components/context/UserContext';
import SystemsPage from './components/pages/SystemsPage';

function App() {
  return (
    <Router>
      <div className="App">
        <UserContextProvider>
          <div>
            <Navbar></Navbar>
          </div>
          <Switch>
            <Route exact path="/home" component={HomePage}/>
            <Redirect exact from="/" to="/home"/>
            <Route exact path="/login" component={LoginPage}/>
            <Route exact path="/register" component={RegisterPage}/>
            <Route exact path="/systems" component={SystemsPage}/>
          </Switch>
        </UserContextProvider>
      </div>
    </Router>
  );
}

export default App;
