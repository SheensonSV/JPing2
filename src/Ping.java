import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Ping implements Runnable {
    private String ip;
    private int timeOut;
    private List<String> collectedBadAnswers;

    public Ping(String ip, int timeOut) {
        collectedBadAnswers = new ArrayList<>();
        this.ip = ip;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!InetAddress.getByName(ip).isReachable(timeOut))
                {
                    Thread.sleep(timeOut);
                    System.out.println(new Date() + "Host - " + ip + " is NOT available.\n");
                    AccumulateBadRequests.addData(new Date() + " Host - " + ip + " is NOT available.\n");
                }
                Thread.sleep(timeOut);
                System.out.println(new Date() + " - " + ip + " - is available.");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
