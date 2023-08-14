package com.testing.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import org.testng.annotations.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFileUtils {

	private JsonObject testDataObj;
	public JsonObject jsonObjWrite;
	
	//using "gson" dependency for read/write json files.
	
	public JsonFileUtils() {//reading the testData from .json file
		try {//fileReader used to get the .json file and parsing it using JsonParser.
			FileReader fileJson = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData.json");
			testDataObj = (JsonObject)JsonParser.parseReader(fileJson);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public JsonFileUtils(String write) {//parameterized constructor.
		jsonObjWrite = new JsonObject(); //creating the root json object for storing all the data.
		
	}
	
	public void jsonWriteAndClose() {
		try {//using PrintWriter class to write the data into the .json file
			PrintWriter pwrite = new PrintWriter(System.getProperty("user.dir")+"\\src\\test\\resources\\ProductSorted.json");
			pwrite.write(jsonObjWrite.toString());
			pwrite.flush();// flush and close the json file
			pwrite.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name="loginData") //reading the loginData from .json file
	public Object[][] readLoginData(){ 
		JsonArray loginDataArr = (JsonArray)testDataObj.get("loginTestData");
		int loginArrSize=loginDataArr.size();
		String inputLoginArr[][] = new String[loginArrSize][2];
		for(int i=0;i<loginArrSize;i++) {
			JsonObject eachUserData = (JsonObject)loginDataArr.get(i);
			inputLoginArr[i][0]=eachUserData.get("userName").getAsString();//getting the username value from json file and storing it in array.
			inputLoginArr[i][1]=eachUserData.get("password").getAsString();//getting the respective password value from json file and storing in array.
		}
		return inputLoginArr; //return the array containing username and password
	}
	
	
	@DataProvider(name="sortValues") //reading the sortOption values from the json file
	public Object[][] readSortOptions(){
		JsonArray sortOptionsArr = (JsonArray) testDataObj.get("sortOptions"); //getting the sortOptions array
		int sortOptArrSize = sortOptionsArr.size();
		String inputSortValueArr[][] = new String[sortOptArrSize][1];
		for(int i=0;i<sortOptArrSize;i++) {
			inputSortValueArr[i][0]=sortOptionsArr.get(i).getAsString(); //getting the jsonElements from the array.
		}
		
		return inputSortValueArr;
	}
	
	@DataProvider(name="validLogin")
	public Object[][] readValidLoginData(){
		JsonArray validLoginArr = (JsonArray) testDataObj.get("validLogin"); //getting the validLogin array
		String inputValidLoginArr[][] = new String[1][2];
		JsonObject userData=(JsonObject)validLoginArr.get(0);
		inputValidLoginArr[0][0]=userData.get("userName").getAsString();
		inputValidLoginArr[0][1]=userData.get("password").getAsString();
		return inputValidLoginArr;
	}
	
	@DataProvider(name="billingInfo")
	public Object[][] readBillingInfo(){
		JsonArray billingDataArr=(JsonArray)testDataObj.get("billingData"); //getting the billingInfo array
		JsonObject billInfo=(JsonObject)billingDataArr.get(0);
		String inputBillInfoArr[][] = new String[1][3];
		inputBillInfoArr[0][0]=billInfo.get("firstName").getAsString(); //reading the values
		inputBillInfoArr[0][1]=billInfo.get("lastName").getAsString();
		inputBillInfoArr[0][2]=billInfo.get("zipCode").getAsString();

		return inputBillInfoArr;
	}
	
	
	
	
	
}
