import React, { Component } from 'react';
import {Route,
  BrowserRouter as Router,
  Redirect,
  NavLink,
  Switch,
  Link 
} from 'react-router-dom';

import logo from './logo.svg';
import Home from './components/Home';
import Apartment from './components/Apartment';
import AddApartment from './components/AddApartment';
import AddPerson from './components/AddPerson';
import Calendar from './components/Calendar';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
         <div className ="bodyDiv">
          <div className="container-fluid">
                    <ul className="header nav navbar-nav">
                      <li><NavLink to="/" exact activeClassName="active">Home</NavLink></li>  
                      <li><NavLink to="/apartment" activeClassName="active">Apartment</NavLink></li>
                      <li><NavLink to="/calendar" activeClassName="active">Calendar</NavLink></li>
                    </ul>
          </div>

          <Switch>
          <div className = "content">
            <Route exact path = "/" component={Home}/>
            <Route exact path = "/apartment" component={Apartment}/>
            <Route path = "/apartments/addApartment" component={AddApartment}/>
            <Route path = "/addPerson" component={AddPerson}/>
            <Route path = "/calendar" component={Calendar}/>
      
          </div>
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
