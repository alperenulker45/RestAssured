
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

}
