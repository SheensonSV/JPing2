import Exceptions.EmptyFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadedIPFromFile implements LoadedIP
{
    public List<String> ipAddresses = new ArrayList<>();
    public String fileName;

    LoadedIPFromFile(String fileName){
        this.fileName = fileName;
    }

    @Override
    public List<String> getAllIP() throws IOException, EmptyFileException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("File is not Exist");
        }
        List<String> stringsOfFile = Files.readAllLines(Paths.get(file.toURI()));
        for (String s : stringsOfFile)
        {
            if (CheckingPull.checkOfIPInArguments(s.trim()))
            {
                ipAddresses.add(s.trim());
            }
        }
        if (ipAddresses.size() == 0)
        {
            throw new EmptyFileException("There is no IP addresses in selected file");
        }
        return ipAddresses;
    }
}
