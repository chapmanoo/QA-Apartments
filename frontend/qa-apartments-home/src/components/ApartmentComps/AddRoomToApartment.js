import React from 'react';

import {baseUrl} from '../helperFunctions';

function create(roomObject,id){
    const url = baseUrl + 'room/addRoom';
    const fetchData = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: roomObject
    };

    fetch(url, fetchData).then((response)=>{
        alert(JSON.stringify(response));
      });
}

class AddRoomToApartment extends React.Component {
    
    addRoom = (id)=>{
        const roomObject = {
            "apartment" : { "id" : id }
        }
        create(JSON.stringify(roomObject),id)
    }

    render(){
         const {id} = this.props;
      return (
        <button onClick={()=>this.addRoom(id)}>add room</button>
      );
    };
}


export default AddRoomToApartment;