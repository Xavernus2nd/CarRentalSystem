/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RentalSystem;

/**
 *
 * @author USER
 */
public class UserSession {
    private static String username;
    private static String userType;
    
    public static void setUsername(String username){
        UserSession.username = username;
    }
    
    public static void setUserType(String userType){
        UserSession.userType = userType;
    }

    public static String getUsername() {
        return username;
    }

    public static String getUserType() {
        return userType;
    }
    
    
}
