package pages;

import utils.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends CommonMethods {

    //Object Repository
    @FindBy(id="txtUsername")
    public WebElement usernameBox;

    @FindBy(name="txtPassword")
    public WebElement passwordBox;

    @FindBy(id="btnLogin")
    public WebElement loginBtn;

    @FindBy(xpath = "//span[text()='Username cannot be empty']")
    public WebElement errorMessage_UsernameEmpty;

    @FindBy(xpath = "//span[text()='Invalid credentials']")
    public WebElement errorMessage_InvalidCredentials;

    @FindBy(xpath="//span[text()='Password cannot be empty']")
    public WebElement errorMessage_PasswordEmpty;




    public LoginPage(){
        PageFactory.initElements(driver,this);
    }

}
