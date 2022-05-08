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

static Workbook book;
static Sheet sheet;


public static void openExcel(String filePath){
    try {
        FileInputStream fis=new FileInputStream(filePath);
        book= new XSSFWorkbook(fis);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public static void getSheet(String sheetName){
    sheet=book.getSheet(sheetName);
}

//it will return total no. of rows available in the worksheet
public static int getRowCount(){
   return sheet.getPhysicalNumberOfRows();
}

//it will return the total no. of columns in every row
public static int getColsCount(int rowIndex){
    return sheet.getRow(rowIndex).getPhysicalNumberOfCells();
}

//it will return the data from cell in string format
public static String getCellData(int rowIndex,int colIndex){
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
