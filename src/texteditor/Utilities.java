/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texteditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.logging.*;
import java.util.logging.FileHandler;

/**
 *
 * @author 61481
 */
public class Utilities {
    static Connection conn;
    static Statement stmt;
    private static ResultSet rs;
    static String url, uName="root", password="root";
    
    private static Logger logger = Logger.getLogger("texteditor.Utilities");
    
    public static void writeToFile(User user) throws IOException
    {
        //this takes a User file and prints it in the proper format in the login file
        try
        {
            File file = new File("login.txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter output = new PrintWriter(br);
            
            output.println(user.username + "," + user.password + "," + user.usertype + "," + user.firstName + "," + user.lastName + "," + user.dateOfBirth);
            output.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Cannot find file: login.txt");
        }
    }
    
    public static void writeToFile(String content, String path)
    {
        //this saves the content of the text area to a file at the selected file path
        try
        {
            PrintWriter output = new PrintWriter(path);
            
            output.print(content);
            output.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Cannot find file: " + path);
        }
    }
    
    public static void writeToDatabase(User user)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");            
            url = "jdbc:mysql://localhost:3306/javaskills";
            conn = DriverManager.getConnection(url,uName,password);            
            stmt = conn.createStatement();
            
            FileHandler fh = new FileHandler("databaseLog.txt");
            logger.addHandler(fh);
            fh.setLevel(Level.SEVERE);
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user (username, password, usertype, firstName, lastName, dob) "
                    + "VALUES (?, ?, ?, ?, ?, ?);");
            ps.setString(1, user.username);
            ps.setString(2, user.password);
            ps.setString(3, user.usertype);
            ps.setString(4, user.firstName);
            ps.setString(5, user.lastName);
            ps.setString(6, user.dateOfBirth);
            ps.executeUpdate();
            logger.severe("Database updated");
            
            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public static boolean readFromFile(String fileName, String username, String password)
    {
        //this code reads the login file and puts the data back into an array of the User data structure
        String currentRecord = "";
        String[] fields;
        User[] users = new User[100];
        int count = 0;
        try {
            Scanner inFile = new Scanner(new File(fileName));
            currentRecord = inFile.nextLine();
            fields = currentRecord.split(",");
            users[count] = new User(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5],true);
            count += 1;
            while(inFile.hasNext())
            {
                currentRecord = inFile.nextLine();
                fields = currentRecord.split(",");
                users[count] = new User(fields[0],fields[1],fields[2],fields[3],fields[4],fields[5],true);
                count += 1;
            }
        }
        catch (FileNotFoundException fe) {
            System.out.println("Cannot find file: " + fileName);
        }
        
        //this checks the input username and password against the content in the user array
        for(int i = 0; i < count; i++)
        {
            if(username.equals(users[i].username) && password.equals(users[i].password))
            {
                return true;
            }
        }
        return false;
    }
    
    public static boolean readFromDatabase(String username, String userpass)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");            
            url = "jdbc:mysql://localhost:3306/javaskills";
            conn = DriverManager.getConnection(url,uName,password);            
            stmt = conn.createStatement();
            int count = 0;
            
            rs = stmt.executeQuery("SELECT * FROM user WHERE username = '" + username + "' AND password = '" + userpass + "';");
            
            if (rs != null) {
                rs.last();
                count = rs.getRow();
            }
            
            if(count == 1)
            {
                conn.close();
                rs.close();
                return true;
            }
            else
            {
                conn.close();
                rs.close();
                return false;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return false;
    }
}
