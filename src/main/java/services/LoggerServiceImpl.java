package services;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerServiceImpl implements LoggerService {
    private final FileWriter fileWriter;

    public LoggerServiceImpl(String fileName) throws IOException {
        this.fileWriter = new FileWriter(fileName, true);
    }

    @Override
    public void log(String content) {
        System.out.print(content);

        try {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
