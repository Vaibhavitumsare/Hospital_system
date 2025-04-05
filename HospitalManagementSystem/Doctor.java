package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

    private Connection connection;



    public Doctor(Connection connection) {

        this.connection = connection;

    }



    public void viewDoctors() {
        String query="SELECT * from Doctors";
        try{

            PreparedStatement preparestatement = connection.prepareStatement(query);
            ResultSet resultSet = preparestatement.executeQuery();
            System.out.println("Patient list");
            System.out.println("+------------+----------------------+----------------------------+");
            System.out.println("| Doctor ID  | Name                 | Specialization             |");
            System.out.println("+------------+----------------------+----------------------------+");
            while(resultSet.next()){
                //int id is java local variable
                int id=resultSet.getInt("id");//sql query names
                String name=resultSet.getString("name");
                String specilization=resultSet.getString("specialization");
                System.out.printf("|%-12s|%-22s|%-28s| \n",id,name,specilization);
                System.out.println("+------------+----------------------+----------------------------+");
            }


        }catch (SQLException  e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorsByID(int id){

        String query="SELECT * from doctors WHERE id=?";
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
