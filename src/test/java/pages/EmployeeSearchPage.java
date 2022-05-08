package pages;

import utils.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EmployeeSearchPage extends CommonMethods {

    @FindBy (id="menu_pim_viewPimModule")
    public WebElement pimOption;

    @FindBy (css="a#menu_pim_addEmployee")
    public WebElement addEmployeeOption;

    @FindBy (id="menu_pim_viewEmployeeList")
    public WebElement empListOption;

    @FindBy (id="empsearch_id")
    public WebElement idField;

    @FindBy (id="searchBtn")
    public WebElement searchBtn;

    @FindBy (name="empsearch[employee_name][empName]")
    public WebElement nameField;

    public EmployeeSearchPage(){
        PageFactory.initElements(driver,this);
    }



}
