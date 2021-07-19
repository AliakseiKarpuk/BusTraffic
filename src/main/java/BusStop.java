import lombok.SneakyThrows;

import java.util.Date;

public class BusStop {

    @SneakyThrows
    public static void BusRun(int busCount) {
        Date date = new Date();
        for (int i = 0; i < busCount; i++) {
            new Thread(new Bus(i + 1)).start();//выезд автобусов через одну секунду
            Thread.sleep(1000);
        }
    }
}

