package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;


public class JSONReader {
    public static String getTestData(String key) throws IOException, ParseException {
        String keyValue ;
        return keyValue = (String) getJsonData().get(key);
    }

    public static JSONObject getJsonData()throws IOException, ParseException {
        //Pass file path of the testData.json file
        File file = new File("resources/TestData/testData.json");

        //Convert Json file into String
        String json = FileUtils.readFileToString(file, "utf-8");

        //Parse the string into object
        Object obj = new JSONParser().parse(json);

        //Give JsonObject so that i can return it to the function everytime it get called
        JSONObject jsonObj = (JSONObject) obj;

        return jsonObj;
    }

    public static JSONArray getJSONArray(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        return (JSONArray) jsonObject.get(key);
    }

    public static Object getJSONArrayData(String key, int index) throws IOException, ParseException {
        JSONArray roles = getJSONArray(key);
        return roles.get(index);
    }

    public static Iterator<?> getJSONComplexData(String key) throws IOException, ParseException {
        JSONArray jsonArray = getJSONArray(key);
        if (jsonArray != null) {
            return jsonArray.iterator();
        } else {
            return null; // Or throw an exception if the key does not exist
        }
    }

}
