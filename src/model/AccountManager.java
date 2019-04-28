package model;

public class AccountManager {
    private static AccountManager uniqueInstance;
    private Customer currentUser;
    private boolean isActive;
    private AccountManager(){
        isActive = false;
        currentUser = null;
    }

    public static AccountManager getManager(){
        if(uniqueInstance == null)
            return uniqueInstance = new AccountManager();
        return uniqueInstance;
    }

    public boolean register(Customer customer){
        DataManager manager = DataManager.getInstance();
        return manager.insertCustomer(customer);
    }

    public boolean isThereActiveUser(){
        return isActive;
    }

    public boolean login(String userName, int hashedPassword){
        return false;
    }
}
