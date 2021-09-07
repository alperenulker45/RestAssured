package goRest.Model;

import static io.restassured.RestAssured.*;


import goRest.Model.POJO.Comments;
import goRest.Model.POJO.mother;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class GoRestCommentsTest {

    // Task 1: https://gorest.co.in/public/v1/comments  Api sinden dönen verilerdeki data yı bir nesne yardımıyla
    //         List olarak alınız.
    @Test(enabled = false)
    public void getComments() {

        Response response =

        given()

                .when()
                .get("https://gorest.co.in/public/v1/comments")

                .then()
                .log().body()
                .statusCode(200)
                .extract().response()

                ;

        List<Comments> commentsList = response.jsonPath().getList("data", Comments.class);
        System.out.println("commentsList = " + commentsList);

    }

    int userID;
    int commentID;

    @Test
    public void createComment() {

        Comments comments = new Comments();
        comments.setName("Alperen");
        comments.setEmail("alpierro45@gmail.com");
        comments.setBody("testerstesterstesters");

        commentID=
        given()
                .headers("Authorization", "Bearer 2196649af35ed487c77fa862b22eb1b24d24fcf4f26397870044288dd90b35cd")
                .contentType(ContentType.JSON)
                .body(comments)
                .log().body()
                .log().uri()

                .when()
                .post("https://gorest.co.in/public/v1/posts/123/comments")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getInt("data.id")
                ;

        System.out.println("comments = " + commentID);

    }

    @Test (dependsOnMethods = "createComment", priority = 1)
    public void updateComment() {


        given()
                .headers("Authorization", "Bearer 2196649af35ed487c77fa862b22eb1b24d24fcf4f26397870044288dd90b35cd")
                .pathParam("commentID", commentID)
                .contentType(ContentType.JSON)
                .body("{\n" + "\"body\": \"update\" \n"+"}")
                .log().uri()

                .when()
                .put("https://gorest.co.in/public/v1/comments/{commentID}")

                .then()
                .log().body()
                .statusCode(200)
                .body("data.body", equalTo("update"))
                ;

    }

    @Test(dependsOnMethods = {"createComment"}, priority = 2)
    public void deleteAComment() {
        // Task 6 : Create edilen Comment ı siliniz. Status kodu kontorl ediniz 204
        // Delete https://gorest.co.in/public/v1/comments/1394

        given()
                .header("Authorization", "Bearer 2196649af35ed487c77fa862b22eb1b24d24fcf4f26397870044288dd90b35cd")
                .pathParam("commentId", commentID)
                .when()
                .delete("https://gorest.co.in/public/v1/comments/{commentId}")
                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = {"createComment"}, priority = 3)
    public void deleteCommentNegative() {
        // Task 6 : Create edilen Comment ı siliniz. Status kodu kontorl ediniz 204
        // Delete https://gorest.co.in/public/v1/comments/1394

        given()
                .header("Authorization", "Bearer 2196649af35ed487c77fa862b22eb1b24d24fcf4f26397870044288dd90b35cd")
                .pathParam("commentId", commentID)
                .when()
                .delete("https://gorest.co.in/public/v1/comments/{commentId}")
                .then()
                .log().body()
                .statusCode(404)
        ;
    }

    @Test(dependsOnMethods = "createComment")
    public void getCreatedComment() {
        given()

                .pathParam("commentID", commentID)
                .log().uri()


                .when()
                .get("https://gorest.co.in/public/v1/comments/{commentID}")

                .then()
                .log().body()
                .statusCode(200)

                ;


    }

    //  // Bütün Comment lardaki emailleri bir list olarak alınız ve
    //    // içinde "acharya_rajinder@ankunding.biz" olduğunu doğrulayınız.

    @Test(enabled = false)
    public void getCommentEmails() {

        Response response =

                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/comments")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response()

                ;

        List<String> emailList = response.jsonPath().getList("data.email");
        System.out.println("emailList = " + emailList);
        Assert.assertTrue(emailList.contains("acharya_rajinder@ankunding.biz"));
       // for (String s: emailList) {
       //     System.out.println(s);
       // }

    //   List<String> emails = new ArrayList<>();

    //   for (Comments c : commentsList) {
    //       emails.add(c.getEmail());
    //   }

    //   Assert.assertTrue(emails.contains("acharya_rajinder@ankunding.biz"));

    }
    // Task 3 : https://gorest.co.in/public/v1/comments  Api sinden
    // dönen bütün verileri tek bir nesneye dönüştürünüz

    @Test(enabled = false)
    public void getAllInfo() {

            mother all =
                    given()

                            .when()
                            .get("https://gorest.co.in/public/v1/comments")

                            .then()
                            .extract().as(mother.class)

                    ;


        System.out.println("all = " + all);
        System.out.println("all.getMeta().getPagination() = " + all.getMeta().getPagination());
        System.out.println("all.getMeta().getPagination().getLinks() = " + all.getMeta().getPagination().getLinks());

    }


}
