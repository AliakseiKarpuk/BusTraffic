import lombok.SneakyThrows;

import java.util.Date;
import java.util.concurrent.Semaphore;

public class Bus implements Runnable {

    private int busNumber;
    private static int countOnBusStop = 2;
    public static final Semaphore semaphore = new Semaphore(countOnBusStop, true);

    public Bus(int busNumber) {
        this.busNumber = busNumber;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();

            System.out.println("Автобус №" + busNumber + " подъехал на остановку." + new Date().toString() + "\n");
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            throw new Error();
        }
        semaphore.release();
        System.out.println("Автобус №" + busNumber + " покинул остановку." + new Date().toString() + "\n");
    }

    @SneakyThrows
    public static void BusRun(int busCount) {
        for (int i = 0; i < busCount; i++) {
            new Thread(new Bus(i + 1)).start();
            Thread.sleep(1000);
        }
    }
}
