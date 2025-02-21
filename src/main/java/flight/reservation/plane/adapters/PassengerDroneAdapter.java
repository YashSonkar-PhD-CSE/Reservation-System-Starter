package flight.reservation.plane.adapters;

import flight.reservation.plane.PassengerDrone;
import flight.reservation.plane.Plane;

public class PassengerDroneAdapter implements Plane {
    private PassengerDrone passengerDrone;

    public PassengerDroneAdapter(PassengerDrone passengerDrone) {
        this.passengerDrone = passengerDrone;
    }

    @Override
    public int getPassengerCapacity() {
        return 2; // Assumption
    }

    @Override
    public int getCrewCapacity() {
        return 1; // Assumption
    }

    @Override
    public String getModel() {
        return this.passengerDrone.getModel();
    }
    
}
