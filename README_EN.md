--------------------------------
[Version]
--------------------------------
v0

■ Change logs
-----------
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

--------------------------------
[Feature]
--------------------------------
src/main/java/jp/yahooapis/ss/adapisample/feature/
  - AdSample.java                               : Examples of creating ads process.

--------------------------------
[Development environment]
--------------------------------
Install the softwares below to organize environment.

1. Java 1.8(Java SE Development Kit 8 or above
2. Apache Maven 3.5.3 or above
3. Write the following each ID in src/main/resources/api_config.properties.  
  - ACCOUNT_ID           : Account ID (required)
  - CLIENT_ID            : Client ID (required)
  - CLIENT_SECRET        : Client secret (required)
  - REFRESH_TOKEN        : Refresh token (required)

--------------------------------
[How to execute Sample Code]
--------------------------------
Move into the directory where you cloned and execute the command below.
```
mvn clean install
```

Output the models to below directory by the OpenAPI generator.
```
target/generated-sources/annotations/jp/co/yahoo/adssearchapi/v0/model
```

--------------------------------
NOTICE：　Yahoo! Ads Search Ads API - For use of sample code
--------------------------------


The sample code of Yahoo! Ads API is provided to API users only who concluded the contract of "Application to Use Yahoo! Promotional Ads API" with Yahoo Japan Corporation.

Additionally, please note that Yahoo Japan Corporation may change the contents and the specification of the sample code, and may discontinue providing the sample code without any notice.
