import java.io.FileWriter;

public class Printer
{
    private Printer(){}

    public static void print(String fileName, String data)
    {
        synchronized (Printer.class) {
            try (FileWriter fileWriter = new FileWriter(fileName, true))
            {
                fileWriter.write(data);
                fileWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public static void printVoid(String fileName)
    {
        try (FileWriter fileWriter = new FileWriter(fileName, false))
        {
            fileWriter.write("");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}