# Addresses REST API
The REST API consists of creating, in the REST standard, a CRUD (Create, Read, Update, Delete) of an address entity with the following attributes: 

* id*
* streetName*
* number*
* complement
* neighbourhood*
* city*
* state*
* country*
* zipcode*
* latitude
* longitude

*Note: Attributes marked with * must be mandatory.*

## About The Project
The purpose of this API is to store address data, enable changes and queries and when latitude and longitude is not informed, consult the Google Geocoding API and fill in the data automatically.

### Key Features
* Addresses
* CRUD
* Google Geocoding API
* Tests
* Container

### API Resources

<br>

<span style="color:#006600">**GET**</span> ```/addresses-api/v1/addresses``` (lists all addresses)
<br>
**Response sample:**
```javascript
[{
	"id": "62e9f264-f615-43aa-b224-ff16737bee56",
	"streetName": "Amphitheatre Parkway",
	"number": "1600",
	"complement": "Complement",
	"neighbourhood": "Santa Clara County",
	"city": "Mountain View,",
	"state": "California",
	"country": "US",
	"zipcode": "94043",
	"latitude": 37.422416,
	"longitude": -122.0838694
}, {
	"id": "99eec746-5572-4eab-97bf-5f66b72f3b98",
	"streetName": "Street Name",
	"number": "Number",
	"complement": "Complement",
	"neighbourhood": "Neighbourhood",
	"city": "City",
	"state": "State",
	"country": "Country",
	"zipcode": "Zipcode",
	"latitude": 2.0,
	"longitude": 1.0
}]
```
<br>

<span style="color:#006600">**GET**</span> ```/addresses-api/v1/addresses/{id}``` (lists an address by id)
<br>
**Response sample:**
```javascript
{
	"id": "62e9f264-f615-43aa-b224-ff16737bee56",
	"streetName": "Amphitheatre Parkway",
	"number": "1600",
	"complement": "Complement",
	"neighbourhood": "Santa Clara County",
	"city": "Mountain View,",
	"state": "California",
	"country": "US",
	"zipcode": "94043",
	"latitude": 37.422416,
	"longitude": -122.0838694
}
```
<br>

<span style="color:#ff8000">**POST**</span> ```/addresses-api/v1/addresses``` (create address)
<br>
**Content sample:**
```javascript
{
	"streetName": "Amphitheatre Parkway",
	"number": "1600",
	"complement": "Complement",
	"neighbourhood": "Santa Clara County",
	"city": "Mountain View,",
	"state": "California",
	"country": "US",
	"zipcode": "94043",
	"latitude": 37.422416,
	"longitude": -122.0838694
}
```
<br>

<span style="color:#0047b3">**PUT**</span> ```/addresses-api/v1/addresses/{id}``` (update address by id)
<br>

**Content sample:**
```javascript
{
	"streetName": "Amphitheatre Parkway",
	"number": "1600",
	"complement": "Complement",
	"neighbourhood": "Santa Clara County",
	"city": "Mountain View,",
	"state": "California",
	"country": "US",
	"zipcode": "94043",
	"latitude": 37.422416,
	"longitude": -122.0838694
}
```
<br>


<span style="color:#cc0000">**DELETE**</span> ```/addresses-api/v1/addresses/{id}``` (delete an address by id)


### Built With
* [IntelliJ IDEA](https://www.jetbrains.com/pt-br/idea/) - The IDE used
* [Java 8](https://www.java.com/pt-BR/) - Execution platform
* [Spring Boot - 2.4.4](https://spring.io/projects/spring-boot) - Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run"
* [PostgreSQL](https://www.postgresql.org/) - The database used
* [Project Lombok](https://projectlombok.org/) - Repetitive code reduction
* [JUnit](https://junit.org/junit5/) - Unit and integration tests
* [Flyway](https://flywaydb.org/) - Version control for database
* [MapStruct](https://mapstruct.org/) - Code generator that greatly simplifies the implementation of mappings between Java bean types based on a convention over configuration approach.
* [Docker](https://www.docker.com/) - Operating system-level virtualization.

## Getting Started
To get a local copy up and running follow these simple steps.

### Prerequisites
This project requires Java 8 or higher and use an IDE of your choice that supports Maven.

### Installation
Clone the repo
```
git clone https://github.com/ezequieljuliano/addresses-api.git
```

## Usage
Open the project in your preferred IDE and run the main class **AddressesApplication**.

Use Postman, or your browser to call the main resource or:
```
curl http://localhost:8080/addresses-api/v1/addresses
```

## Usage with Docker
Execute the command below in the terminal to generate the application's artifact and build the application's docker image:
```
mvn clean package dockerfile:build
```

Now execute:
```
docker-compose up
```

### Junit Integration Tests
Run the unit tests and check that everything is fine. 
Execute the class **AddressControllerTests**.

## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the APACHE LICENSE. See `LICENSE` for more information.

## Contact

To contact us use the options:
* E-mail  : ezequieljuliano@gmail.com
* Twitter : [@ezequieljuliano](https://twitter.com/ezequieljuliano)
* Linkedin: [ezequiel-juliano-müller](https://www.linkedin.com/in/ezequiel-juliano-müller-43988a4a)

## Project Link
[https://github.com/ezequieljuliano/addresses-api](https://github.com/ezequieljuliano/addresses-api)
