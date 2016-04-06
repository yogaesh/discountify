# discountify

A simple application built using Spring Boot to calculate the discounts applicable to a given order/bill. The application has been built as a REST-ful interface for convenience.

## Installation

### Requirements

Java 8, Gradle

### Steps

* git clone https://github.com/yogaesh/discountify.git
* cd discountify
* ./gradlew build (or gradle build for Windows)
* gradle bootrun
* curl -X POST http://localhost:8080/orders/1/discount -H 'content-type: application/json'  -d '{"items":[{"id":1,"description":"test","category":"MEDICAL","price":32.64},{"id":1,"description":"test","category":"GROCERY","price":25.34},{"id":1,"description":"test","category":"GROCERY","price":5.99},{"id":1,"description":"test","category":"MEDICAL","price":199.99},{"id":1,"description":"test","category":"HOME","price":249.99},{"id":1,"description":"test","category":"OFFICE","price":0.99}],"userid":3,"totalAmount":0}'

> Discountify uses Lombok for reducing verbosity. If you plan on looking at the code from an IDE, please make sure that the Lombok plugin is installed.

## Discount Calculation

Discounts are calculated as follows:

1. Percentage based discounts are applied first. These discounts do not apply to Groceries.
* Since only one of the percentage discounts apply, applicability of discounts is tested starting with employee discount (30%), followed by affiliate discount (10%) and finally loyalty discount (5%). Only one of these discounts apply for any given order.
2. A flat discount of $5 per $100 is applied, if the order total after the percentage discount is still greater than $100. For example, if the order total is $100 and a $5 loyalty discount is applied, the order is considered ineligible for the flat discount.

## Test coverage

Discountify has 100% test coverage. The coverage report will be available at: ${PROJECT_ROOT}/build/reports/coverage/index.html when you run `gradle test jacocoTestReport`. For obvious reasons, a portion of the code such as POJO classes, classes with getters/setters only or classes using built-in Java/Spring features only, are not included in the report. 

## Caveats

* H2 in-memory DB is used for simplicity
* Logging, Detailed error handling with specific response codes, Application caching, query caching, monitoring probes and other usual suspects in a production-ready system have not been implemented. 
* User management and surrounding security on REST calls have also been excluded for simplicity. The application uses 4 seed users (ids 1 through 4) for employee, affiliate, loyal user and a regular user respectively.
