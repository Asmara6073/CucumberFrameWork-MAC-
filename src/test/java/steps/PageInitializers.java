package steps;

import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeeSearchPage;
import pages.LoginPage;

public abstract class PageInitializers {

    public static LoginPage login;
    public static EmployeeSearchPage employeeSearchPage;
    public static AddEmployeePage addEmployeePage;
    public static DashboardPage dash;


    public static void initPageObjects(){
        login=new LoginPage();
        employeeSearchPage=new EmployeeSearchPage();
        addEmployeePage=new AddEmployeePage();
        dash=new DashboardPage();
    }


}
