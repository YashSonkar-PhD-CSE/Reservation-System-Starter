package flight.reservation.plane.adapters;

import flight.reservation.plane.PassengerPlane;
import flight.reservation.plane.Plane;

public class PassengerPlaneAdapter implements Plane {
    private PassengerPlane passengerPlane;

    public PassengerPlaneAdapter(PassengerPlane passengerPlane) {
        this.passengerPlane = passengerPlane;
    }

    @Override
    public int getPassengerCapacity() {
        return this.passengerPlane.passengerCapacity;
    }

    @Override
    public int getCrewCapacity() {
        return this.passengerPlane.crewCapacity;
    }

    @Override
    public String getModel() {
        return this.passengerPlane.model;
    }
    
}
