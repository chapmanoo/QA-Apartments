import React from 'react';
import ReactDOM from 'react-dom'
import Timeline from 'react-calendar-timeline/lib'
import moment from 'moment'
import ApartmentList from './ApartmentComps/ApartmentList';
import {baseUrl} from './helperFunctions';

class Calendar extends React.Component {
  constructor(){
    super();

    this.state = {
      apartmentArray:[],
      groups:[{id:'loading',title:'loading'}]
    }
  }

  componentDidMount = () => {
     this.getApartmentList();
     setTimeout(()=>{ console.log(this.state)}, 200);
  }

  getApartmentList = () => {
    let url = `${baseUrl}apartment/json/`;
    let fetchData = { 
      method: 'GET'
    };
    fetch(url,fetchData)
    .then(response=>
         response.ok?response.json():Error(response.statusText)
    )
    .then(response=>{
      console.log('adding stuf')
      const apartmentArray = response.reduce((acc,apartment)=>{
        acc.push(apartment);
        return acc;
      },[])
      const groups = apartmentArray.reduce((acc,apartment)=>{
        const obj = {id: apartment.id, title:apartment.apartmentNo};
        acc.push(obj);
        return acc;
      },[])
      console.log('apartmentArray:',apartmentArray)
       this.setState({apartmentArray,groups});
    })
    .catch(error=>{
      console.log("Request Failed: " + error.message);
    }); 
  }

  render(){
    // setTimeout(()=>{ console.log(this.state)}, 200);
   const items = [
      {id: 1, group: 1, title: 'item 1', start_time: moment(), end_time: moment().add(1, 'hour')},
      {id: 2, group: 2, title: 'item 2', start_time: moment().add(-0.5, 'hour'), end_time: moment().add(0.5, 'hour')},
      {id: 3, group: 1, title: 'item 3', start_time: moment().add(2, 'hour'), end_time: moment().add(3, 'hour')}
    ]
    console.log(this.state.groups);
    return (
      
      <div>
      <Timeline groups={this.state.groups}
                items={items}
                defaultTimeStart={moment().add(-12, 'hour')}  
                defaultTimeEnd={moment().add(12, 'hour')}
                />
    </div>);
  }
    
}

export default Calendar;