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
            apartment:{
                addressField:{}
            }
        }
    }

    onChange=e=>{
        // console.log(e.target.value)
        this.setState({apartment:{...this.state.apartment,[e.target.id]: e.target.value}})
        console.log(this.state)
    }

       onChangeAdd=e=>{
        // console.log(e.target.value)
        this.setState({apartment:{...this.state.apartment,addressField:{...this.state.apartment.addressField,[e.target.id]: e.target.value}}})
        console.log(this.state)
    }

    addApartment=()=>{
        let stateToSend = {...this.state.apartment}
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
                <input type='text' id='address' onChange={this.onChange} placeholder='Address line 1'/>*<br/>
                <input type='text' id='apartmentNum'onChange={this.onChangeAdd} placeholder='apartment number'/><br/>
                <input type='text' id='building'onChange={this.onChangeAdd} placeholder='building name'/><br/>
                <input type='text' id='street'onChange={this.onChangeAdd} placeholder='street'/><br/>
                <input type='text' id='city'onChange={this.onChangeAdd} placeholder='city'/><br/>
            <   input type='text' id='postcode'onChange={this.onChangeAdd} placeholder='Address line 1'/><br/>
                <input type='text' id='deposit' onChange={this.onChange} placeholder='deposit'/>*<br/>
                <button onClick={()=>this.addApartment()}> Submit </button>
            </form>

      

          </div>
        );
      }
    };


export default AddApartment;