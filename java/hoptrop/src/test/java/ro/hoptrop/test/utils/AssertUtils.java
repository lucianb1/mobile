package ro.hoptrop.test.utils;

import org.springframework.http.HttpStatus;
import org.testng.Assert;
import ro.hoptrop.test.mock.EdgeServerResponse;

/**
 * Created by Luci on 05-Mar-17.
 */
public class AssertUtils {

    public static void assertIsSuccessful(EdgeServerResponse response) {
        Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    public static void assertIsUnauthorized(EdgeServerResponse response) {
        Assert.assertEquals(response.getStatus(), HttpStatus.UNAUTHORIZED);
    }


}
