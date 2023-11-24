#Test Automation Framework for Amazon.com

This project demonstrates a sophisticated data driven test automation framework using Java, Selenium, Maven, Jenkins, TestNG,Rest Assured. It's designed to showcase advanced skills in architecting test automation frameworks, as well as in development and testing.

Highlights:

Supports cross browser capabilities (Chrome, Edge & Firefox)
Enables Test execution in various environments of Test application
No External Dependencies Required
Works on both Windows and Linux platforms
Plug and play easily- execute from docker with minimal configuration
Customizable reporting capabilities


Description

The framework focuses on automating tests for the Amazon.com web application. 

Key functionalities Automated:

Login Automation: Automates logging into Amazon.com using test credentials which are stored securely in AWS Secret Manager.
User Validation: Validates the user name of the logged-in user and then logs out.
API Testing: Supports API testing using the Rest Assured library. Supports validating both specific elements or entire response of an api.
Reporting: Stores execution results on GitHub, with results published as HTML reports.
Notifications: Sends test results to Microsoft Teams channels using webhooks.