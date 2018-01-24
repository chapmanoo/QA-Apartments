import React from 'react';

import ApartmentListItem from './ApartmentListItem';

class ApartmentList extends React.Component {
  render() {
      const {apartments,getApartment, addRoom}=this.props;
    return (
            <table>
            {
              apartments.map(apartment => 
                   <ApartmentListItem addRoom={addRoom} getApartment={getApartment} apartment={apartment}/>
              )
            }
            </table>
    );
  }
}

export default ApartmentList;