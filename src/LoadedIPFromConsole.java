import Exceptions.EmptyLineException;

import java.util.ArrayList;
import java.util.List;

public class LoadedIPFromConsole
{
    public List<String> ipAddresses = new ArrayList<>();
    public String lineWithIPAddresses;

    public LoadedIPFromConsole(String lineWithIPAddresses)
    {
        this.lineWithIPAddresses = lineWithIPAddresses;
    }

    public List<String> getAllIPFromFileInList() throws EmptyLineException
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
