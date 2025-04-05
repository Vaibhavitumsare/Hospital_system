package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url ="jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "tandoori@07";

   public static void main(String[] args) {

       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
       }catch(ClassNotFoundException e){
           e.printStackTrace();
       }

       Scanner scanner = new Scanner(System.in);
       try{
           Connection connection = DriverManager.getConnection(url,username,password);
           patient patient=new patient(connection,scanner);
           Doctor Doctor=new Doctor(connection);

           while(true){
               System.out.println("|| HOSPITAL MANAGEMENT SYSTEM ||");
               System.out.println("1.Add Patient");
               System.out.println("2.View patients");
               System.out.println("3.view Doctors");
               System.out.println("4.Book Appointment");
               System.out.println("5.Exit \n\n");
               System.out.println("Enter your choice");
               int choice = scanner.nextInt();
               scanner.nextLine();
               switch(choice){
                   case 1:
                       //add patient
                       patient.addPatient();
                       System.out.println();
                       break;
                   case 2:
                       //view patient
                       patient.viewPatients();
                       System.out.println();
                       break;
                   case 3:
                       // view doctors
                       Doctor.viewDoctors();
                       System.out.println();
                       break;
                    case 4:
                        //book appointment
                        bookAppointment(patient,Doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;
                        default:
                            System.out.println("enter valid choice");

               }

           }

       }catch (SQLException e){
           e.printStackTrace();
       }
   }

   public static void bookAppointment(patient patient,Doctor doctor,Connection connection,Scanner scanner){

       System.out.println("enter patient id");
       int patientId = scanner.nextInt();
       System.out.println("enter doctor id");
       int doctorId = scanner.nextInt();
       System.out.println("enter appointment date (yyyy-mm-dd)");
       String appointmentDate = scanner.next();

       if(patient.getPatientByID(patientId) && doctor.getDoctorsByID(doctorId)){

           if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
             String appointmentquery="INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";

             try{

                 PreparedStatement preparedStatement=connection.prepareStatement(appointmentquery);
                 preparedStatement.setInt(1,patientId);
                 preparedStatement.setInt(2,doctorId);
                 preparedStatement.setString(3,appointmentDate);
                 int rowsAffected=preparedStatement.executeUpdate();
                 if(rowsAffected>0){
                     System.out.println("Appointment booked successfully");
                 }
                 else{
                     System.out.println("Appointment booking failed");
                 }

             }catch (SQLException e){
                 e.printStackTrace();
             }

           }else {
               System.out.println("Doctor is not available");
           }
       }
       else{
           System.out.println("either patient or either doctor doesnt exist");
       }

   }

   public static boolean checkDoctorAvailability(int doctorId,String appointmentDate,Connection connection){
       String query="SELECT count(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";

       try{

           PreparedStatement preparedStatement=connection.prepareStatement(query);
           preparedStatement.setInt(1,doctorId);
           preparedStatement.setString(2,appointmentDate);
           ResultSet resultSet=preparedStatement.executeQuery();
           if(resultSet.next()){
               int count = resultSet.getInt(1);
               if(count == 0){
                   return true;
               }
               else{
                   return false;
               }
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
   }
}
