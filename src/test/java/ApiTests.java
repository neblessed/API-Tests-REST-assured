import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.List;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ApiTests extends RequestBodies {

    @Test @DisplayName("1. Проверка статус-кода [GET]")
    public void okStatus(){
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .assertThat().statusCode(200);
    }

    @Test @DisplayName("2.Создание нового пользователя [POST]")
    public void createNewUser(){
        Response response = given()
                .when().contentType(ContentType.JSON)
                .body(requestCreateNewUser)
                .post("https://reqres.in/api/users")
                .then().extract().response();

        Assertions.assertEquals("morpheus",response.jsonPath().getString("name"));
        Assertions.assertEquals("leader",response.jsonPath().getString("job"));
    }

    @Test @DisplayName("3.Успешная регистрация [POST]")
    public void registerSuccsessful(){
        Response response = given().when().contentType(ContentType.JSON)
                .body(requestRegisterSuccsess)
                .post("https://reqres.in/api/register")
                .then().extract().response();
        String AuthToken = response.jsonPath().getString("token");

        Assertions.assertEquals(200,response.statusCode());
    }

    @Test @DisplayName("4. Регистрация без пароля (запрет) [POST]")
    public void registerUnsuccsessful(){
        Response response = given().when().contentType(ContentType.JSON)
                .body(requestRegisterUnsuccsess)
                .post("https://reqres.in/api/register")
                .then().extract().response();
        Assertions.assertEquals("Missing password",response.jsonPath().getString("error"));
        Assertions.assertEquals(400,response.statusCode());
    }

    @Test @DisplayName("5. Получение пользователя из списка по e-mail [GET]")
    public void getNewUser(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("https://reqres.in/api/users?page=2")
                .then().extract().response();
        Assertions.assertEquals("byron.fields@reqres.in", response.jsonPath().getString("data.email[3]"));
    }

    @Test @DisplayName("6. E-mail заканчивается на @reqres.in [GET]")
    public void checkEmail(){
        List<UsersPojo> users = given()
                .when()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().body().jsonPath().getList("data",UsersPojo.class);

        Assertions.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));
    }

    @Test @DisplayName("7. Изменение пользователя [PATCH]")
    public void updateUser(){
        Response response = given().when().contentType(ContentType.JSON)
                .body(updateUser)
                .patch("https://reqres.in/api/users/2")
                .then().extract().response();
        Assertions.assertEquals("morpheus",response.jsonPath().getString("name"));
        Assertions.assertEquals("zion resident",response.jsonPath().getString("job"));
    }
}
