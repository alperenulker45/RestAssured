import POJO.Todo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

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

    /** Task 3
     *  create a request to https://jsonplaceholder.typicode.com/todos/2
     *  expect status 200
     *  expect content type JSON
     *  expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void task3() {


        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))

                ;

    }

    /** Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     *  expect status 200
     *  expect content type JSON
     *  expect response completed status to be false
     */

    @Test
    public void task4() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false));


    }

    /** Task 5
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * expect content type JSON
     * expect third item have:
     *      title = "fugiat veniam minus"
     *      userId = 1
     */

    @Test
    public void task5() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().body()
                .body("title[2]", equalTo("fugiat veniam minus"))
                .body("userId[2]", equalTo(1))

                ;



    }

    /** Task 6
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * Converting Into POJO
     */

    @Test
    public void task6() {

        Todo todo =

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .extract().as(Todo.class)

                ;


        System.out.println("todo = " + todo);
        System.out.println("todo.getTitle() = " + todo.getTitle());
        System.out.println("todo.getId() = " + todo.getId());
        System.out.println("todo.getClass() = " + todo.getUserId());



    }


    /** Task 7
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * Converting Array Into Array of POJOs
     */

    @Test
    public void task7() {

        Todo[] todos=


        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .log().status()
                .statusCode(200)
                .extract().as(Todo[].class)

                ;

        System.out.println("Arrays.toString(todos) = " + Arrays.toString(todos));


    }

    /** Task 8
     * create a request to https://jsonplaceholder.typicode.com/todos
     * expect status 200
     * Converting Array Into List of POJOs
     */

    @Test
    public void task8() {

        Todo[] todos=


                given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos")

                        .then()
                        .log().status()
                        .statusCode(200)
                        .extract().as(Todo[].class)

                ;
        
        List<Todo> todoList = Arrays.asList(todos);

        System.out.println("todoList = " + todoList);
                


    }


}


