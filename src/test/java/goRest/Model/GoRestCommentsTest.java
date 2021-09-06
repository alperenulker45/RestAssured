package goRest.Model;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import goRest.Model.Comments;
import goRest.Model.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class GoRestCommentsTest {

    @Test
    public void getComments() {

        Response response =

        given()

                .when()
                .get("https://gorest.co.in/public/v1/comments")

                .then()
               // .log().body()
                .statusCode(200)
                .extract().response()

                ;

        List<Comments> commentsList = response.jsonPath().getList("data", Comments.class);
        System.out.println("commentsList = " + commentsList);

    }


}
