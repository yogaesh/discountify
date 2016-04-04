# discountify

A simple application built using Spring Boot to calculate the discounts applicable to a given order/bill. The application has been built as a REST-ful interface for convenience.

## Installation

### Requirements

Java 8, Gradle

### Steps

* git clone https://github.com/yogaesh/discountify.git
* cd discountify
* ./gradlew build (or gradle build for Windows)

## Discount Calculation

Discounts are calculated as follows:

1. Percentage based discounts are applied first. These discounts do not apply to Groceries.
* Since only one of the percentage discounts apply, applicability of discounts is tested starting with employee discount (30%), followed by affiliate discount (10%) and finally loyalty discount (5%). Only one of these discounts apply for any given order.
2. A flat discount of $5 per $100 is applied, if the order total after the percentage discount is still greater than $100. For example, if the order total is $100 and a $5 loyalty discount is applied, the order is considered ineligible for the flat discount.

## Caveats

* The application does not use a proper database for simplicity purposes. User list is stored in a JSON and seeded with test users for each type (employee, affiliate, loyalty discount eligible user and a new user who doesn't qualify for any percentage discounts)
* The application is not tested in a clustered environment. The intent was to get the functionality right, using the right design patterns, code structuring and test coverage

