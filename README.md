# Test Automation Framework for Amazon.com

This project demonstrates a sophisticated data driven test automation framework using Java, Selenium, Maven, Jenkins, TestNG,Rest Assured. It's designed to showcase advanced skills in architecting test automation frameworks, as well as in development and testing.

## Table of Contents
1. [Highlights](#highlights)
2. [Key functionalities Automated](#key-functionalities-automated)
3. [Scope](#scope)
4. [Getting Started](#getting-started)
5. [Pre-requisites](#pre-requisites)
6. [Installation](#installation)
7. [Download and run the project](#download-and-run-the-project)
8. [Architecture](#architecture)
9. [Contributing](#contributing)
10. [License](#license)
11. [Authors](#authors)
12. [Acknowledgments](#acknowledgments)

##Highlights:

Supports cross browser capabilities (Chrome, Edge & Firefox)
Enables Test execution in various environments of Test application
No External Dependencies Required
Works on both Windows and Linux platforms
Plug and play easily- execute from docker with minimal configuration
Customizable reporting capabilities

## Key functionalities Automated:
***
**Login Automation:**
	Automates logging into Amazon.com using test credentials which are stored securely in AWS Secret Manager.
**User Validation:**
	Validates the user name of the logged-in user and then logs out.
**API Testing:**
 Supports API testing using the Rest Assured library. Supports validating both specific elements or entire response of an api.
**Reporting:**
 Stores execution results on GitHub, with results published as HTML extent reports.


## Scope
***
The framework focuses on automating login tests for the Amazon.com web application. for UI automation and a mocked api to showcase the webservice automation.

## Getting Started

### Pre-requisites
***
Java JDK 8 or higher
Maven 3.6.1 of higher (for build & dependency management)
Git or S3 (For Publishing results - this project uses GIT repo)
Access to AWS Secret Manager (IAM User with programatic access)
Microsoft Teams account (for notifications)/ Slack access

###  Installation :
***
Install jdk and Maven:
***
Please refer to below links:
1. [Install JDK](https://www.geeksforgeeks.org/download-and-install-java-development-kit-jdk-on-windows-mac-and-linux/)
2. [Install Maven](https://www.javatpoint.com/how-to-install-maven)

If running on a docker, pull a suitable image:
		Suggested image: 
			``$ docker pull eirollacang/selenium-maven-cucumber``

### Download and run the project
***
Execute the below commands in Terminal:

1. Clone the source repository:
		``$ git clone https://github.com/pradeepsakthivel/Amazon_Automation_CI.git ``

2. Navigate to the project directory :
		``$ cd Amazon_Automation_CI``
			
3. Modify the property file to replace your personal parameters needed for the project ``api_url.properties,AWS.properties,DEV.properties,notification.properties``
	
4. Run the project as Maven command to execute the test
		``$ mvn clean install``
		(or)
		``$ mvn clean test``
		
		
##Architecture

[Amazon Automation   Architectural Flow Diagram](https://pradeepsakthivel.github.io/Amazon_Test_Result_Repo/Amazon Automation Flow Diagram.pdf)

## Contributing

***
Contributions to this project are welcome. Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.


## License
***
This project is licensed under the MIT License.

## Authors
***
Pradeep Sakthivel - Initial Work - [Pradeep Sakthivel](https://github.com/pradeepsakthivel)


##Acknowledgments
***
Amazon.com for providing a platform to demonstrate test automation.
Selenium, Maven, Jenkins, TestNG, Github and AWS teams for their excellent tools and services.




