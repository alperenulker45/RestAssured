
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {

        given()
                //Hazirlik islemlerini yapacagiz

                .when()
                //link ve aksiyon islemleri

                .then()
                //test ve extract islemleri
        ;

    }

    @Test
    public void statusCodeTest() {

        given()


                .when()
                .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .log().body() // log.all() --> tum bilgileri yazar. body sadece body icindeki

                        .statusCode(200) // status kontrolu

        ;

    }

    @Test
    public void contentTypeTest() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .contentType(ContentType.JSON) //icerik tipi JSON formatinda mi geliyor.

                ;


    }

    @Test
    public void logTest() {

        given()

                .log().all()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()

                ;
    }

    @Test
    public void checkStateInResponseBody() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("country", equalTo("United States")) //body.country nin verilen degere esitligini kontrol
                .statusCode(200)

        ;

    }

    @Test
    public void checkStateResponseBody() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places[0].state", equalTo("California")) //body.country nin verilen degere esitligini kontrol
                .statusCode(200)

        ;

    }

    @Test
    public void bodyJsonPathHasItem() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places.state", hasItem("California")) //butun statelerde aranan eleman var mi diye kontrol
                .statusCode(200)

        ;

    }


    @Test
    public void bodyJsonPathTest3() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
        ;

    }

    @Test
    public void checkbodyArrayHasSize() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1)) //verilen pathdeki listin size kontrolu
                .statusCode(200)

                ;


    }

    @Test
    public void combiningTest() {

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .statusCode(200)
                ;
    }

}
