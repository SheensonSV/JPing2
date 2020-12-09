import Exceptions.EmptyFileException;
import Exceptions.EmptyLineException;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static String fileName = "";
    private static int timeOut;
    private static List<String> ipList;
    private static final ThreadGroup threadGroup = new ThreadGroup("GroupToPing");

    public static void main(String[] args)
    {
        System.out.println("JPing v2.1." +
                "\nFollow the instructions below.");

        enteringFileNameToSave();

        enteringTimeOutForPing();

        selectFileOrConsole();

        ThreadStarter.toRunGroupOfThreads(ipList, timeOut, threadGroup, fileName);

        new Thread(new Watcher(ipList, timeOut ,threadGroup, fileName)).start();

        exitAnyTime();
    }

    private static void exitAnyTime()
    {
        Printer.printVoid(fileName);// Clearing existing file
        Printer.print(fileName, new Date() + " - Beginning of monitoring\n");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while (true)
            {
                System.out.println("...Press enter to exit anytime...");
                s = br.readLine();
                if (s.length() == 0)
                {
                    Printer.print(fileName, new Date() + " - End of monitoring");
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

