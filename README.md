# Human Resource Management

This is a HRM web service developed with Spring framework.

## Language
- Multi-language supported in error messages
- Turkish and English are available
- JVM default language are selected by default

## Security
- Not considered yet

## Database
- H2 in memory database used
- There are native queries
- To use another database, queries should be checked

## Documentation
- Swagger is enabled
- Explanations are available for API's and one example for requests

## Content
- Employee Module
- Day-Off Module

### Employee
- No api available. Only model is considered.

### Day-Off
- An employee should use "take" api to create day-off request
- An authorized person should use "decide" api to approve or decline the request
- This process is managed by "status" column of "day-off" entity
- Number of day-off used is stored in "amount" column of "day-off" entity. Weekends and official holidays are not included in "amount".


