
JPing v 2.0
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