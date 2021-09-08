package goRest.Model;

import static io.restassured.RestAssured.*;


import goRest.Model.POJO.Comments;
import goRest.Model.POJO.mother;
import goRest.Model.PostPojo.PostData;
import goRest.Model.PostPojo.allData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;
import org.testng.annotations.Test;

public class GoRestPostTests {

    @Test
    public void getPostData() {

        List<PostData> postDataList=
        
        given()

                .when()
                .get("https://gorest.co.in/public/v1/posts")

                .then()
                .extract().jsonPath().getList("data", PostData.class);

        for (PostData p : postDataList) {
            System.out.println("p = " + p);
        }


    }


}
