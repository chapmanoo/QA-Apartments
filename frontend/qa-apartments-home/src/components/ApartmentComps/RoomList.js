import React from 'react';

class RoomList extends React.Component {
  render() {
      const {room}=this.props;
    return (
         <tr>Room id:{room.roomId}</tr>
    );
  }
}

export default RoomList;