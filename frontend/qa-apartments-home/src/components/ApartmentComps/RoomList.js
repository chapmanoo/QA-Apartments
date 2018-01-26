import React from 'react';
import { baseUrl } from '../helperFunctions';
class RoomList extends React.Component {
  constructor() {
    super();
    this.state = {
      schedule:[]
    }

    this.getSchedule = this.getSchedule.bind(this);
  }
  

  getSchedule = (id) =>{
    let fetchData = {
      method: 'GET'
    };
    let url = `${baseUrl}schedule/json`;
    fetch(url,fetchData)
    .then(function(data){
        return data.json();
    })
    .then(function(data){

      let scheduleList = [];

      for(let x in data){
        scheduleList.push(data[x]);
      }

      this.setState({schedule: []})
      this.setState({schedule: scheduleList})
    });


  }

  render() {
      const {room}=this.props;
    return (
      <div>
         <tr>Room {room.roomId}</tr>
         <button onClick={this.getSchedule(room.roomId)}> Get Room Schedule </button>
         {
              this.state.schedule.map((roomInfo) => (
                <p key = {roomInfo.id}>Person:{roomInfo.personID.firstName + " " + roomInfo.personID.lastName} From:{roomInfo.from_date} To:{roomInfo.to_date}</p>
              ))
          }
      </div>
    );
  }
}

export default RoomList;