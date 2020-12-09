import java.util.List;

public class Watcher implements Runnable
{
    private final int timeOut;
    private final List<String> ipList;
    private ThreadGroup threadGroup;
    private final String fileName;

    public Watcher(List<String> ipList, int timeOut, ThreadGroup threadGroup, String fileName)
    {
        this.ipList = ipList;
        this.timeOut = timeOut;
        this.threadGroup = threadGroup;
        this.fileName = fileName;
    }

    @Override
    public void run()
    {
        for(;;)
        {
            if (isCountOfThreadLessThenCountOfIpAddresses(ipList))
            {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadStarter.toStopGroupOfThreads(threadGroup);
                ThreadStarter.toRunGroupOfThreads(ipList, timeOut, threadGroup, fileName);
            }
        }
    }

    public boolean isCountOfThreadLessThenCountOfIpAddresses(List<String> ipAddresses)
    {
        if (threadGroup.activeCount() < ipAddresses.size())
        {
            return true;
        }
        return false;
    }
}
