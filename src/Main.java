/**
 *  JPing v 2.0
 *  Wrote by Sergei Sheenson
 *  This is specific program developed for use in local network to monitoring reach status of couple of IP addresses.
 *  In the beginning the program asks user to enter a name of  a file to save data.
 *  Use full path. ex.: "c:/somefolder/ping.log" or just a filename "ping.log".
 *  After that user need to enter a timeout of testing IPs. ex.: "1500". Parameter in milliseconds.
 *  Then need to answer how to input IP addresses - from the console or from the file.
 *  If choose from the file - need to enter file name. ex.: "ips.txt".
 *  If choose from the console - need to input ip addresses with spaces. ex.: "10.1.1.1 10.1.1.2 10.1.1.50" etc.
 *  Then will executed the first test of getting IP's addresses reach status.
 *  Ip will be cutted from multipinging if some of it is unreached.
 *  Then begins a multi-pinging.
 *  The ended file will have a structured information only about unreached state of IPs.
 *  ex.: "  BEGIN -> Wed Sep 23 13:17:56 GMT+04:00 2020 - 172.16.1.1 is unreachable
 *          END -> Wed Sep 23 13:18:56 GMT+04:00 2020 - 172.16.1.1 is unreachable"
 *  This data will added to file once.
 *  And after that this IP will not pinging again.
 *  To Quit from program you can press "Enter" at any time.
 */

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;



public class Main
{
    public static File defaultFileName;
    public static Integer defaultTimeOut;

    public static List<String> db = Collections.synchronizedList(new ArrayList<>());

    private static List<String> ipList;

    public static void main(String[] args)
    {

        userInterface();

        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        String s;
        try
        {
            executingThreads();

            while(true)
            {


                System.out.println("...Press enter to exit anytime...");

                s = br.readLine();

                if ( s.length() == 0 )
                {

                    FileWriter fileWriter = new FileWriter(defaultFileName.getName());

                    for (String string : db)
                    {
//                        System.out.println(string);
                        fileWriter.write( string );
                    }

                    fileWriter.flush();
                    fileWriter.close();

                    System.out.println( "Data saved to file " + defaultFileName.getAbsolutePath() );

                    System.exit(0);

                }
            }
        } catch (Exception e)
        {

            e.printStackTrace();

        }

    }

    private static void executingThreads()
    {
        if (ipList.size() != 0)
        {
            for (String test : ipList)
            {
                System.out.println(test);
            }

            try {
                beginingTestOfIPsReachment(defaultTimeOut);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String ip : ipList)
            {
                new Thread(() ->
                {
                    try
                    {


                        pingingAddress(ip, defaultTimeOut);

                    } catch (Exception e)
                    {

                        e.printStackTrace();

                    }
                }).start();
            }
        }
        else
        {
            System.out.println("Sorry, but some data was not recognized. Correct data before program's restarting.");
        }
    }

    private static void beginingTestOfIPsReachment(Integer timeOut) throws IOException {
        for (int i = 0; i < ipList.size(); i++)
        {
            if ( !InetAddress.getByName( ipList.get(i) ).isReachable( timeOut ) )
            {
                System.out.println(ipList.get(i) + " is unreached and will cut from pinging.");
                ipList.remove(i);
            }
        }
    }

    private static void pingingAddress(String defaultIP, Integer timeOut) throws Exception
    {

        String dataString = "";

        while ( InetAddress.getByName( defaultIP ).isReachable( timeOut ) )
        {
            Thread.sleep(timeOut);
            System.out.println(new Date() + " - " + defaultIP + " - is Reached.");
        }

        dataString = new Date() + " | " + defaultIP + " is unreachable.\n";
        db.add("BEGIN -> " + dataString);
        System.out.println("BEGIN -> " + dataString);
        while ( !InetAddress.getByName( defaultIP ).isReachable( timeOut ) )
        {
            Thread.sleep(timeOut);
            dataString = new Date() + " | " + defaultIP + " is unreachable.\n";

            System.out.println(dataString);

        }

        dataString = new Date() + " | " + defaultIP + " is unreachable.\n";
        db.add("END -> " + dataString);
        System.out.println("END -> " + dataString);

    }

    private static void userInterface()
    {
        ipList = new ArrayList();

        System.out.println("This program can ping many hosts at the one moment and write data to the file.");
        for (;;)
        {
            System.out.print("Please enter file name to save: > ");

            Scanner scanner = new Scanner(System.in);
            String userAnswerFileName = scanner.next();

            if (!checkOfFILENAMEInArguments( userAnswerFileName ) )
            {
                System.out.println("Wrong answer");
                continue;
            }

            try {

                defaultFileName = createNewFile( userAnswerFileName );

            } catch (IOException e)
            {

                e.printStackTrace();

            }

            break;
        }

        for (;;)
        {
            System.out.print("Enter timeOut for Ping: > ");

            Scanner scanner = new Scanner(System.in);
            String userAnswerTimeOut = scanner.next();

            if (!checkOfTIMEOUTInArguments( userAnswerTimeOut ) )
            {

                System.out.println("Wrong answer!");
                continue;

            }

            defaultTimeOut = Integer.parseInt(userAnswerTimeOut);
            break;
        }

        for (;;)
        {
            System.out.print("Do you wont to load ip's from file? Yes(y) / No(n) : > ");

            Scanner scanner = new Scanner(System.in);
            String userAnswer = scanner.next();

            if (userAnswer.equalsIgnoreCase("yes") || userAnswer.equalsIgnoreCase("y"))
            {

                try
                {

                    ipList = loadDataFromFile();
                    break;

                } catch (IOException e)
                {

                    e.printStackTrace();

                }

            }
            if (userAnswer.equalsIgnoreCase("no") || userAnswer.equalsIgnoreCase("n"))
            {

                ipList = enterIPByHands();
                break;

            }
        }

    }

    private static ArrayList<String> enterIPByHands()
    {

        ArrayList<String> correctIPsList = new ArrayList();

        for (;;)
        {
            System.out.print( "Enter IPs use space divider (example: 192.168.0.1 10.1.1.1 172.16.1.1) \n> " );

            Scanner scanner = new Scanner(System.in);
            String userAnswer = scanner.nextLine();

            String[] ipAddresses = userAnswer.split("\\s");
            for (int i = 0; i < ipAddresses.length; i++)
            {
                if ( checkOfIPInArguments( ipAddresses[i].trim() ) )
                {
                    correctIPsList.add(ipAddresses[i].trim());
                }
            }
            if (correctIPsList.size() == 0)
            {
                return new ArrayList<>();
            }

            return correctIPsList;

        }

    }

    private static List<String> loadDataFromFile() throws IOException
    {
        List<String> stringsOfFile;
        for (;;) {

            System.out.print("Enter file name \n>");

            Scanner scanner = new Scanner(System.in);
            String userAnswer = scanner.next();
            File file = new File(userAnswer);

            if (file.exists()) {
                stringsOfFile = Files.readAllLines(Paths.get(file.toURI()));
                List<String> correctIPsList = new ArrayList<>();

                for (int i = 0; i < stringsOfFile.size(); i++) {
                    if (checkOfIPInArguments(stringsOfFile.get(i).trim())) {
                        correctIPsList.add(stringsOfFile.get(i).trim());
                    }
                }

                if (correctIPsList.size() == 0) {
                    return new ArrayList<>();
                }

                return correctIPsList;

            }
            else
            {
                System.out.println("Cant find your file!");
            }
        }
    }

    private static File createNewFile(String fileName) throws IOException
    {
        File file;
        String[] temp = fileName.split(" ");
        if (temp.length != 0)
        {

            fileName = temp[0].trim();
            if (!fileName.contains("."))
            {

                fileName = fileName + ".log";

            }

            file = new File( fileName );

            if (file.exists())
            {

                file.delete();

            }

            file.createNewFile();

            return file;
        }

        return new File("ping.log");
    }


    //-------------------Checking Part beginning--------------------------

    private static boolean checkOfIPInArguments(String oneArgumentFromArgs)
    {
        if ( Pattern.matches("(\\d{1,3}\\.){3}\\d{1,3}", oneArgumentFromArgs ) )
        {

            String[] splitedIP = oneArgumentFromArgs.split("\\.");

            for (String part : splitedIP)
            {
                Integer intFromPart = Integer.parseInt(part);
                if (intFromPart > 255 || intFromPart < 0)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean checkOfFILENAMEInArguments(String oneArgumentFromArgs)
    {
            if ( Pattern.matches("[a-zA-Z0-9_\\.\\\\/\\:]+", oneArgumentFromArgs.trim()) )//([\w]{0,12}(\.[a-zA-Z]{3,10}){0,2})
        {
           return true;
        }
        return false;
    }

    private static boolean checkOfTIMEOUTInArguments(String oneArgumentFromArgs)
    {
        if ( Pattern.matches("[0-9]{1,8}", oneArgumentFromArgs) )
        {
            Integer timeOut = Integer.parseInt(oneArgumentFromArgs);
            if (timeOut > 100000 || timeOut < 1)
            {
                return false;
            }
            return true;
        }
        return false;
    }

    //-------------------Checking Part end--------------------------



    public static List<String> getIpList()
    {
        return ipList;
    }

    public static void setIpList(List<String> ipList)
    {
        Main.ipList = ipList;
    }

}
