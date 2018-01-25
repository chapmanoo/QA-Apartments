import React from 'react';

import { baseUrl } from './helperFunctions';

function create(personObject){
    const url = `${baseUrl}person/json`;
    const fetchData = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: personObject
    };

    fetch(url, fetchData)

}
class AddPerson extends React.Component {
    constructor() {
        super();
        this.state = {
            person: {

            }
        }
    }

    
    addPerson=()=>{
        const stateToSend = {...this.state.person}
        console.log(stateToSend)
        create(JSON.stringify(stateToSend))

    }

    onChange=e=>{
        this.setState({person:{...this.state.person,[e.target.id]: e.target.value}})
    }

    render() {
        return (
            <div>
                <h2>Lets get adding a person son</h2>
                First name
                <input type='text' id='firstName' onChange={this.onChange} placeholder='First name'/>*<br/>
                Last name
                <input type='text' id='lastName' onChange={this.onChange} placeholder='Last name'/>*<br/>
                email
                <input type='text' id='Email' onChange={this.onChange} placeholder='Email'/>*<br/>
                mobile
                <input type='text' id='PhoneNumber' onChange={this.onChange} placeholder='Phone number'/>*<br/>
                 <button onClick={()=>this.addPerson()}> Submit </button>
                {/* "firstName": "James",
	"lastName": "Herbert",
	"email": "test@test.com",
	"phoneNumber": "01234567891" */}
            </div>
        );
    }
};


export default AddPerson;