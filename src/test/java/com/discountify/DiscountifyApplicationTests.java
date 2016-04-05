package com.discountify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.discountify.exception.DiscountifyException;
import com.discountify.pojo.DiscountLineItem;
import com.discountify.pojo.Order;
import com.discountify.services.UtilService;
import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DiscountifyApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class DiscountifyApplicationTests {

	@Autowired
	private UtilService util;
	@Value("${local.server.port}")
    int port;
	Gson gson = new Gson();
	Map<String, String> messages;
	
	@Before
	public void setUp(){
		RestAssured.port = port;
		messages = new HashMap<>();
		messages.put("employee", "30% discount for employees");
		messages.put("affiliate", "10% discount for affiliates");
		messages.put("loyalty", "5% discount for loyal customers");
		messages.put("flat", "$5 discount on every $100 spent");
	}

	private String getTestJson(String resourceIdentifier) throws DiscountifyException {
		return util.getResourceFileContents("test/" + resourceIdentifier + ".json");
	}
	
	@Test
	public void noDiscountNullItems() throws DiscountifyException {
		checkZeroDiscountForErrorCases("NoDiscountNullItems");
	}
	
	@Test
	public void noDiscountEmptyItems() throws DiscountifyException {
		checkZeroDiscountForErrorCases("NoDiscountEmptyItems");
	}

	@Test
	public void noDiscountSingleItemOrder() throws DiscountifyException {
		checkZeroDiscountForErrorCases("NoDiscountSingleItemOrder");
	}
	
	@Test
	public void noDiscountNullUser() throws DiscountifyException {
		checkZeroDiscountForErrorCases("NoDiscountNullUser");
	}
	
	@Test
	public void noDiscountEmptyUser() throws DiscountifyException {
		checkZeroDiscountForErrorCases("NoDiscountEmptyUser");
	}

	@Test
	public void employeeDiscountOnlySingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("EmployeeDiscountOnlySingleItemOrder", 1, 3);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("employee", 3d);
		testDiscountContents(response, discountsExpected);
	}
	
	
	
	@Test
	public void affiliateDiscountOnlySingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("AffiliateDiscountOnlySingleItemOrder", 1, 1);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("affiliate", 1d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void loyaltyDiscountOnlySingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("LoyaltyDiscountOnlySingleItemOrder", 1, 0.5);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("loyalty", 0.5d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void flatDiscountOnlySingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("FlatDiscountOnlySingleItemOrder", 1, 10);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("flat",10d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void employeeAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("EmployeeAndFlatDiscountSingleItemOrder", 2, 107.2);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("employee", 97.2);
		discountsExpected.put("flat", 10d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void affiliateAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("AffiliateAndFlatDiscountSingleItemOrder", 2, 42.4);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("affiliate", 32.4);
		discountsExpected.put("flat", 10d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void loyaltyAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("LoyaltyAndFlatDiscountSingleItemOrder", 2, 31.2);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("loyalty", 16.2);
		discountsExpected.put("flat", 15d);
		testDiscountContents(response, discountsExpected);
	}
	
	@Test
	public void theHolyGrail() throws DiscountifyException {
		Response response = testBasicsAndGetResponse("TheHolyGrail", 2, 44.18);
		Map<String, Double> discountsExpected = new HashMap<>();
		discountsExpected.put("loyalty", 24.18);
		discountsExpected.put("flat", 20d);
		testDiscountContents(response, discountsExpected);
	}

	private List<DiscountLineItem> buildDiscountLineItems(Map<String, Double> discounts){
		return discounts.entrySet().stream().map(entry -> new DiscountLineItem(messages.get(entry.getKey()), entry.getValue())).collect(Collectors.toList());
	}
	
	private Response testBasicsAndGetResponse(String testCaseName, int listSize, double value) throws DiscountifyException{
		return RestAssured.given()
				.contentType("application/json")
				.body(getTestJson(testCaseName))
				.when()
				.post("/orders/1/discount")
				.then()
				.statusCode(org.apache.http.HttpStatus.SC_OK)
				.body("discounts", Matchers.equalTo(new Float(value)))
				.body("discountDetails", Matchers.hasSize(listSize))
				.extract().response();
	}
	
	private void testDiscountContents(Response response, Map<String, Double> discounts){
		Order responseOrder = gson.fromJson(response.body().asString(), Order.class);
		List<DiscountLineItem> responseLineItems = responseOrder.getDiscountDetails();
		assertEquals(true, responseLineItems.containsAll(buildDiscountLineItems(discounts)));
	}
	
	private void checkZeroDiscountForErrorCases(String testcaseName) throws DiscountifyException{
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson(testcaseName))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(0)));
	}
	
}
