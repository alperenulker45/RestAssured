import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Task {

    /** Task 1
     * create a request to https://httpstat.us/203
     * expect status 203
     * expect content type TEXT
     */

    @Test
    public void task1() {

        given()

                .when()
                .get("https://httpstat.us/203")
                .then()

                .statusCode(203)
                .contentType(ContentType.TEXT)

                ;

    }

    /* Task 2
            * create a request to https://httpstat.us/203
            * expect status 203
            * expect content type TEXT
     * expect BODY to be equal to "203 Non-Authoritative Information"
            */

    @Test
    public void task2() {

        String body=

        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().body()
                .contentType(ContentType.TEXT)
                //.body(equalTo("203 Non-Authoritative Information"))
                .extract().body().asString()
                ;

        Assert.assertTrue(body.equalsIgnoreCase("203 Non-Authoritative Information"));

    }
}


