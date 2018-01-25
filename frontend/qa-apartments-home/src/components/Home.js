import React from 'react';
import {NavLink} from 'react-router-dom';
import Notifications from './NotificationComp/HomeNotifications'

class Home extends React.Component {
  
      render(){
        return (
          <div className='pageheader'>
            <div className='row'>
              <div className="col-md-6">
                <h2>QA Apartments</h2>
                <p>
                <NavLink to="/apartments/addApartment" exact activeClassName="active"><button>Add Apartment</button></NavLink>
                </p>
                <p>
                button with add person to apartment <br/>
                <NavLink to="/AddPerson" activeClassName="active"><button>Add Person</button></NavLink>
                </p>

                <p>notifications</p>
                <Notifications/>
                </div>
              </div>
          </div>
        );
      }
    };


export default Home;