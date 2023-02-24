package services;

import java.io.File;
import java.util.List;

public interface FileService {
    String[] getRowById(File file, String id);

    List<String[]> getRows(File file);

    void deleteRowById(File file, String id);

    void saveRow(File file, String[] row);

    void replaceRowById(File file, String id, String[] newRow);
}
