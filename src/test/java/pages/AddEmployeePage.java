package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

import java.util.List;

public  class AddEmployeePage extends CommonMethods {

    @FindBy (css = "input#firstName")
    public WebElement firstNameField;

    @FindBy (css = "input#middleName")
    public WebElement middleNameField;

    @FindBy (css = "input#lastName")
    public WebElement lastNameField;

    @FindBy (css = "input#btnSave")
    public WebElement saveBtn;

    @FindBy (id="employeeId")
    public WebElement empIdLocator;

    @FindBy (id="photofile")
    public WebElement photoUpload;

    @FindBy (id="chkLogin")
    public WebElement checkBox;

    @FindBy (id="user_name")
    public WebElement createUsername;

    @FindBy (id="user_password")
    public WebElement createPassword;

    @FindBy (id="re_password")
    public WebElement confirmPassword;


    @FindBy (xpath = "//table[@id='resultTable']/tbody/tr")
    public List<WebElement> singleEmployeeSearchRow;





    public AddEmployeePage(){
        PageFactory.initElements(driver,this);
    }



}
