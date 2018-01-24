import React from 'react';

class RoomSchedule extends React.Component {
  render() {
      const {roomInfo}=this.props;
    return (
      'hello'
      // <p key = {roomInfo.id}>Person:{roomInfo.personID.firstName + " " + roomInfo.personID.lastName} From:{roomInfo.from_date} To:{roomInfo.to_date}</p>
    );
  }
}

export default RoomSchedule;

