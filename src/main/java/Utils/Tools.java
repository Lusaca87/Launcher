package Utils;

import java.io.*;

public class Tools {

    public static String getFileExtension(String fileName) {

        String dirParts[] = fileName.split("/");
        return dirParts[dirParts.length - 1].substring(dirParts[dirParts.length - 1].length() - 4);
    }

    public static String getFilepreFix(String fileName) {

        String dirParts[] = fileName.split("/");
        return dirParts[dirParts.length - 1].substring(0, dirParts[dirParts.length - 1].length() - 4);
    }


    public static String getDirectorys(String fileName) {

        String fileDir = "";
        String dirParts[] = fileName.split("/");
        for (int i = 0; i < dirParts.length - 1; i++) {
            fileDir = String.format("%s%s/", fileDir, dirParts[i]);
        }

        return fileDir;
    }

    public static boolean checkFileExist(String filename, String rootDir) {

        if (rootDir != null) {
            filename = rootDir + "/" + filename;
        }
        File tempFileForCheck = new File(filename);
        return tempFileForCheck.exists();
    }

    public static void writeDataToFile(String data, String fileName, String type) {

       //ToDo: Add a method to write data from a url or file to anoother local file. !

    }
}
