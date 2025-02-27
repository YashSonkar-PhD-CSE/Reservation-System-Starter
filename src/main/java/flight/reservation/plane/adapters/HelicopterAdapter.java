package flight.reservation.plane.adapters;

import flight.reservation.plane.Helicopter;
import flight.reservation.plane.Plane;

public class HelicopterAdapter implements Plane {

    private Helicopter helicopter;

    public HelicopterAdapter(Helicopter helicopter) {
        this.helicopter = helicopter;
    }

    @Override
    public int getPassengerCapacity()  {
        return this.helicopter.getPassengerCapacity();
    }

    @Override
    public int getCrewCapacity() {
        return this.helicopter.getCrewCapacity();
    }

    @Override
    public String getModel() {
        return this.helicopter.getModel();
    }
    
}
