/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RentalSystem;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author USER
 */
public class Booking {
    private int bookingID;
    private int carID;
    private String bStartDate;
    private String bEndDate;
    private int cardNumber;
    private double paymentTotal;
    private String status;
    private String username;
    private String cancelReason;
    private boolean approve;
    
    public Booking(int bookingID, int carID, String bStartDate, String bEndDate, int cardNumber, double paymentTotal, String status) {
        this.bookingID = bookingID;
        this.carID = carID;
        this.bStartDate = bStartDate;
        this.bEndDate = bEndDate;
        this.cardNumber = cardNumber;
        this.paymentTotal = paymentTotal;
        this.status = status;
        this.username = UserSession.getUsername();
    }
    
    public Booking(int bookingID){ //for isCancellationAllowed()
        this.bookingID = bookingID;
    }
    
    public Booking(int bookingID, String cancelReason){ //for cancelBooking() & updateStatus()
        this.bookingID = bookingID;
        this.username = UserSession.getUsername();
        this.cancelReason = cancelReason;
    }
    
    public Booking(int carID, String bStartDate, String bEndDate){ //for checkBooking() and initialBooking()
        this.carID = carID;
        this.bStartDate = bStartDate;
        this.bEndDate = bEndDate;
        this.username = UserSession.getUsername();
    }

    public Booking(int bookingID, int carID, String bStartDate, String bEndDate) { //for payment calculation
        this.bookingID = bookingID;
        this.carID = carID;
        this.bStartDate = bStartDate;
        this.bEndDate = bEndDate;
    }
    
    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getbStartDate() {
        return bStartDate;
    }

    public void setbStartDate(String bStartDate) {
        this.bStartDate = bStartDate;
    }

    public String getbEndDate() {
        return bEndDate;
    }

    public void setbEndDate(String bEndDate) {
        this.bEndDate = bEndDate;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public double getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
    
    public boolean isCancellationAllowed(){ //to check if the cancel date is 48 hours before start date
        boolean bookingFound = false;
    try {
        BufferedReader br = new BufferedReader(new FileReader("booking.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String record[] = line.split(",");
            int checkBookID = Integer.parseInt(record[0]);
            if (checkBookID == bookingID) {
                bookingFound = true;
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(record[2]);
                Date currentDate = new Date();

                long timeDiffInMillis = startDate.getTime() - currentDate.getTime();
                long timeDiffInDays = TimeUnit.MILLISECONDS.toDays(timeDiffInMillis);

                if (timeDiffInDays >= 2) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        br.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return !bookingFound;
    }
    
    public void cancelBooking(){
        try{
            File file = new File("booking.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder(); // Change to StringBuilder
            String line;
            while ((line = br.readLine()) != null) {
                String record[] = line.split(",");
                int checkBookingID = Integer.parseInt(record[0]);
                if (checkBookingID == bookingID) {
                    if (!isCancellationAllowed()) {
                        
                        record[6] = "PENDING CANCEL";
                        record[8] = cancelReason;
                    } else {
                        record[6] = "CANCELLED";
                    }
                    line = String.join(",", record);
                }
                stringBuilder.append(line).append("\n"); 
            }
            br.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter("booking.txt"));
            bw.write(stringBuilder.toString()); 
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String checkBooking(){
        //to check validity of the booking dates
        String validity = "valid";
        Calendar startDate = new GregorianCalendar();
        Calendar endDate = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            //setting date object from dates
            startDate.setTime(format.parse(bStartDate));
            endDate.setTime(format.parse(bEndDate));
            BufferedReader br = new BufferedReader(new FileReader("booking.txt"));
            String line;
            while((line = br.readLine()) != null){
                String record[] = line.split(",");
                int checkCarID = Integer.parseInt(record[1]);
                //checking validity specific not cancelled carID 
                if(checkCarID == carID && (!record[7].equals("CANCELLED"))){
                    //creating calendar objects for dates in file
                    Calendar rStartDate = new GregorianCalendar();
                    Calendar rEndDate = new GregorianCalendar();
                    rStartDate.setTime(format.parse(record[2]));
                    rEndDate.setTime(format.parse(record[3]));
                    //comparing dates in file to current object dates
                    if(startDate.compareTo(rStartDate)>=0 && startDate.compareTo(rEndDate)<=0){
                        validity = "invalidS";
                        break;
                    }
                    else if (endDate.compareTo(rStartDate)>=0 && endDate.compareTo(rEndDate)<=0){
                        validity = "invalidE";
                        break;
                    }
                }
            }
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return validity;
    }
    
    public void initialBooking(){
        int lastID = 0;
        String formattedTotal = String.format("%.2f", paymentTotal);
        try{
            BufferedReader br = new BufferedReader(new FileReader("booking.txt"));
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
        String newLine = lastID + "," + carID + "," + bStartDate + "," + bEndDate + ",," + formattedTotal +",PROCESSING PAYMENT,"+username+",null";
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("booking.txt", true));
            bw.write(newLine + "\n");
            bw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    
    //For payment
    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(dateString);
    }
    
    private long calculateDaysBetweenDates(Date startDate, Date endDate) {
        long diffmillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffmillies, TimeUnit.MILLISECONDS)+1;
        return diff;
    }

    private double getCarRateFromID(int CarID) {
        double carRate = 0.0;
        try (BufferedReader br = new BufferedReader(new FileReader("car.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                int id = Integer.parseInt(st.nextToken());
                if (id == CarID) {
                    st.nextToken(); //skip car name
                    st.nextToken(); //skip car type
                    carRate = Double.parseDouble(st.nextToken());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carRate;
    }

    public double calculatePaymentTotal() {
        double total = 0.00; 
        try {
            double rate = getCarRateFromID(carID);
            Date startDate = parseDate(bStartDate);
            Date endDate = parseDate(bEndDate);
            long duration = calculateDaysBetweenDates(startDate, endDate);
            total = rate * duration; 
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return total; 
    }
    
    public void writeBookingDetails(String cardNumber, double paymentTotal, int bookingID) {
        String formattedTotal = String.format("%.2f", paymentTotal);
        try {
            File file = new File("booking.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookingData = line.split(",");
                int currentBookingID = Integer.parseInt(bookingData[0]);
                if (currentBookingID == bookingID) {
                    //Masking card number
                    String maskedCardNumber = maskCardNumber(cardNumber);
                    //update booking & payment details
                    bookingData[4] = maskedCardNumber;
                    bookingData[5] = String.valueOf(formattedTotal);
                    bookingData[6] = "PAID";
                    line = String.join(",", bookingData);
                }
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String maskCardNumber(String cardNumber) {
        //check if card number length is greater than 4
        if (cardNumber.length() > 4) {
            String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
            //mask all digits except the last four
            String maskedDigits = cardNumber.substring(0, cardNumber.length() - 4).replaceAll("\\d", "X");
            //concatenate masked digits with last four digits
            return maskedDigits + lastFourDigits;
        } else {
            return cardNumber; //if card number length is less than or equal to 4, return as it is
        }
    }
    
    //for manage booking
    public void updateStatus(int bookingID, boolean approve){
        try {
            File file = new File("booking.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookingData = line.split(",");
                int bookID = Integer.parseInt(bookingData[0]);
                String currentStatus = bookingData[6];
                double paymentTotal = Double.parseDouble(bookingData[5]);
                if (bookID == bookingID) {
                    if ("PAID".equals(currentStatus) && approve){
                        //confirm booking
                        bookingData[6] = "CONFIRMED";
                    } else if ("CONFIRMED".equals(currentStatus)&& approve){
                        //mark booking complete
                        bookingData[6] = "COMPLETED";
                    } else if ("CANCELLED".equals(currentStatus)&& approve){
                        //mark refunded
                        bookingData[5] = String.valueOf(paymentTotal * 0.8);
                        bookingData[6] = "REFUNDED";
                    } else if ("PENDING CANCEL".equals(currentStatus)){
                        if (approve){
                            //update status
                            bookingData[5] = String.valueOf(paymentTotal * 0.8);
                            bookingData[6] = "REFUNDED";
                        } else {
                            //update status
                            bookingData[6] = "CONFIRMED";
                        }
                    }
                    line = String.join(",", bookingData);
                }
                stringBuilder.append(line).append("\n");
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(stringBuilder.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public double calculateNewTotal(double paymentTotal) {
        //to get a new total after refund
        return paymentTotal * 0.2;
    }
}
