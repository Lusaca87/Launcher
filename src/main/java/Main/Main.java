package Main;

import Account.Account;
import GUI.Gui;
import Utils.Encryption;
import javafx.application.Application;


public class Main {

    public static void main(String[] args) {
        Encryption.generateKeyPair();
        Account acc = new Account();
        Application.launch(Gui.class, args);
    }
}
