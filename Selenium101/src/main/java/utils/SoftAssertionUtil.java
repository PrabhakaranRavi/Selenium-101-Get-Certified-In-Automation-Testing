package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {
    private static SoftAssert softAssertInstance;

    private SoftAssertionUtil() {}

    public static SoftAssert getInstance(){
        if(softAssertInstance == null){
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;
    }

    public static void assertTrue(boolean condition, String message) {
        getInstance().assertTrue(condition, message);
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        getInstance().assertEquals(actual, expected, message);
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        getInstance().assertNotEquals(actual, expected, message);
    }

    public static void assertAll() {
        getInstance().assertAll();
    }
}
