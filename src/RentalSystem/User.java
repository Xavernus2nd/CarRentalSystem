/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RentalSystem;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author USER
 */
public class User {
    protected String username;
    protected String password;
    protected String icnum;
    protected String hpnum;
    protected String conpassword;
    protected String userType;
    
    public User(String username, String password, String icnum, String hpnum, String conpassword){
        this.username = username;
        this.password = password;
        this.icnum = icnum;
        this.hpnum = hpnum;
        this.conpassword = conpassword;
    }
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public User(String username, String password, String userType){
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
    
    public String getUserType(){
        return userType;    
    }
    
    public void setUserType(String userType){
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean validateLogin(){ //used to validate credentials
        boolean isValid = false;
        try {
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line=br.readLine()) != null){
                String[] record = line.split(",");
                String rUsername = record[0];
                String rPassword = record[1];

                if (username.equals(rUsername) && password.equals(rPassword)){
                    isValid = true;
                    break;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return isValid;
    }
    
    public String returnUserType(){ //used to return user type from file
        String tUserType = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line=br.readLine()) != null){
            String[] record = line.split(",");
            String rUsername = record[0];
            String rPassword = record[1];
            String rUserType = record[4];
            
            if (username.equals(rUsername) && password.equals(rPassword)){
                tUserType = rUserType;
                break;
            }
        }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return tUserType;
    }
    
    public void logLogin(){
        //creating line to write into file
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());
        String message = date + "," + time + "," + username + "," + userType;
        //writing into file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("loginRecord.txt", true));
            bw.write(message + "\n");
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public void setUserSession(){
        UserSession.setUsername(username);
        UserSession.setUserType(userType);
    }
    
    public boolean validateRegistration(String checkUsername, String checkPassword, String icNum, String confirmPassword, String hpNum) {
        //check if the user is at least 18 years old
        if (!isOldEnough(icNum)) {
            return false;
        }

        //check if the username is unique
        if (!isUsernameUnique(checkUsername)) {
            return false;
        }

        //check if the password meets the length requirement
        if (checkPassword.length() < 5 && checkPassword.length() > 9) {
            return false;
        }

        //check if the password matches the confirm password
        if (!checkPassword.equals(confirmPassword)) {
            return false;
        }
        
        if (!isHPNumICNumValid(hpNum, icNum)){
            return false;
        }

        //all validations passed
        return true;
    }

    private boolean isOldEnough(String icNum) {
        //get the current year
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        //extract the birth year from the IC number
        int birthYear = Integer.parseInt(icNum.substring(0, 2));

        //calculate the actual birth year
        if (birthYear >= 0 && birthYear <= 21) {
            birthYear += 2000; // Assume 21st century
        } else {
            birthYear += 1900; // Assume 20th century
        }

        //calculate the age
        int age = currentYear - birthYear;

        //check if the age is at least 18
        return age >= 18;
    }

    private boolean isUsernameUnique(String checkUsername) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                String rUsername = record[0];
                if (checkUsername.equals(rUsername)) {
                    //username is not unique
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }
    
    private boolean isHPNumICNumValid(String hpNum, String ICNum) {
        int lenHP = hpNum.length();
        int lenIC = ICNum.length();
        if (lenHP != 10 && lenHP != 11 && lenIC != 12){
            return false;
        }
        
        return true;
    }
    
    public void regRegistration() {
        String record = username + "," + password + "," + icnum + "," + hpnum + ",customer,0";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write("\n" + record);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Admin extends User{
    public Admin(String username, String password){
        super(username, password);
        this.userType = "admin";
    }
}

class Customer extends User{
    private int points;
    public Customer(String username, String password){
        super(username, password);
        this.userType = "customer";
    }
    public void addPoints(int add){
        this.points += add;
        try{
            //Writing to file
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null){
                String record[] = line.split(",");
                String checkUsername = record[0];
                if (checkUsername.equals(username)){
                    line = record[0] + "," + record[1] + ","+ record[2] + ","+ record[3] + ","+ record[4] + "," + points;
                }
                String bufferLine = line + "\n";
                buffer.append(bufferLine);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"));
            bw.write(buffer.toString());
            bw.close();   
    }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public void deductPoints(int sub){
        this.points -= sub;
        try{
            //Writing to file
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null){
                String record[] = line.split(",");
                String checkUsername = record[0];
                if (checkUsername.equals(username)){
                    line = record[0] + "," + record[1] + ","+ record[2] + ","+ record[3] + ","+ record[4] + "," + points;
                }
                String bufferLine = line + "\n";
                buffer.append(bufferLine);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"));
            bw.write(buffer.toString());
            bw.close();   
    }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public void setPoints(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("user.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                if (username.equals(record[0])) {
                    this.points = Integer.parseInt(record[5]);
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public int getPoints(){
        return points;
    }
    
    public int calculateEarnablePoints(double payment){
        int earnablePoints = (int) Math.round(payment*0.05);
        return earnablePoints;
    }
    
}
