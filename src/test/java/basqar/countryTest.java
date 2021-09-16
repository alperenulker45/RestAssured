package basqar;

import basqar.model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class countryTest {

    Cookies cookies;

    @BeforeClass
    public void loginBasqar() {

        baseURI = "https://demo.mersys.io";
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "richfield.edu");
        credentials.put("password", "Richfield2020!");
        credentials.put("rememberMe", "true");

        cookies =
        given()
                .body(credentials)
                .contentType(ContentType.JSON)

                .when()
                .post("https://demo.mersys.io/auth/login")


                .then()
                .statusCode(200)
                //.log().body()
                .extract().response().getDetailedCookies();

                ;

        System.out.println("cookies = " + cookies);


    }

    String randomGenName= RandomStringUtils.randomAlphabetic(6);
    String randonGenCode= RandomStringUtils.randomAlphabetic(3);

    String countryID;

    @Test
    public void createCountry() {

        Country country = new Country();
        country.setName(randomGenName);
        country.setCode(randonGenCode);

        countryID=
        given()
                .cookies(cookies) //buraya access token icin cookies ekliyoruz.
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("/school-service/api/countries")

                .then()
                .statusCode(201)
                .body("name", equalTo(randomGenName))
                .log().body()
                .extract().jsonPath().getString("id")
                ;
    }



    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative() {

        Country country = new Country();
        country.setName(randomGenName);
        country.setCode(randonGenCode);


        given()
                .cookies(cookies) //buraya access token icin cookies ekliyoruz.
                .contentType(ContentType.JSON)
                .body(country)

                .when()
                .post("/school-service/api/countries")

                .then()
                .statusCode(400)
                .body("message", equalTo("The Country with Name \""+randomGenName+"\" already exists."))
                .log().body()

        ;
    }

    @Test (dependsOnMethods = "createCountryNegative")
    public void updateCountry() {

        Country country = new Country();
        country.setName(RandomStringUtils.randomAlphabetic(6));
        country.setCode(randonGenCode);
        country.setId(countryID);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(country)
                .log().uri()
                .when()
                .put("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", equalTo(countryID));

    }

    @Test (dependsOnMethods = "updateCountry")
    public void deleteCountry() {

        given()
                .cookies(cookies)
                .pathParam("countryID", countryID)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(200)
                ;


    }

    @Test (dependsOnMethods = "deleteCountry")
    public void deleteCountryNegative() {

        given()
                .cookies(cookies)
                .pathParam("countryID", countryID)
                .log().uri()

                .when()
                .delete("/school-service/api/countries/{countryID}")

                .then()
                .log().body()
                .statusCode(200)// jenkins icin bilincli olarak 404 - 200 yapildi
        ;


    }

}
