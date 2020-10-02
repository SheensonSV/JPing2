import Exceptions.EmptyLineException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadedIPFromConsole implements LoadedIP
{
    public List<String> ipAddresses = new ArrayList<>();
    public String lineWithIPAddresses;

    public LoadedIPFromConsole(String lineWithIPAddresses)
    {
        this.lineWithIPAddresses = lineWithIPAddresses;
    }

    @Override
    public List<String> getAllIP() throws EmptyLineException
    {
        String[] ips = lineWithIPAddresses.split("\\s");
        for (String ip : ips)
        {
            if (CheckingPull.checkOfIPInArguments(ip.trim()))
            {
                ipAddresses.add(ip.trim());
            }
        }
        if (ipAddresses.size() == 0)
        {
            throw new EmptyLineException("There is no IP addresses in selected file");
        }
        return ipAddresses;
    }
}
