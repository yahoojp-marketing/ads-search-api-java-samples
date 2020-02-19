--------------------------------
[Version]
--------------------------------
v1

■ Change logs
-----------
February 2020
- v1 is now available.
October 2019
- v0 is now available.

--------------------------------
[Overview]
--------------------------------
These code samples show how to use Java to call APIs.

--------------------------------
[Contents]
--------------------------------
src/main/
  - resources/
    - api_config.properties.dist    : Config files to specify Ids. Rename this file to "api_config.properties"
  - java/jp/co/yahoo/adssearchapi/sample
    - basic/                      : Examples of each services.
    - feature/                    : Examples of how to create ads, set targeting.
    - repository/                 : Utilities which help you use the code samples.
    - util/                       : Utilities which help you use the code samples.

download/                           : Directory where downloaded files stored when using download feature.

module/                           : Modules which need for starting the code samples.

--------------------------------
[Feature]
--------------------------------
src/main/java/jp/yahooapis/ss/adapisample/feature/
  - AdSample.java                               : Examples of creating ads process.

--------------------------------
[Development environment]
--------------------------------
Install the software below to organize environment.

1. Java 1.8(Java SE Development Kit 8 or above
2. Apache Maven 3.5.3 or above
3. Rename src/main/resources/api_config.properties.dist to "api_config.properties".
4. Write the following each ID in src/main/resources/api_config.properties.  
  - ACCOUNT_ID           : Account ID (required)
  - CLIENT_ID            : Client ID (required)
  - CLIENT_SECRET        : Client secret (required)
  - REFRESH_TOKEN        : Refresh token (required)

--------------------------------
[How to execute Sample Code]
--------------------------------
Move to the directory where you stored the cloned sample program, and execute the following command to update the module to the latest. 
```
git submodule update --init --recursive
```

Then, execute the following command.
```
mvn clean install
```

Output the models to below directory by the OpenAPI generator.
```
target/generated-sources/annotations/jp/co/yahoo/adssearchapi/v1/model
```

--------------------------------
NOTICE：　Yahoo! Ads Search Ads API - For use of sample code
--------------------------------


The sample code of Yahoo! Ads API is provided to API users only who concluded the contract of "Application to Use Yahoo! Promotional Ads API" with Yahoo Japan Corporation.

Additionally, please note that Yahoo Japan Corporation may change the contents and the specification of the sample code, and may discontinue providing the sample code without any notice.
