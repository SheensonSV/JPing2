import java.util.List;

public class ThreadStarter
{
    private ThreadStarter(){}

    public static void toRunGroupOfThreads(List<String> ipList, int timeOut, ThreadGroup tg, String filName)
    {
        for (String ip : ipList)
        {
            new Thread(tg, new Ping(ip, timeOut, filName)).start();
        }
    }

    public static void toStopGroupOfThreads(ThreadGroup tg)
    {
        tg.interrupt();
    }
}