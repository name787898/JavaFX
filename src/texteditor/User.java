/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author 61481
 */
public class User {
    String username;
    String password;
    String usertype;
    String firstName;
    String lastName;
    String dateOfBirth;
    
    public User()
    {
        //default constructor for User class
        username = "default user";
        password = "password123";
        usertype = "default type";
        firstName = "first name";
        lastName = "last name";
        dateOfBirth = "01-01-1901";
    }
    
    public User(String username, String password, String usertype, String firstName, String lastName, String dateOfBirth)
    {
        //constructor used for creating new users
        this.username = username;
        this.password = password;
        this.usertype = usertype;
        this.firstName = firstName;
        this.lastName = lastName;
        
        //the date chooser returns a different format to what is required, so this code converts it to the correct format
        String dob [];
        dob = dateOfBirth.split(" ");
        
        switch(dob[1])
        {
            case "Jan":
                dob[1] = "01";
                break;
            case "Feb":
                dob[1] = "02";
                break;
            case "Mar":
                dob[1] = "03";
                break;
            case "Apr":
                dob[1] = "04";
                break;
            case "May":
                dob[1] = "05";
                break;
            case "Jun":
                dob[1] = "06";
                break;
            case "Jul":
                dob[1] = "07";
                break;
            case "Aug":
                dob[1] = "08";
                break;
            case "Sep":
                dob[1] = "09";
                break;
            case "Oct":
                dob[1] = "10";
                break;
            case "Nov":
                dob[1] = "11";
                break;
            case "Dec":
                dob[1] = "12";
                break;
            default:
                dob[1] = "00";
                break;
        }
        
        this.dateOfBirth = dob[2] + "-" + dob[1] + "-" + dob[5];
    }
    
    public User(String username, String password, String usertype, String firstName, String lastName, String dateOfBirth, boolean formatted)
    {
        //constructor used when reading from login file. since the login file is already formatted, an extra
        //parameter is added to differentiate the two constructors
        this.username = username;
        this.password = password;
        this.usertype = usertype;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
