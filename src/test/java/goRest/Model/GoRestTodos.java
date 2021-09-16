package goRest.Model;
import static io.restassured.RestAssured.*;
import goRest.Model.POJO.ToDosData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class GoRestTodos {

    @Test
    public void getTodos() {

        Response response =
        
        given()

                .when()
                .get("https://gorest.co.in/public/v1/todos")
                .then()
                .statusCode(200)
                //.log().body()
                .extract().response()

                ;
                
        List<ToDosData> toDosDataList = response.jsonPath().getList("data", ToDosData.class);
        System.out.println("toDosDataList = " + toDosDataList);

        int biggestID=0;
        for(int i=0; i<toDosDataList.size(); i++) {
                if (toDosDataList.get(i).getId() > biggestID)
                {
                    biggestID = toDosDataList.get(i).getId();
             
                }
            }
        System.out.println("biggestID = " + biggestID);
        System.out.println("toDosDataList.size() = " + toDosDataList.size());

    }






}
