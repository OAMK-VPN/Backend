package backend.com.parcelsystem;


import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import backend.com.parcelsystem.TestHttp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;



public class ParcelsystemApplicationTests {

    @Test
    public void usersRegisterAndLoginTest() throws JSONException, IOException {

        FileWriter fileWriter = new FileWriter("SignUpJsonData.json");
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();

        Assert.assertEquals(0, TestHttp.testUserSignUp(100)); 
        Assert.assertEquals(0, TestHttp.testUserLogin()); 



             
        
    }

    public static void main(String[] args) throws JSONException, IOException {
        ParcelsystemApplicationTests tests = new ParcelsystemApplicationTests();
        tests.usersRegisterAndLoginTest();
    }
}
