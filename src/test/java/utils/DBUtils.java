package utils;



import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    public static List<Map<String,String>> getDataFromDB(String query){
        String dbUrl=ConfigReader.getPropertyValue("dbURL");// neeed to create connection
        String userName=ConfigReader.getPropertyValue("dbUserName");// need to create connection
        String password= ConfigReader.getPropertyValue("dbPassword");//need to create connection
        Connection connection = null;//
        Statement statement=null;
        ResultSet resultSet=null;
        ResultSetMetaData resultSetMetaData=null;

        List<Map<String,String>> tableData=new ArrayList<>();// list to store the all maps of our data
        try{
            //establishes connection to the DB-- you need the database url, username, and password
            connection= DriverManager.getConnection(dbUrl,userName,password);
            //once we have a connection we must createStatement
            //statement is responsible for taking our queries and executing them on database and returning our resultset
            statement=connection.createStatement();
            //use our statement to execute our query and returns our resultSet
            resultSet=statement.executeQuery(query);
            //resultsetmetadata gives you the information about the table itself like size, column names, etc
            // we use this to get our column name and store them as keys in our map
            resultSetMetaData=resultSet.getMetaData();

            while(resultSet.next()){// points to next row of results(iterates through the rows)
                Map<String,String> row=new HashMap<>();// create a new map for each row of data
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++){// how many columns in this resultSet
                    //(for loop iterates through the columns)
                    //gets me the column name by .getColumnName at index we are on until we get all column names
                    String columnName=resultSetMetaData.getColumnName(i);
                    //using column name to retrieve the value in the columns in the result set
                    String columnValue=resultSet.getString(columnName);
                    row.put(columnName,columnValue);// add k-v pair into map
                }
                tableData.add(row);// add the map into our list
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            closeConnection(connection,statement,resultSet);
        }

        return tableData;
    }

    public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if(connection!=null){
                connection.close();
            }
            if(statement!=null){
                statement.close();
            }
            if (resultSet!=null){
                resultSet.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }



}
