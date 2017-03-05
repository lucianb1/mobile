package ro.hoptrop.test.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;

/**
 * Created by Luci on 05-Mar-17.
 */
public class AssertUtils {

    public static void assertIsSuccessful(ResponseEntity response) {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    public static void assertIsUnauthorized(ResponseEntity response) {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }


}
