import React from 'react';

import ApartmentList from './ApartmentComps/ApartmentList';
import RoomList from './ApartmentComps/RoomList';
import RoomSchedule from './ApartmentComps/RoomSchedule';

import { baseUrl } from './helperFunctions';

class Apartment extends React.Component {

  constructor() {
    super();

    this.state = {
      apartments: [],
      roomText: [],
      roomSchedule: [],
      leaseStart: null,
      leaseEnd: null,
      breakClause: null,
      id: null
    };
  }

  componentDidMount = () => {
    this.getApartmentList();
  }

  addRoom = (id) => {
    const roomObject = {
      "apartment": { "id": id }
    }
    const url = baseUrl + 'room/addRoom';

    const fetchData = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(roomObject)
    };

    fetch(url, fetchData)
      .then(() => {
        console.log('added right?')
        this.getAllRooms(id)

      })
      .catch(error => {
        console.log(error)

      })
  };



  getApartment = (id) => {
    const url = `${baseUrl}apartment/json/${id}`;

    let fetchData = {
      method: 'GET'
    };

    fetch(url, fetchData)
      .then(function(response){
          return response.json();
      })
      .then(response => {
        this.setState({
          leaseStart: response.leaseStart,
          leaseEnd: response.leaseEnd,
          breakClause: response.breakClause,
          id: response.id
        })
      })
      .then(() => {
        console.log(this.state.id);
        this.getAllRooms(String(this.state.id))
      })
  }

  getApartmentList = () => {
    let url = `${baseUrl}apartment/json/`;
    let fetchData = {
      method: 'GET'
    };

    fetch(url, fetchData)
      .then(response =>
        response.json()
      )
      .then(response => {

        const apartments = response.reduce((acc, apartment) => {
          acc.push(apartment);
          return acc;
        }, [])
        console.log(apartments)
        this.setState({ apartments });
      })

  }

  getAllRooms = (idValue) => {
    let url = `${baseUrl}room/getRoom`;
    let fetchData = {
      method: 'GET'
    };

    fetch(url, fetchData)
      .then(function(response){
        return response.json();
      }
      )
      .then(data => {
        console.log("get room request", data)
        const roomArray = data.reduce((acc, room) => {
          console.log(room)
          if (String(idValue) === String(room.apartment.id)) {
            acc.push(room);
          }
          return acc;
        }, [])
        console.log("room objects", roomArray)
        return roomArray
      })
      .then((roomIds) => {
        console.log("roomIDs", roomIds);
        this.setState({ roomText: roomIds });
      })
  }

  getRoomDetails = () => {
    let selectBox = document.getElementById("roomSelect");
    let selectedValue = selectBox.options[selectBox.selectedIndex].value;
    let url = `${baseUrl}schedule/json`;

    let fetchData = {
      method: 'GET'
    };

    fetch(url, fetchData)
      .then(function(response){
        return response.json();
      }
      )
      .then(response => {
        const roomData = response.reduce((acc, room) => {
          if (String(selectedValue) === String(room.roomID.roomId)) {
            acc.push(room);
          }
          return acc;
        }, [])
        console.log(roomData)
        this.setState({ roomSchedule: roomData });
      })
  }

  render() {
    return (
      <div>
        <div>
          <ApartmentList addRoom={this.addRoom} apartments={this.state.apartments} getApartment={this.getApartment} />
          <br />
          {/* <button id="getApartment"onClick={()=>this.getApartment()}>Get Apartment</button><br/> */}
        </div>
        <br /> <br />
        <hr />
        <div>
          <table id="roomtable">

            {
              this.state.roomText.map(room =>
                <RoomList room={room} />
              )
            } #

          </table>
          <button id="getRoomInfo" onClick={() => this.getRoomDetails()}>Get Room Details</button><br />
          {
            this.state.roomSchedule.map(roomInfo =>
              <RoomSchedule roomInfo={roomInfo} />
            )
          }
          {/* <AddRoomToApartment id={this.state.id}/> */}
        </div>
        <br /> <br /> <br /> <br />
        <hr />
        <div>
          <p>Misc Section</p>
          <label for="leaseStartBox">Lease Start</label>
          <input type="text" id="leaseStartBox" value={this.state.leaseStart ? this.state.leaseStart : 'Lease Start'} />
          <label for="leaseEndBox">Lease End</label>
          <input type="text" id="leaseEndBox" value={this.state.leaseEnd ? this.state.leaseEnd : 'Lease End'} />
          <label for="breakClauseBox">Break Clause</label>
          <input type="text" id="breakClauseBox" value={this.state.breakClause ? this.state.breakClause : 'Break Clause'} />
        </div>
      </div>
    );
  }
}

export default Apartment;