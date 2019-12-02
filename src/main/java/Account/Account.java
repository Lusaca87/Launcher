package Account;

import Utils.Tools;

import java.io.File;

public class Account {

    public Account(){

    checkAccountFile("ZockerDelta");

    }

    private void checkAccountFile(String accountName)
    {
        String fileName = String.format("%s.json",accountName);
        String directory = "accounts";

        if(!Tools.checkFileExist(fileName, directory)) {
            String fullFileName = String.format("%s/%s", directory, fileName);




            File tempFile = new File(Tools.getDirectorys(fullFileName));
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }


        }
    }



}
