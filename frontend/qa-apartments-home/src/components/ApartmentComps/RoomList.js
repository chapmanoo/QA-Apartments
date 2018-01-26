import React from 'react';
import { baseUrl } from '../helperFunctions';
class RoomList extends React.Component {
  constructor() {
    super();
    this.state = {
      schedule:[]
    }
  }
   
  viewRoomDetails=(id)=>{
         let fetchData = {
          method: 'GET',
        };
        let url = `${baseUrl}schedule/json`;
        fetch(url,fetchData)
        .then(function(data){
            if (data) return data.json()
        })
  }

  render() {
      const {room}=this.props;
    return (
      <div>
         <tr>Room {room.roomId}</tr>
         <tr><button onclick={()=>this.viewRoomDetails(room.roomId)}>view details</button></tr>
      </div>
    );
  }
}

export default RoomList;