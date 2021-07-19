import lombok.SneakyThrows;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class Bus implements Runnable{

    private int busNumber;
    private final int countOnBusStop = 5;
    Date date = new Date();

    public Bus(int busNumber) {
        this.busNumber = busNumber;
    }

    public static Semaphore countBusOnStation(int countBusOnStop){
        return new Semaphore(countBusOnStop, true);
    }

    @Override
    public void run() {
        try {
            countBusOnStation(countOnBusStop).acquire();

            System.out.printf("Автобус №" + busNumber + " подъехал на остановку." + date.toString() + "\n");
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            throw new Error();
        }
        countBusOnStation(countOnBusStop).release();
        System.out.printf("Автобус №" + busNumber + " покинул остановку." + date.toString() + "\n");
    }

    @SneakyThrows
    public static void BusRun(int busCount) {
        for (int i = 0; i < busCount; i++) {
            new Thread(new Bus(i + 1)).start();
            Thread.sleep(1000);
        }
    }
}
