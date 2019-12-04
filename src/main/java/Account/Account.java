package Account;

import Utils.Tools;

import org.json.JSONObject;

import java.io.*;

public class Account {

    private final String API_BASE_URL = "https://authserver.mojang.com";


    public Account() {

        try {
            checkAccountStatus("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check an Minecraft profile status.
     *
     * @param accountName the useraccount / profile name
     * @throws IOException
     */
    private void checkAccountStatus(String accountName) throws IOException {
        String fileName = String.format("%s.json", accountName);
        String directory = "accounts";
        String fullFileName = String.format("%s/%s", directory, fileName);

        if (!Tools.checkFileExist(fileName, directory)) {
            File tempFile = new File(Tools.getDirectorys(fullFileName));
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            String dataAfterLogin = login("", "");
            if (!dataAfterLogin.isEmpty() && !checkErrorMessage(dataAfterLogin)) {
                Tools.writeDataToFile(dataAfterLogin, fullFileName, "file");
            }
        } else {
            String result = validateSession(accountName);
            if (checkErrorMessage(result)) {
                //TODO: Generate an GUI or save the password encrypted in the folder as .dat (binary mode!)
                //String dataAfterLogin = login("", "");
                //if(!dataAfterLogin.isEmpty()){
                //    Tools.writeDataToFile(dataAfterLogin, fullFileName, "file");
                //}
            }
        }
    }

    /**
     * Login with an Mohjang account (user and password) and generate a local json file with accesstoken and more.
     *
     * @param user     the username (mostly an email)
     * @param password the password
     */
    public String login(String user, String password) throws IOException {

        JSONObject apiAgent = new JSONObject();
        apiAgent.put("name", "Minecraft");
        apiAgent.put("version", 2);

        JSONObject Json = new JSONObject();
        Json.put("agent", apiAgent);
        Json.put("username", user);
        Json.put("password", password);
        Json.put("requestUser", true);

        return Tools.sendJsonPostToUrl(API_BASE_URL + "/authenticate", Json);
    }

    /**
     * Refresh a Minecraft Session, but the Accesstoken will be get invalied.
     *
     * @param accountName the Accountname what we want to refresh.
     */
    public String refreshSession(String accountName) throws IOException {

        String fileName = String.format("%s.json", accountName);
        String directory = "accounts";
        String fullFileName = String.format("%s/%s", directory, fileName);

        JSONObject oldData = new JSONObject(Tools.readFileIntoString(fullFileName));

        JSONObject postData = new JSONObject();
        postData.put("accessToken", oldData.getString("accessToken"));
        postData.put("clientToken", oldData.getString("clientToken"));
        postData.put("requestUser", true);

        return Tools.sendJsonPostToUrl(API_BASE_URL + "/refresh", postData);

    }

    /**
     * Validate a Minecraft session. Try to keep the user logned in (needed for minecraft server)
     *
     * @param accountName the useraccount / profile
     */
    public String validateSession(String accountName) throws IOException {

        String fileName = String.format("%s.json", accountName);
        String directory = "accounts";
        String fullFileName = String.format("%s/%s", directory, fileName);

        JSONObject oldData = new JSONObject(Tools.readFileIntoString(fullFileName));

        JSONObject postData = new JSONObject();
        postData.put("accessToken", oldData.getString("accessToken"));
        postData.put("clientToken", oldData.getString("clientToken"));

        return Tools.sendJsonPostToUrl(API_BASE_URL + "/validate", postData);
    }

    /**
     * Check if the String contains an 403 Code from Majong.
     *
     * @param result the string to check
     */
    private Boolean checkErrorMessage(String result) {

        if (result.contains("code: 403 for URL: https://authserver.mojang.com")) {
            return true;
        } else {
            return false;
        }
    }
}
