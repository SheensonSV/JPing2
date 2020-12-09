import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Ping implements Runnable {
    private final String ip;
    private final int timeOut;
    private final String fileName;

    public Ping(String ip, int timeOut,  String fileName)
    {
        this.ip = ip;
        this.timeOut = timeOut;
        this.fileName = fileName;
    }

    @Override
    public void run()
    {
        String data = new Date().toString();
        try {
            while (true)
            {
                if (!InetAddress.getByName(ip).isReachable(timeOut))
                {
//                    Thread.sleep(timeOut);
                    data = new Date() + " Host - " + ip + " is NOT available.";
                    System.out.println(data);
                    Printer.print(fileName, data + "\n");
                }
                Thread.sleep(timeOut);
                System.out.println(new Date() + " Host - " + ip + " - is available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(ip + " is not reached cause of error!");
        }
        finally {
            Printer.print(fileName, data + " ...Here was some exception..." + "\n");
        }
    }
}
