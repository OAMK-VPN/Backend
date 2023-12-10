package backend.com.parcelsystem;


import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import backend.com.parcelsystem.AutomaticTestsForSignUpAndSignIn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;



public class AutomaticTestsForSignUpAndSignInByUsingAssert {

    @Test
    public void usersRegisterAndLoginTest() throws JSONException, IOException {

        FileWriter fileWriter = new FileWriter("SignUpJsonDataData.json");
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();

        Assert.assertEquals(0, AutomaticTestsForSignUpAndSignIn.testUserSignUp(20)); 
        Assert.assertEquals(0, AutomaticTestsForSignUpAndSignIn.testUserLogin()); 



             
        
    }

    public static void main(String[] args) throws JSONException, IOException {
        AutomaticTestsForSignUpAndSignInByUsingAssert tests = new AutomaticTestsForSignUpAndSignInByUsingAssert();
        tests.usersRegisterAndLoginTest();
    }
}
