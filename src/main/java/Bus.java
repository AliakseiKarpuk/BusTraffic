import java.util.Date;
import java.util.concurrent.Semaphore;

public class Bus implements Runnable{

    private int busNumber;
    private static final Semaphore SEMAPHORE = new Semaphore(10, true);
    Date date = new Date();

    public Bus(int busNumber) {
        this.busNumber = busNumber;
    }

    @Override
    public void run() {
        try {
            SEMAPHORE.acquire();

            System.out.printf("Автобус №" + busNumber + " подъехал на остановку." + date.toString() + "\n");
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            throw new Error();
        }
        SEMAPHORE.release();
        System.out.printf("Автобус №" + busNumber + " покинул остановку." + date.toString() + "\n");
    }
}
