import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BusStopTest {

    @Test
    void busLoadingPassengersAfterGetIn() throws InterruptedException {
        BusStop busStopFirst = new BusStop(2);
        Bus bus = new Bus(busStopFirst);
        bus.loadingPassengers();
        assertEquals(BusStatus.LOADING_PASSENGERS, bus.getBusStatus());
    }

    @Test
    void busStopEnteringWithNoPlace() {
        BusStop busStop = new BusStop(0);
        assertFalse(busStop.comparisonOfNumberOfSeats());
    }

    @Test
    void busStopAreNotExist() {
        Bus bus = new Bus(null);
        bus.start();
        assertEquals(BusStatus.NOT_BEHIND_BUS_STOP, bus.getBusStatus());
    }

    @Test
    void noFreePlacesOnBusStop() {
        BusStop busStop = new BusStop(0);

        Bus bus = new Bus(busStop);

        bus.start();

        await().pollInterval(1, SECONDS)
                .until(() -> bus.getBusStatus() != BusStatus.NOT_BEHIND_BUS_STOP);
        assertEquals(BusStatus.WAITING_TO_GET_IN, bus.getBusStatus());
    }

    @Test
    void busLeavingBusStop() {
        BusStop busStop = new BusStop(2);

        Bus bus = new Bus(busStop);

        bus.start();

        await().pollInterval(1, SECONDS)
                .until(() -> bus.getBusStatus() != BusStatus.LOADING_PASSENGERS && bus.getBusStatus() != BusStatus.DROVE_TO_STOP);
        assertEquals(BusStatus.LEFT_THE_STOP, bus.getBusStatus());
        await().until(() -> !bus.isAlive());
    }
}
