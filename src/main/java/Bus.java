import org.apache.log4j.Logger;

public class Bus extends Thread {

    private static final Logger LOGGER = Logger.getLogger(Bus.class.getName());
    private final BusStop busStop;
    private BusStatus busStatus;

    public Bus(BusStop busStop) {
        this.busStop = busStop;
        setBusStatus(BusStatus.NOT_BEHIND_BUS_STOP);
    }

    public BusStatus getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(BusStatus busStatus) {
        this.busStatus = busStatus;
    }

    @Override
    public void run() {
        try {
            checkBusStopSeats();
            goToStop();
            loadingPassengers();
            leavingTheBusStop();
        } catch (InterruptedException e) {
            LOGGER.error("ERROR!", e);
            Thread.currentThread().interrupt();
        }
    }

    private void checkBusStopSeats() throws InterruptedException {
        while (!busStop.comparisonOfNumberOfSeats()) {
            LOGGER.info(Thread.currentThread().getId() + " the bus is waiting");
            setBusStatus(BusStatus.WAITING_TO_GET_IN);

            synchronized (busStop) {
                busStop.wait();
            }
        }
    }

    private void leavingTheBusStop() {
        LOGGER.info(Thread.currentThread().getId() + " the bus left");
        setBusStatus(BusStatus.LEFT_THE_STOP);
        busStop.leave();
    }

    private void goToStop() throws InterruptedException {
        LOGGER.info(Thread.currentThread().getId() + " the bus stopped at a stop");
        setBusStatus(BusStatus.DROVE_TO_STOP);
        sleep(1000);
    }

    public void loadingPassengers() throws InterruptedException {
        setBusStatus(BusStatus.LOADING_PASSENGERS);
        LOGGER.info(Thread.currentThread().getId() + " the bus is loading passengers");
        sleep(2000);
    }
}