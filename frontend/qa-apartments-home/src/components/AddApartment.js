import React from 'react';
import DatePicker from 'react-datepicker';
import moment from 'moment';

import 'react-datepicker/dist/react-datepicker.css';

import {baseUrl} from './helperFunctions';

function create(apartmentObject){
    const url = baseUrl + "apartment/json";
    const fetchData = {
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

        },
        apartment:{
           addressField:{},
           leaseStart: moment(),
           leaseEnd: moment(),
           breakClause: moment()
        }
       
      }
    }

    getFullDate = (dateRequired) => {
      const year = dateRequired._d.getFullYear().toString()
      let month = Number((dateRequired._d.getMonth()) + 1).toString()
      let day = dateRequired._d.getDate().toString()
      if(month < 10)
      {
        month = 0 + month;
      }
      if(day < 10)
      {
        day = 0 + day;
      }
      return year + "-" + month + "-" + day;

    }

    onChange=e=>{
        this.setState({apartment:{...this.state.apartment,[e.target.id]: e.target.value}})
        console.log(this.state)
    }

       onChangeAdd=e=>{
        // console.log(e.target.value)
        this.setState({apartment:{...this.state.apartment,addressField:{...this.state.apartment.addressField,[e.target.id]: e.target.value}}})
        console.log(this.state)
    }

    addApartment=()=>{
        const stateToSend = {...this.state.apartment}
        let startLeaseDate = stateToSend.leaseStart;
        let endLeaseDate = stateToSend.leaseEnd;
        let breakClauseDate = stateToSend.breakClause;
        startLeaseDate = this.getFullDate(startLeaseDate);
        endLeaseDate = this.getFullDate(endLeaseDate);
        breakClauseDate = this.getFullDate(breakClauseDate);
        stateToSend.leaseStart = startLeaseDate;
        stateToSend.leaseEnd = endLeaseDate;
        stateToSend.breakClause = breakClauseDate;
        stateToSend.noRooms = Number(stateToSend.noRooms);
        stateToSend.deposit = (Number(stateToSend.deposit));
        stateToSend.rent = Number(stateToSend.rent);
        create(JSON.stringify(stateToSend))

    }

    handleChange =(date)=> {
        console.log(this.startId)
            this.setState({
                apartment:{...this.state.apartment, 
                [this.startId.input.id+'value']: this.startId.props.selected._d,
                [this.endId.input.id+'value']: this.endId.props.selected._d,
                [this.breakId.input.id+'value']: this.breakId.props.selected._d
            }});
      }

    
      render(){
        return (
          <div className='pageheader'>
            <h2>Lets get adding son</h2>
                <i>* indicates required fields</i><br/>
                <input type='text' id='buildingName' onChange={this.onChange} placeholder='Building name'/>*<br/>
                <input type='text' id='apartmentNo' onChange={this.onChange} placeholder='Apartment number'/>*<br/>
                <input type='text' id='agency' onChange={this.onChange} placeholder='Agency'/>*<br/>
                <input type='text' id='landlord' onChange={this.onChange} placeholder='Landlord'/><br/>
                <input type='text' id='tenant' onChange={this.onChange} placeholder='Tenant'/>*<br/>
                <DatePicker id='leaseStart' ref={node => this.startId = node} selected={this.state.apartment.leaseStart} onChange={this.handleChange}/>
                <DatePicker id='leaseEnd' ref={node => this.endId = node} selected={this.state.apartment.leaseEnd} onChange={this.handleChange}/>
                <DatePicker id='breakClause' ref={node => this.breakId = node} selected={this.state.apartment.breakClause} onChange={this.handleChange}/>
                <input type='text' id='agencyPhoneNo' onChange={this.onChange} placeholder='Agency Telephone Number'/>*<br/>
                <input type='text' id='noRooms' onChange={this.onChange} placeholder='Number of rooms'/><br/>
                <input type='text' id='rent' onChange={this.onChange} placeholder='Rent: 00.00'/>*<br/>
                <input type='text' id='notes' onChange={this.onChange} placeholder='Notes here...'/><br/>
                <input type='text' id='address' onChange={this.onChange} placeholder='Address line 1'/>*<br/>
                <input type='text' id='apartmentNum'onChange={this.onChangeAdd} placeholder='apartment number'/><br/>
                <input type='text' id='building'onChange={this.onChangeAdd} placeholder='building name'/><br/>
                <input type='text' id='street'onChange={this.onChangeAdd} placeholder='street'/><br/>
                <input type='text' id='city'onChange={this.onChangeAdd} placeholder='city'/><br/>
                <input type='text' id='postcode'onChange={this.onChangeAdd} placeholder='Address line 1'/><br/>
                <input type='text' id='deposit' onChange={this.onChange} placeholder='deposit'/>*<br/>
                <button onClick={()=>this.addApartment()}> Submit </button>

          </div>
        );
      }
    };


export default AddApartment;