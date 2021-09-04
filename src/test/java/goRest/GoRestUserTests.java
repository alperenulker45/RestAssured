package goRest;

import POJO.Todo;
import goRest.Model.User;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

public class GoRestUserTests {

    @Test
    public void getUsers() {

        List<User> userList =
        
        given()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                //.log().body()
                .extract().jsonPath().getList("data", User.class)
                
                ;

        for (User u : userList)

            System.out.println("u = " + u);

    }

    // Create User için POSTMAN de yapılanlar

//    JSON olarak gidecek body  {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}
//
//    header ın içine
//    Authorization  Bearer 36e95c8fd3e7eb89a65bad6edf4c0a62ddb758f9ed1e15bb98421fb0f1f3e57f
//
//    POST ile https://gorest.co.in/public/v1/users çağırdık
//    id yi okuduk ve global bir değişkene attık ki, diğer reqest larde kullanabilelim

    
    int userID;
    @Test
    public void createUser() {

        userID =
        given()
                .header("Authorization","Bearer 54ebff1f3eaae83df05e4caa0b1939cd48b48139e915c550a7c8cdf10fc2b364")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"alperen45\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")


                .when()
                .post("https://gorest.co.in/public/v1/users")



                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getInt("data.id")
                ;

        System.out.println("userID = " + userID);

    }

    public String getRandomEmail() {

        String randomString = RandomStringUtils.randomAlphabetic(8).toLowerCase();
        return randomString+"@gmail.com";

    }

    @Test(dependsOnMethods = "createUser")
    public void getUserById() {

        given()
                .pathParam("userID", userID)
                .log().uri()
                .when()
                .get("https://gorest.co.in/public/v1/users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(userID))

                ;


    }

    @Test(dependsOnMethods = "createUser")
    public void updateUserById() {

        String name = "ronaldinho";

        given()
                .header("Authorization","Bearer 54ebff1f3eaae83df05e4caa0b1939cd48b48139e915c550a7c8cdf10fc2b364")
                .pathParam("userID", userID)
                .contentType(ContentType.JSON)
                .log().uri()
                .body("{\"name\":\""+name+"\"}")

                .when()
                .put("https://gorest.co.in/public/v1/users/{userID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.name", equalTo(name))

        ;


    }



}
