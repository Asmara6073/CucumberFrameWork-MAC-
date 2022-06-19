package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.AddEmployeePage;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtils;
import utils.ExcelReader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {
    String empIDValue;
    String empID;
    String firstName;
    String dbFirstName;
    String dbEmpID;

    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
       click(employeeSearchPage.pimOption);
    }
    @When("user clicks on add employee option")
    public void user_clicks_on_add_employee_option() {
      click(employeeSearchPage.addEmployeeOption);
    }

    @When("user enters firstname middlename and lastname")
    public void user_enters_firstname_middlename_and_lastname() {
       sendText(addEmployeePage.firstNameField,"Steph");
       sendText(addEmployeePage.middleNameField,"Bean");
       sendText(addEmployeePage.lastNameField,"Bryant");
    }
    @When("user clicks on save button")
    public void user_clicks_on_save_button() {
      click(addEmployeePage.saveBtn);
    }

    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("Employee added successfully");
    }


    @When("user enters {string} {string} and {string}")
    public void user_enters_and(String firstNameValue, String middleNameValue, String lastNameValue) {
       sendText(addEmployeePage.firstNameField, firstNameValue);
       sendText(addEmployeePage.middleNameField, middleNameValue);
       sendText(addEmployeePage.lastNameField, lastNameValue);
    }
    @When("user provides {string} {string} and {string}")
    public void user_provides_and(String firstName, String middleName, String lastName) {
      sendText(addEmployeePage.firstNameField, firstName);
      sendText(addEmployeePage.middleNameField, middleName);
      sendText(addEmployeePage.lastNameField, lastName);
    }

    @When("user provides multiple employee data and verify they are added")
    public void user_provides_multiple_employee_data_and_verify_they_are_added(DataTable dataTable) throws InterruptedException {
           List<Map<String,String>> employeeNames= dataTable.asMaps();// store data table as a list of maps
            // keys are header and values are all data cells under each header
        for (Map<String,String> employee:employeeNames) {
            // looping through the data table and getting value through each iteration of all the keys
            String firstNameValue=employee.get("firstName");
            String middleNameValue=employee.get("middleName");
            String lastNameValue=employee.get("lastName");


            // sending the text to the text fields from each value
            sendText(addEmployeePage.firstNameField, firstNameValue);
            sendText(addEmployeePage.middleNameField, middleNameValue);
            sendText(addEmployeePage.lastNameField,lastNameValue);
            click(addEmployeePage.saveBtn);
            Thread.sleep(3000);

            //verification of adding an employee is HW
            click(employeeSearchPage.addEmployeeOption);

        }
    }

    @When("user adds multiple employees from excel file using {string} sheet and verify the user added")
    public void user_adds_multiple_employees_from_excel_file_using_sheet_and_verify_the_user_added(String sheetName) throws InterruptedException {
        //creating list of maps of each row in our excel sheet
        List<Map<String,String>>newEmployees=ExcelReader.excelIntoMap(Constants.TESTDATA_FILEPATH,sheetName);
        //calling iterator to iterate through the K-V pairs for employees
        Iterator<Map<String,String>> itr=newEmployees.iterator();

        while(itr.hasNext()){
            //returns key and values for employees
            Map<String,String> mapNewEmp=itr.next();

            //filling all the fields from the data coming from the excel file
            sendText(addEmployeePage.firstNameField, mapNewEmp.get("FirstName"));
            sendText(addEmployeePage.middleNameField, mapNewEmp.get("MiddleName"));
            sendText(addEmployeePage.lastNameField,mapNewEmp.get("LastName"));
            //it will fetch the employee id from attribute
           String empIDValue= addEmployeePage.empIdLocator.getAttribute("value");

           //to upload the photograph
            sendText(addEmployeePage.photoUpload,mapNewEmp.get("Photograph"));

            //clicking checkbox if it is not already selected
            if(!addEmployeePage.checkBox.isSelected()){
                click(addEmployeePage.checkBox);
            }

            //sending text to remaining fields
            sendText(addEmployeePage.createUsername,mapNewEmp.get("Username"));
            sendText(addEmployeePage.createPassword,mapNewEmp.get("Password"));
            sendText(addEmployeePage.confirmPassword,mapNewEmp.get("Password"));
            //clicking save button
            click(addEmployeePage.saveBtn);

            Thread.sleep(5000);
            //to verify the employee, we will navigate to employee list option
            click(employeeSearchPage.empListOption);
            Thread.sleep(5000);
            //passing unique id value we got earlier to find the employee in the employee list
            sendText(employeeSearchPage.idField,empIDValue);
            click(employeeSearchPage.searchBtn);

            //creating a list for the rowData of the employee
            List<WebElement> rowData=driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));

                for(int i=0;i<rowData.size(); i++){
                    //getting the row text for the employee
                    String rowText=rowData.get(i).getText();
                    //storing expected data in String
                    String expectedData=empIDValue+" "+mapNewEmp.get("FirstName")+" "+mapNewEmp.get("MiddleName")+
                            " "+mapNewEmp.get("LastName");
                    //comparing both in assertion to verify if same
                    Assert.assertEquals(rowText,expectedData);
                }
                //clicking add new employee to do it for next employee in excel file
                click(employeeSearchPage.addEmployeeOption);
                Thread.sleep(5000);

        }
    }
    @When("user grabs the employee id")
    public void user_grabs_the_employee_id() {
      empID=addEmployeePage.empIdLocator.getAttribute("value");
      firstName=addEmployeePage.firstNameField.getAttribute("value");
    }




    @When("user query the database for same employee id")
    public void user_query_the_database_for_same_employee_id() {
        String query = "select * from hs_hr_employees where employee_id= '"+empID+"'";
        List<Map<String,String>> tableData=DBUtils.getDataFromDB(query);
        dbFirstName=tableData.get(0).get("emp_firstname");
        dbEmpID=tableData.get(0).get("employee_id");
    }


    @Then("user verifies the results")
    public void userVerifiesTheResults() {
        System.out.println("First name from front end" +firstName);
        System.out.println("First name from back end"+ dbFirstName);

        Assert.assertEquals(firstName,dbFirstName);
        Assert.assertEquals(empID,dbEmpID);


    }

    @And("user captures empID")
    public void userCapturesEmpID() {
         empIDValue=addEmployeePage.empIdLocator.getAttribute("value");
    }
}
