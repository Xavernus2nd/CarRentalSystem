/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RentalSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class Car {
    protected int carID;
    protected String carName;
    protected String carType; 
    protected double carRate;
    protected String deletionStatus;
    
    public Car(){
    }
    
    public Car(int id){
        this.carID = id;
    }
    
    public Car(String name, String type){
        this.carName = name;
        this.carType = type;
    }
    
    public Car(String name, String type, double rate){
        this.carName = name;
        this.carType = type;
        this.carRate = rate;
    }

    public Car(int id, String name, String type, double rate, String status){
        this.carID = id;
        this.carName = name;
        this.carType = type;
        this.carRate = rate;
        this.deletionStatus = status;
    }
    
    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getCarRate() {
        return carRate;
    }

    public void setCarRate(double carRate) {
        this.carRate = carRate;
    }

    public String getDeletionStatus() {
        return deletionStatus;
    }

    public void setDeletionStatus(String deletionStatus) {
        this.deletionStatus = deletionStatus;
    }
    
    public enum carTypes{
    Sedan,
    Hatchback,
    SUV,
    MPV
}
    
    public String checkAvail(){ //checks for car availability from deletion status and booking file
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(calendar.getTime());
        String avail = "Available";
        try (BufferedReader br = new BufferedReader(new FileReader("booking.txt"))) {
            String line;
            while((line = br.readLine()) != null){
                String record[] = line.split(",");
                if(deletionStatus.equals("Deleted")){
                    avail = "Deleted";
                    break;
                }
                //checks for validity of date for records of specified carID
                else if((record[1].equals(String.valueOf(carID))) && (currentDate.compareTo(record[3]))<=0 && (currentDate.compareTo(record[2]))>=0){
                    if(record[6].equals("CANCELLED")){
                        avail = "Available";
                        break;
                    }
                    avail = "Not Available";
                    break;
                }
            }
        } 
        catch (IOException e) {
        e.printStackTrace();
    }
    return avail;
    }
    
    public void addCar(){
        //checking name if they only contain digits
        if((carName.matches("\\d+"))){
            throw new NumberFormatException();
        }
        carTypes cartype = carTypes.valueOf(carType); //for enum
        //fetching latest carID from file
        int lastID = 0;
        try{
        BufferedReader br = new BufferedReader(new FileReader("car.txt"));
        String line;
        while((line=br.readLine()) != null){
            String[] record = line.split(",");
            lastID = Integer.parseInt(record[0]);
        }
        lastID = lastID + 1;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //Writing to file
        String newLine = lastID + "," + carName + "," + carType + "," + carRate + ","+ "Not Deleted";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("car.txt", true));
            bw.write(newLine + "\n");
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        }
    
    public void editCar(){
        try{
            //checking for string validity
            if((carName.matches("\\d+"))){
                throw new NumberFormatException();
            }
            //Writing to file
            BufferedReader br = new BufferedReader(new FileReader("car.txt"));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null){
                String record[] = line.split(",");
                int checkCarID = Integer.parseInt(record[0]);
                if (checkCarID == carID){
                    line = record[0] + "," + carName + ","+ carType + ","+ carRate + ","+ deletionStatus;
                }
                String bufferLine = line + "\n";
                buffer.append(bufferLine);
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("car.txt"));
            bw.write(buffer.toString());
            bw.close();   
    }
        catch(IOException e){
            e.printStackTrace();
        }
}
    //show car details
    public String[] getCarDetails(String carID) throws IOException {
            try (BufferedReader reader = new BufferedReader(new FileReader("car.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(carID)) {
                        return new String[]{parts[1], parts[2]}; //car name and type
                    }
                }
            }
            return new String[]{"", ""}; //default if car ID is not found
        }

}
