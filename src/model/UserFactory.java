package model;

import contracts.DBContract;
import contracts.Users;

import java.util.HashMap;

public class UserFactory {
    public static Customer getCustomer(HashMap<String, String> userData) {
        Customer customer;
        if(userData.get(DBContract.User.CREDENTIAL_COLUMN).equalsIgnoreCase(Users.CUSTOMER)){
           customer = new Customer();
        }
        else if(userData.get(DBContract.User.CREDENTIAL_COLUMN).equalsIgnoreCase(Users.MANAGER)){
            customer = new Manager();
        }
        else{
            customer = new Manager(); // supposed to be admin
        }
        customer.setUserName(userData.get(DBContract.User.USER_NAME_COLUMN));
        customer.setHashedPassword(userData.get(DBContract.User.PASSWORD_COLUMN));
        customer.setFirstName(userData.get(DBContract.User.FIRST_NAME_COLUMN));
        customer.setLastName(userData.get(DBContract.User.LAST_NAME_COLUMN));
        customer.setAddress(userData.get(DBContract.User.ADDRESS_COLUMN));
        customer.setCredential(userData.get(DBContract.User.CREDENTIAL_COLUMN));
        customer.setPhoneNumber(userData.get(DBContract.User.PHONE_COLUMN));
        customer.setEmail(userData.get(DBContract.User.EMAIL_COLUMN));
        return customer;
    }
}
