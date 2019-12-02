package Utils;

import java.io.*;
import java.net.URL;

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

    public static void writeDataToFile(String data, String fileName, String type) throws IOException {

        if (type.equals("file")) {
            OutputStream os = null;
            try {
                os = new FileOutputStream(new File(fileName));
                os.write(data.getBytes(), 0, data.length());
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (type.equals("url")) {
            try (BufferedInputStream in = new BufferedInputStream(new URL(data).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
