package model;

import contracts.DBContract;
import contracts.Users;

import java.util.HashMap;

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

    public Customer login(String userName){
        DataManager manager = DataManager.getInstance();
        HashMap<String, String> userData = manager.getUser(userName);
        Customer customer = UserFactory.getCustomer(userData);
        isActive = true;
        currentUser = customer;
        return customer;
    }

    public boolean logout(){
        deleteInstance();
        return DataManager.getInstance().deleteInstance();
    }

    public Customer getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(Customer customer){
        this.currentUser = customer;
    }

    private void deleteInstance(){
        uniqueInstance = null;
    }
}
