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

import Exceptions.EmptyFileException;
import Exceptions.EmptyLineException;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class PrimaryClass
{
    private static String fileName = "";
    private static int timeOut;
    private static List<String> ipList;

    public static void main(String[] args)
    {
        System.out.println("JPing v2.1." +
                "\nFollow the instructions below.");

        enteringFileNameToSave();

        enteringTimeOutForPing();

        selectFileOrConsole();

        for (String ip : ipList) {
            new Thread(new Ping(ip, timeOut)).start();
        }

        exitAnyTimeAndSavingDataToFile();
    }

    private static void exitAnyTimeAndSavingDataToFile()
    {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while (true) {
                System.out.println("...Press enter to exit anytime...");
                s = br.readLine();
                if (s.length() == 0)
                {
                    try(FileWriter fileWriter = new FileWriter(fileName))
                    {
                        for (String string : AccumulateBadRequests.getData())
                        {
                            fileWriter.write( string );
                        }
                        fileWriter.flush();
                        System.out.println( "Data saved to file " + fileName );
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selectFileOrConsole()
    {
        for (;;)
        {
            System.out.print("Do you wont to load IP's from file? Yes(y) / No(n) : > ");
            String userAnswerYesOrNo = new Scanner(System.in).next();
            if (userAnswerYesOrNo.equalsIgnoreCase("yes") || userAnswerYesOrNo.equalsIgnoreCase("y"))
            {
                for (;;)
                {
                    System.out.print("Enter file name >");
                    String pathToFile = new Scanner(System.in).next();
                    try {
                        ipList = new LoadedIPFromFile(pathToFile).getAllIP();
                    } catch (IOException | EmptyFileException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                }
                break;
            }
            if (userAnswerYesOrNo.equalsIgnoreCase("no") || userAnswerYesOrNo.equalsIgnoreCase("n"))
            {
                for (;;)
                {
                    System.out.print("Enter IPs use space divider (example: 192.168.0.1 10.1.1.1 172.16.1.1) \n> ");
                    String userAnswer = new Scanner(System.in).nextLine();
                    try {
                        ipList = new LoadedIPFromConsole(userAnswer).getAllIP();
                    } catch (EmptyLineException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                }
                break;
            }
            System.out.println("Wrong answer! Please repeat.");
        }
    }

    private static void enteringTimeOutForPing()
    {
        for (;;)
        {
            System.out.print("Enter timeOut for Ping: > ");
            String userAnswerTimeOut = new Scanner(System.in).next();
            if (!CheckingPull.checkOfTIMEOUTInArguments(userAnswerTimeOut))
            {
                System.out.println("Wrong answer!");
                continue;
            }
            timeOut = Integer.parseInt(userAnswerTimeOut);
            break;
        }
    }

    private static void enteringFileNameToSave()
    {
        for (;;)
        {
            System.out.print("Please enter file name to save: > ");
            String userAnswerFileName = new Scanner(System.in).next();
            if (!CheckingPull.checkingFileNameString(userAnswerFileName))
            {
                System.out.println("Wrong answer");
                continue;
            }
            try {
                new NewLogFile( userAnswerFileName ).creating();
                fileName = userAnswerFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }

            break;
        }
    }
}

