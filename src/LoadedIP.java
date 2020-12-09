import Exceptions.EmptyFileException;
import Exceptions.EmptyLineException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface LoadedIP
{
    default List<String> getAllIP() throws IOException, EmptyFileException, EmptyLineException
    {
        return new ArrayList<>();
    }
}
