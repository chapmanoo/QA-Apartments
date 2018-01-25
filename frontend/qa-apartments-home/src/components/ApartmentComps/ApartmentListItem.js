import React from 'react';

class ApartmentListItem extends React.Component {
  render() {
      const {apartment,getApartment,addRoom}=this.props;
    return (
      <tbody>
      <tr>
        <td>
          {apartment.apartmentNo}
          </td>        
          <td>
          {apartment.buildingName}
          </td>       
                   <td>
          {apartment.landlord}
          </td>    
          <td>
          {apartment.noRooms}
          </td>
          <td>
          <button  onClick={()=>getApartment(apartment.id)}>get apartment details</button>
          </td>
          <td>
          <button  onClick={()=>addRoom(apartment.id)}>add room</button>
          </td>
          </tr>
        </tbody>
    );
  }
}

export default ApartmentListItem;