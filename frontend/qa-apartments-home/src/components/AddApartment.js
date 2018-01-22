import React from 'react';

import {baseUrl} from './helperFunctions';

function create(apartmentObject){
    const request = new Request(`${baseUrl}apartment/json/`, {
        method: "POST",
        headers : {
            'contentType' : 'application/json'
        },
        body : apartmentObject
    });
    return fetch(request).then(()=>'worked', ()=>'failed');
}

class AddApartment extends React.Component {
        constructor(){
        super();
        this.state={
            apartment:{}
        }
    }

    onChange=e=>{
        // console.log(e.target.value)
        this.setState({apartment:{...this.state.apartment,[e.target.id]: e.target.value}})
        console.log(this.state)
    }

    addApartment=()=>{
        const stateToSend = {...this.state.apartment}
        create(JSON.stringify(stateToSend))
    }
    
   
      render(){
        return (
          <div className='pageheader'>
            <h2>Lets get adding son</h2>
            <form id='form'>
                <i>* indicates required fields</i><br/>
                <input type='text' id='buildingName' onChange={this.onChange} placeholder='Building name'/>*<br/>
                <input type='text' id='apartmentNo' onChange={this.onChange} placeholder='Apartment number'/>*<br/>
                <input type='text' id='agency' onChange={this.onChange} placeholder='Agency'/>*<br/>
                <input type='text' id='landlord' onChange={this.onChange} placeholder='Landlord'/><br/>
                <input type='text' id='tenant' onChange={this.onChange} placeholder='Tenant'/>*<br/>
                <input type='text' id='leaseStart' onChange={this.onChange} placeholder='Lease Start: yyyy-mm-dd'/>*<br/>
                <input type='text' id='leaseEnd' onChange={this.onChange} placeholder='Lease End: yyyy-mm-dd'/>*<br/>
                <input type='text' id='breakClause' onChange={this.onChange} placeholder='Break Clause: yyyy-mm-dd'/>*<br/>
                <input type='text' id='agencyPhoneNo' onChange={this.onChange} placeholder='Agency Telephone Number'/>*<br/>
                <input type='text' id='noRooms' onChange={this.onChange} placeholder='Number of rooms'/><br/>
                <input type='text' id='rent' onChange={this.onChange} placeholder='Rent: 00.00'/>*<br/>
                <input type='text' id='notes' onChange={this.onChange} placeholder='Notes here...'/><br/>
                <input type='textblock' id='address' onChange={this.onChange} placeholder='Address line 1'/>*<br/>
                {/* 
                <input type='textblock' id='address2' onChange={this.onChange} placeholder='address line 2'/><br/>
                <input type='textblock' id='address3' onChange={this.onChange} placeholder='address line 3'/><br/>
                <input type='textblock' id='postCode' onChange={this.onChange} placeholder='Post Code'/><br/>
                */}
                <input type='text' id='deposit' onChange={this.onChange} placeholder='deposit'/>*<br/>
                <button onClick={()=>this.addApartment()}> Submit </button>
            </form>
          </div>
        );
      }
    };


export default AddApartment;