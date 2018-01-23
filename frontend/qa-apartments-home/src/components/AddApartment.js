import React from 'react';

import {baseUrl} from './helperFunctions';

function create(apartmentObject){

    let url = baseUrl + "apartment/json";

    let fetchData = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: apartmentObject
    };

    fetch(url, fetchData).then((response)=>{
        alert(response);
      });

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

        let apartNum = document.getElementById("apartmentNum").value;
        let building = document.getElementById("building").value;
        let street = document.getElementById("street").value;
        let city = document.getElementById("city").value;
        let postcode = document.getElementById("postcode").value;

        let stateToSend = {...this.state.apartment}
        stateToSend.addressField = {
            "apartmentNum":apartNum,
            "building":building,
            "street":street,
            "city":city,
            "postCode":postcode
        };
        alert(JSON.stringify(stateToSend));
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

            <input type='text' id='apartmentNum'/><br/>
            <input type='text' id='building'/><br/>
            <input type='text' id='street'/><br/>
            <input type='text' id='city'/><br/>
            <input type='text' id='postcode'/><br/>

          </div>
        );
      }
    };


export default AddApartment;