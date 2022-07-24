package utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

private static Workbook book;
private static Sheet sheet;


public static void openExcel(String filePath){
    try {
        FileInputStream fis=new FileInputStream(filePath);// navigate to the location of our excel file
        book= new XSSFWorkbook(fis);// xssf workbook is the class that reads files with xlsx extension (excel files)
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void getSheet(String sheetName){
    // we need to specify which sheet we want to work with so we use the .getSheet("SheetName") method and pass the name
    sheet=book.getSheet(sheetName);
}

//it will return total no. of rows available in the worksheet
public static int getRowCount(){
   // we need the total number of rows so we can iterate through each row and create a map for the data of each row
    // excluding the header row because those are our keys
    return sheet.getPhysicalNumberOfRows();
}

//it will return the total no. of columns in every row
public static int getColsCount(int rowIndex){
    // we need the total number of columns in each row so we can iterate through each column in each row to get the cell value
    return sheet.getRow(rowIndex).getPhysicalNumberOfCells();
}

//it will return the data from cell in string format
public static String getCellData(int rowIndex,int colIndex){
    // we get the data from each cell in string format as our values in map
    return sheet.getRow(rowIndex).getCell(colIndex).toString();
}

public static List<Map<String,String>>excelIntoMap(String filePath,String sheetName){
    openExcel(filePath);// opening our excel file
    getSheet(sheetName);// going to the sheet we want to work with in our excel file
    List<Map<String,String>>listData=new ArrayList<>();// creating list of maps to store our data in KV pairs

    //outer loop is looping through all the rows excluding the header row bc that is our keys
    for(int row=1;row<getRowCount();row++){
        // creating a map for every row so we don't override the data for the previous rows
        Map<String,String>map=new LinkedHashMap<>();
        //inner loop fetches the data from all the cells
        for(int col=0;col<getColsCount(row);col++){
            map.put(getCellData(0,col),getCellData(row,col));
        }
        listData.add(map);
    }
return listData;
}



}
