import java.io.File;
import java.io.IOException;

class NewLogFile {
    private String userAnswerFileName;

    public NewLogFile(String userAnswerFileName) {
        this.userAnswerFileName = userAnswerFileName.trim();
    }

    public File creating() throws IOException
    {
        File file;
        if (!userAnswerFileName.contains(".")) {
            userAnswerFileName = userAnswerFileName + ".log";
        }
        file = new File(userAnswerFileName);

        if (!file.exists() && !file.isDirectory()) {
            file.createNewFile();
            return file;
        }
        return new File("ping.log");
    }
}
