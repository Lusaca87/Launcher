package Utils;

import java.io.*;
import java.net.URL;

public class Tools {

    /**
     * Get The Fileextension (suffix after dot)
     * @param fileName The filename
     */
    public static String getFileExtension(String fileName) {

        String dirParts[] = fileName.split("/");
        if(dirParts.length > 0){
            return dirParts[dirParts.length - 1].substring(dirParts[dirParts.length - 1].length() - 4);
        }else{
            return fileName.substring(fileName.length()-4);
        }
    }

    /**
     * Get the Filename without extension (suffix before dot)
     * @param fileName the file for this operation.
     * @return
     */
    public static String getFilepreFix(String fileName) {

        String dirParts[] = fileName.split("/");

        if(dirParts.length > 0){
            return dirParts[dirParts.length - 1].substring(0, dirParts[dirParts.length - 1].length() - 4);
        }else {
            return fileName.substring(0, fileName.length() -4);
        }
    }


    /**
     * Get the directorystructure from a path with filename
     * @param fileName the filenname where want to get the directorys
     */
    public static String getDirectorys(String fileName) {

        String fileDir = "";
        String dirParts[] = fileName.split("/");
        if(dirParts.length > 0) {
            for (int i = 0; i < dirParts.length - 1; i++) {
                fileDir = String.format("%s%s/", fileDir, dirParts[i]);
            }
        }
        return fileDir;
    }

    /**
     * Check if the file exists in the dirctory
     * @param filename the filename to check
     * @param rootDir the directory which will append first for check (if it is in an subdirectory)
     */
    public static boolean checkFileExist(String filename, String rootDir) {

        if (rootDir != null) {
            filename = rootDir + "/" + filename;
        }
        File tempFileForCheck = new File(filename);
        return tempFileForCheck.exists();
    }

    /**
     * Write data to a file
     * @param data the data as String or a weburl to a file (for download)
     * @param fileName the filename after write or download it
     * @param type file or url to define the operation.
     */
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
