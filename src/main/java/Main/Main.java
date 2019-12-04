package Main;

import Account.Account;
import Instances.Instances;

public class Main {

    public static void main(String[] args) {



        Account account = new Account();
        Instances mcInstance = Instances.getMCInstance();

        mcInstance.testOutput();
    }
}
