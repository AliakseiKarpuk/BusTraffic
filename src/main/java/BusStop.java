public class BusStop {

    private int maxPlacesOnBusStop;
    private int placesInCurrentMoment;

    public BusStop(int maxPlacesOnBusStop) {
        this.maxPlacesOnBusStop = maxPlacesOnBusStop;
        this.placesInCurrentMoment = 0;
    }

    public boolean comparisonOfNumberOfSeats() {
        return placesInCurrentMoment != maxPlacesOnBusStop;
    }

    public void leave() {
        placesInCurrentMoment--;

        synchronized (this) {
            notifyAll();
        }
    }
}
