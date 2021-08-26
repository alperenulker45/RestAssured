
import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

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
                .body("places.state", hasItem("California")) 
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

    @Test
    public void pathParameterTest() {

        String country = "us";
        String zipCode = "90210";

        given()
                .pathParam("country", country)
                .pathParam("zipCode", zipCode)
                .log().uri() //request linki

                .when()
                .get("http://api.zippopotam.us/{country}/{zipCode}")


                .then()
                .log().body()
                .body("places", hasSize(1))

                ;
    }

    @Test
    public void pathParameterTest2() {

        String country = "us";


        for (int i = 90210; i <90220 ; i++) {
            String zipCode = String.valueOf(i);

            given()
                    .pathParam("country", country)
                    .pathParam("zipCode", zipCode)
                    .log().uri() //request linki

                    .when()
                    .get("http://api.zippopotam.us/{country}/{zipCode}")


                    .then()
                    .log().body()
                    .body("places", hasSize(1))

            ;
        }
    }


    private ResponseSpecification responseSpecification;
    private RequestSpecification requestSpecification;

    @BeforeClass
    public void setup() {

        baseURI = "http://api.zippopotam.us";

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setAccept(ContentType.JSON)
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                //.expectBody("places", hasSize(1))
                //.expectResponseTime(lessThan(10000L))
                .log(LogDetail.BODY)
                .build();
    }

    @Test
    public void bodyArrayHasSizeTest_baseUriTest() {
        given()

                .log().uri()
                .when()
                .get("/us/90210")// url nin basinda http yoksa baseUrideki deger otomatik gelir

                .then()
                .body("places", hasSize(1)) // verilen pathteki listin size konntrolu
                .log().body()
                .statusCode(200)

        ;
    }

    @Test
    public void bodyArrayHasSizeTest_RequestSpecification() {
        given()

                .spec(requestSpecification)
                .when()
                .get("/us/90210")// url nin basinda http yoksa baseUrideki deger otomatik gelir

                .then()
                .body("places", hasSize(1)) // verilen pathteki listin size konntrolu
                .spec(responseSpecification)

        ;

    }

    @Test
    public void bodyArrayHasSizeTest_ResponseSpecification() {
        given()

                .log().uri()
                .when()
                .get("/us/90210")// url nin basinda http yoksa baseUrideki deger otomatik gelir

                .then()
                .body("places", hasSize(1)) // verilen pathteki listin size konntrolu
                .spec(responseSpecification)

        ;

    }

    //Json extract
    @Test
    public void extractingJsonPath() {

        String place_name =

                given()

                        //.spec(requestSpecification)
                        .when()
                        .get("/us/90210")
                        .then()
                        //.spec(responseSpecification)
                        .extract().path("places[0].'place name'") // extract ile baslayan satir disariya deger dondurur


                ;


        System.out.println("place name = " + place_name);


    }

    @Test
    public void extractingJsonPathInt() {

        int limit =

                given()

                        .param("page", 1)
                        .log().uri()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        //.spec(responseSpecification)
                        .extract().path("meta.pagination.limit") // extract ile baslayan satir disariya deger dondurur


                ;


        System.out.println("place name = " + limit);


    }

    @Test
    public void extractingJsonPathIntList() {

        List<Integer> ids =

                given()

                        .param("page", 1)
                        .log().uri()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()

                        .extract().path("data.id") // extract ile baslayan satir disariya deger dondurur


                ;

        System.out.println("ids = " + ids);

    }

    @Test
    public void extractingJsonPathStringList() {

        List<String> emails =

                given()

                        .param("page", 1)
                        .log().uri()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()

                        .extract().path("data.email") // extract ile baslayan satir disariya deger dondurur


                ;

        System.out.println("ids = " + emails);
        Assert.assertTrue(emails.contains("ava@mm.am"));

    }
    
    @Test
    public void extractingJsonPOJO(){
        
        Location location =
        given()
                
                .when()
                .get("/us/90210")
                
                .then()
                .extract().as(Location.class)
                
                ;

        System.out.println("Location = " + location);
        System.out.println("location.getCountry() = " + location.getCountry());
        System.out.println("location.getPlaces() = " + location.getPlaces());
        System.out.println("location.getPlaces().get(0).getPlacename() = " + location.getPlaces().get(0).getPlacename());
                
    }
    
}
