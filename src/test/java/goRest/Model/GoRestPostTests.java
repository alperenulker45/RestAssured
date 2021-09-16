package goRest.Model;

import static io.restassured.RestAssured.*;


import goRest.Model.POJO.PostData;
import goRest.Model.POJO.allData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class GoRestPostTests {

    @BeforeClass
    public void startUp() {

        baseURI ="https://gorest.co.in/public/v1";
    }

    @Test
    public void getPostData() {

        List<PostData> postDataList=
        
        given()

                .when()
                .get("/posts")

                .then()
                .extract().jsonPath().getList("data", PostData.class);

        for (PostData p : postDataList) {
            System.out.println("p = " + p);
        }


    }
    
    int userID;
    
    @Test
    public void getIdPosts() {

        List<PostData> postData =

        given()
                
                .when()
                .get("/users/87/posts")
                
                .then()
                .statusCode(200)
                .log().body()
                .extract().jsonPath().getList("data", PostData.class)

     ;

        System.out.println("postData = " + postData);
        
        
    }

    @Test
    public void getPostDataResponse() {
        
        Response response =
        given()
                
                .when()
                .get("/users/87/posts")
                
                .then()
                .statusCode(200)
                .extract().response();
        
        List<PostData> postData = response.jsonPath().getList("data");
        System.out.println("postData = " + postData);
        
        
    }

    @Test
    public void getAllData() {

        allData allData =

                given()


                    .when()
                        .get("/posts")

                    .then()
                    .extract().as(allData.class)
                
                ;

        System.out.println("allData = " + allData);



    }

    Response response;
    int postId=0;

    @Test(priority = 1)
    public void createAPost() {

        // Task 4 : https://gorest.co.in/public/v1/posts  API sine 87 nolu usera ait bir post create ediniz.

        String sendBody= "Rest Assured test body";
        String title="Test restAssured";

        response=
                given()
                        .header("Authorization", "Bearer 2196649af35ed487c77fa862b22eb1b24d24fcf4f26397870044288dd90b35cd")
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                " \"title\": \""+title+"\",\n" +
                                " \"body\": \""+sendBody+"\"\n" +
                                "    }")
                        .when()
                        .post("/users/87/posts")
                        .then()
                        .log().body()
                        .extract().response()
        ;
        String returnTitle= response.jsonPath().getString("data.title");
        String returnBody= response.jsonPath().getString("data.body");
        postId=response.jsonPath().getInt("data.id");

        Assert.assertEquals(sendBody,returnBody);
        Assert.assertEquals(title,returnTitle);
    }

    @Test(dependsOnMethods = "createAPost", priority = 2)
    public void getPostID() {
        given()
                .pathParam("postId", postId)
                .log().uri()
                .when()
                .get("/posts/{postId}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", equalTo(postId))
        ;

    }

    @Test(dependsOnMethods = {"createAPost"},priority = 3)
    public void updateAPost() {

        String sendBody= "MySQL i sakın unutma!";

        response=
                given()
                        .header("Authorization", "Bearer 636144d160083b1ed3acb97f4192dc601314b4d4ebd93a270c328bd3b61cebdf")
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                " \"body\": \""+sendBody+"\"\n" +
                                "    }")
                        .pathParam("postId",postId)
                        .when()
                        .log().uri()
                        .put("/posts/{postId}")
                        .then()
                        .log().body()
                        .extract().response()
        ;

        String returnBody= response.jsonPath().getString("data.body");

        Assert.assertEquals(sendBody,returnBody);
    }

    @Test(dependsOnMethods = {"createAPost"},priority = 4)
    public void deleteAPost() {

        given()
                .header("Authorization", "Bearer 636144d160083b1ed3acb97f4192dc601314b4d4ebd93a270c328bd3b61cebdf")
                .pathParam("postId",postId)
                .when()
                .delete("/posts/{postId}")
                .then()
                .statusCode(204)
        ;
    }

    @Test(priority = 5)
    public void deleteAPostNegative() {

        given()
                .header("Authorization", "Bearer 636144d160083b1ed3acb97f4192dc601314b4d4ebd93a270c328bd3b61cebdf")
                .pathParam("postId",postId)
                .when()
                .delete("/posts/{postId}")
                .then()
                .statusCode(404)
        ;
    }



}
