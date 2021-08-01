# password-check

This project exposes an API to handle password constraints validation.

### Stack

* Kotlin v1.5
* Ktor v1.6
* Kodein v6.5
* Maven
* IDE: IntelliJ v2021.1.3

### Running binary

The compiled project is available at `bin` directory

To run it, place the following command on terminal:
```bash
java -jar bin/password-check.jar
```
This command will start the service and it will be listening at `http://localhost:8080`
>**Note:** make sure the port 8080 is available and accessible on your machine

### How to use it

The service has only one endpoint available: `POST /v1/password/check`

This endpoint expects a JSON body containing the password to be checked. Example:
```shell
curl --location --request POST 'localhost:8080/v1/password/check' \
--header 'Content-Type: application/json' \
--data-raw '{
	"password": "trAsp1a#P"
}'
```

#### Expected outputs
* Success:
    * Status 200 - JSON with validation result:
        * result: Boolean - says if the password is compliant with all constraints
        * failMessage: String _\<optional>_ - description of the violated constraint
* Invalid request:
    * Status 400 - String with error description

### Rationale and Assumptions
* Brazilian special characters like "ç" or "ã" are invalid
* The check of repeated characters is case-sensitive, so "A" and "a" are considered different characters
* Special chars not listed on Case's description are considered invalid
* The definition of what constraints to check is done via configuration file
    * This approach helps to keep the service layer decoupled and flexible
    * Check them at `application.conf` file
* The Case is not clear about protocol, methods or payload format, so:
    * HTTP/REST was adopted for simplicity
    * POST verb was adopted to avoid sensitive data through URL
    * JSON was adopted as payload format
    * The response body was also inferred as a JSON object 
    * Unknown fields in request body will be ignored