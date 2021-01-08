package org.levelup.universities.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static void main(String[] args) {
       // FacultyJdbsStorage facultyJdbsStorage=new FacultyJdbsStorage();
       // facultyJdbsStorage.createFaculty("test9",103);
        //   System.out.println("------");
      // facultyJdbsStorage.findBySql("select * from faculty where university_id>?",102);
        //   System.out.println("------");
       // facultyJdbsStorage.deleteFaculty(104);
        //   System.out.println("------");
     //   facultyJdbsStorage.displayFacilties();

       UniversitiesJdbcStorage storage =new UniversitiesJdbcStorage(new JdbcService());
       storage.findBySql("select * from university ");
       storage.findBySql("select * from university where foundation_year>?",1840);
        System.out.println("------");
     //   storage.findBySql("select * from university where short_name like ?","МГУ");
       /*   try (BufferedReader reader=new BufferedReader((new InputStreamReader(System.in)))){
          System.out.println("Enter UniverName");
            String name= reader.readLine();
            System.out.println("Enter UniverSchortName");
            String shortName= reader.readLine();
            System.out.println("Enter foundationYear");
            String shortName= Integer.parseInt(reader.readLine());

            storage.createUniversity(name,shortName,foundationYear);
            storage.displayUniversities();


        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String name="test";
        String shortName="testShort";
        int  foundationYear=100;
        storage.createUniversity(name,shortName,foundationYear);
    }
}
