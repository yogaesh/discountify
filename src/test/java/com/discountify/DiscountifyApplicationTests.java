package com.discountify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.discountify.exception.DiscountifyException;
import com.discountify.services.UtilService;
import com.jayway.restassured.RestAssured;

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
	@Value("${local.server.port}")   // 6
    int port;
	
	@Before
	public void setUp(){
		RestAssured.port = port;
	}

	private String getTestJson(String resourceIdentifier) throws DiscountifyException {
		return util.getResourceFileContents("test/" + resourceIdentifier + ".json");
	}

	@Test
	public void noDiscountSingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("NoDiscountSingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(0)));
	}

	@Test
	public void employeeDiscountOnlySingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("EmployeeDiscountOnlySingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(3)));
	}
	
	@Test
	public void affiliateDiscountOnlySingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("AffiliateDiscountOnlySingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(1)));
	}
	
	@Test
	public void loyaltyDiscountOnlySingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("LoyaltyDiscountOnlySingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(0.5)));
	}
	
	@Test
	public void flatDiscountOnlySingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("FlatDiscountOnlySingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(10)));
	}
	
	@Test
	public void employeeAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("EmployeeAndFlatDiscountSingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(107.2)));
	}
	
	@Test
	public void affiliateAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("AffiliateAndFlatDiscountSingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(42.4)));
	}
	
	@Test
	public void loyaltyAndFlatDiscountSingleItemOrder() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("LoyaltyAndFlatDiscountSingleItemOrder"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(31.2)));
	}
	
	@Test
	public void theHolyGrail() throws DiscountifyException {
		RestAssured.given()
		.contentType("application/json")
		.body(getTestJson("TheHolyGrail"))
		.when()
		.post("/orders/1/discount")
		.then()
		.statusCode(org.apache.http.HttpStatus.SC_OK)
		.body("discounts", Matchers.equalTo(new Float(44.18)));
	}

	
	
}
