import React from 'react';

import ApartmentList from './ApartmentComps/ApartmentList';
import RoomList from './ApartmentComps/RoomList';
import RoomSchedule from './ApartmentComps/RoomSchedule';

const pjson = require('./BackendURL.json');

class Apartment extends React.Component {

      constructor() {
        super();

    this.state = {
      stateText:[],
      roomText:[],
      roomSchedule:[]
    };
  }

  componentDidMount=()=>{
    console.log('mounting')
    this.getApartmentList();
  }

  getApartment = () => {

    let selectBox = document.getElementById("apartmentSelect");
    let selectedValue = selectBox.options[selectBox.selectedIndex].value;
    let id;
    let url = pjson.URL + "/qa-apartments3/Rest/apartment/json/";

    let leaseStartBox = document.getElementById("leaseStartBox");
    let leaseEndBox = document.getElementById("leaseEndBox");
    let breakClauseBox = document.getElementById("breakClauseBox");

    url = url + selectedValue;

    let fetchData = { 
      method: 'GET',
      mode: 'no-cors'
    };
   
    fetch(url,fetchData)
    .then(response=>{
        //If a 2xx response is received return the response as JSON
        if(response.ok){  
          return response.json();  
        }
        //Throw an error otherwise and jump to the catch statement
        throw Error(response.statusText);
    })
    .then(response=>{
      //Do stuff with the JSON
      //alert(JSON.stringify(response));
      leaseStartBox.value = response.leaseStart;
      leaseEndBox.value = response.leaseEnd;
      breakClauseBox.value = response.breakClause;
      id = response.id;
      //  console.log('getting room details for selected Value: '+selectedValue)
    
    })
    .then(()=>
        this.getAllRooms(String(id))
    )
    .catch(error=>{
      console.log("Request Failed: " + error.message);
    });
    
}

getApartmentList = () => {

  let url = 'http://localhost:8080/qa-apartments3/Rest/apartment/json/';
  let fetchData = { 
    method: 'GET',
    mode: 'no-cors'
  };

  fetch(url,fetchData)
  .then(response=>
       response.ok?response.json():Error(response.statusText)
  )
  .then(response=>{
    console.log('adding stuf')
    const ids = response.reduce((acc,apartment)=>{
      acc.push(apartment.id);
      return acc;
    },[])
    console.log(ids)
     this.setState({stateText:ids});
  })
  .catch(error=>{
    console.log("Request Failed: " + error.message);
  });

}

getAllRooms = (idValue) => {

  let url = "http://localhost:8080/qa-apartments3/Rest/room/getRoom/";
  
  let fetchData = { 
    method: 'GET',
    mode: 'no-cors'
  };

  fetch(url,fetchData)
  .then(response=>
    response.ok?response.json():Error(response.statusText)
  )
  .then(data=>
     data.reduce((acc,room)=>{
        if(String(idValue)===String(room.apartment.id)){
          acc.push(room.roomId);
        }
      return acc;
    },[]))
  .then((roomIds)=>{
      this.setState({roomText:roomIds});
  })
  .catch(error=>{
    console.log("Request Failed: " + error.message);
  });
}

getRoomDetails = () => {
  let selectBox = document.getElementById("roomSelect");
  let selectedValue = selectBox.options[selectBox.selectedIndex].value;
  let url = 'http://localhost:8080/qa-apartments3/Rest/schedule/json';

  let fetchData = { 
    method: 'GET',
    mode: 'no-cors'
  };

  fetch(url,fetchData)
  .then(response=>{
      //If a 2xx response is received return the response as JSON
      if(response.ok){  
        return response.json();  
      }
      //Throw an error otherwise and jump to the catch statement
      throw Error(response.statusText);
  })
  .then(response=>{
    const roomData = response.reduce((acc,room)=>{
      console.log(room)
        if(String(selectedValue) === String(room.roomID.roomId)){
          acc.push(room);
        }
      return acc;
    },[])
    console.log(roomData)
    this.setState({roomSchedule:roomData});
  })
  .catch(error=>{
    console.log("Request Failed: " + error.message);
  });





}

  render() {
    return (
      <div>
        <div>
         <ApartmentList list={this.state.stateText}/>
            <br/>
            <button id="getApartment"onClick={()=>this.getApartment()}>Get Apartment</button><br/>
            <button id="getAllApartments" onClick={()=>this.getApartmentList()}>Refresh List</button>
        </div>
        <br/> <br/> 
        <hr/>
        <div> 
          <select id="roomSelect">
          {
              this.state.roomText.map(room => 
                <RoomList room={room}/>
              )
          } 
          </select>
          <button id="getRoomInfo" onClick={()=>this.getRoomDetails()}>Get Room Details</button><br/>
          {
              this.state.roomSchedule.map(roomInfo => 
                <RoomSchedule roomInfo={roomInfo}/>
              )
          }
        </div>
        <br/> <br/> <br/> <br/>
        <hr/>
        <div>
            <p>Misc Section</p>
            <label for="leaseStartBox">Lease Start</label>
            <input type="text" name="lease_start" id="leaseStartBox" placeholder="Lease Start"/>
            <label for="leaseEndBox">Lease End</label>
            <input type="text" name="lease_end" id="leaseEndBox" placeholder="Lease End"/>
            <label for="breakClauseBox">Break Clause</label>
            <input type="text" name="break_clause" id="breakClauseBox" placeholder="Break Clause"/>
        </div>
      </div>
    );
  }
}

export default Apartment;