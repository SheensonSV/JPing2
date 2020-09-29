import java.util.regex.Pattern;

class CheckingPull {
    public CheckingPull() {}
    public static boolean checkOfIPInArguments(String oneArgumentFromArgs) {
        if (Pattern.matches("(\\d{1,3}\\.){3}\\d{1,3}", oneArgumentFromArgs)) {
            String[] partsOfIP = oneArgumentFromArgs.split("\\.");
            for (String part : partsOfIP) {
                int intFromPart = Integer.parseInt(part);
                if (intFromPart > 255 || intFromPart < 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean checkingFileNameString(String oneArgumentFromArgs) {
        return Pattern.matches("[a-zA-Z0-9_.\\\\/:]+", oneArgumentFromArgs.trim());
    }

    public static boolean checkOfTIMEOUTInArguments(String oneArgumentFromArgs) {
        if (Pattern.matches("[0-9]{1,8}", oneArgumentFromArgs)) {
            int timeOut = Integer.parseInt(oneArgumentFromArgs);
            return timeOut <= 100000 && timeOut >= 1;
        }
        return false;
    }

}
