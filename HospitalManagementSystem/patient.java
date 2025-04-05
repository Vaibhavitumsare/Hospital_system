package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {

    private Connection connection;

    private Scanner scanner;

    public patient(Connection connection,Scanner scanner) {

        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter patient gender: ");
        String gender = scanner.nextLine();

        try{

            String query="INSERT INTO patients(name,age,gender) VALUES(? ,?,?)";
            PreparedStatement prepareStatement = connection.prepareStatement(query); //this creates preparedstatement object to run SQL queries safely
            prepareStatement.setString(1,name);
            prepareStatement.setInt(2,age);
            prepareStatement.setString(3,gender);
            int affectedRows=prepareStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient added successfully");
            }
            else{
                System.out.println("Patient not added");
            }



        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatients(){
        String query="SELECT * from patients";
        try{

            PreparedStatement preparestatement = connection.prepareStatement(query);
            ResultSet resultSet = preparestatement.executeQuery();
            System.out.println("Patient list");
            System.out.println("+------------+----------------------+----------+------------------+");
            System.out.println("| Patient ID | Name                 | Age      | Gender           | \n");
            System.out.println("+------------+----------------------+----------+------------------+");
            while(resultSet.next()){
                //int id is java local variable
                int id=resultSet.getInt("id");//sql query names
                String name=resultSet.getString("name");
                int age=resultSet.getInt("age");
                String gender=resultSet.getString("gender");
                System.out.printf("|%-12s|%-22s|%-10s|%-18s| \n",id,name,age,gender);
                System.out.println("+------------+----------------------+----------+------------------+");
            }


        }catch (SQLException  e){
            e.printStackTrace();
        }
    }

    public boolean getPatientByID(int id){

        String query="SELECT * from patients WHERE id=?";
        try{
           PreparedStatement preparestatement = connection.prepareStatement(query);
           preparestatement.setInt(1,id);//for placeholder (that is for input id)
           ResultSet resultSet = preparestatement.executeQuery(); // it holds all the data
           if(resultSet.next()){ //if the input id has some data it returns true then we return true
               return true;
           }
           else{
               return false;
           }

        }catch(SQLException  e){
            e.printStackTrace();
        }
        return false;
    }


}
