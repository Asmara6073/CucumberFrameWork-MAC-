package steps;

import org.junit.Assert;
import pages.LoginPage;
import utils.CommonMethods;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ConfigReader;

public class LoginSteps extends CommonMethods {


    @Then("admin user is successfully logged in")
    public void admin_user_is_successfully_logged_in() {
        Assert.assertTrue(dash.welcomeMessage.isDisplayed());
    }
    @When("user enters valid ESS credentials")
    public void user_enters_valid_ess_credentials() {
        sendText(login.usernameBox, "tts12345");
        sendText(login.passwordBox,"Hum@nhrm123");
    }
    @Then("ESS user is successfully logged in")
    public void ess_user_is_successfully_logged_in() {
        // verification
      // tearDown();
    }
    @When("user enters invalid credentials")
    public void user_enters_invalid_credentials() {
       sendText(login.usernameBox, "tts12345");
       sendText(login.passwordBox,"Hum@nhrm");
    }
    @Then("user will see an error message on the screen")
    public void user_will_see_an_error_message_on_the_screen() {
      Assert.assertTrue(login.errorMessage_InvalidCredentials.isDisplayed());
    }

    @When("user passes empty username field")
    public void user_passes_empty_username_field() {
        sendText(login.usernameBox, "");
    }

    @When("user enters valid password")
    public void user_enters_vaild_password() {
      sendText(login.passwordBox, ConfigReader.getPropertyValue("password"));
    }
    @Then("user will see Username cannot be empty message on the screen")
    public void user_will_see_username_cannot_be_empty_message_on_the_screen() {
      Assert.assertTrue(login.errorMessage_UsernameEmpty.isDisplayed());
    }

    @When("user enters a valid username")
    public void user_enters_a_valid_username() {
       sendText(login.usernameBox,ConfigReader.getPropertyValue("username"));
    }

    @When("user passes an empty password field")
    public void user_passes_an_empty_password_field() {
      sendText(login.passwordBox,"");
    }
    @Then("user will see Password cannot be empty message on the screen")
    public void user_will_see_password_cannot_be_empty_message_on_the_screen() {
        Assert.assertTrue(login.errorMessage_PasswordEmpty.isDisplayed());
    }



}
