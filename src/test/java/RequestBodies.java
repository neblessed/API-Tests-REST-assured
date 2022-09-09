//Тела для запросов
public class RequestBodies {
    public static String requestCreateNewUser = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"\n" +
            "}";

    public static String requestRegisterSuccsess = "{\n" +
            "    \"email\": \"eve.holt@reqres.in\",\n" +
            "    \"password\": \"pistol\"\n" +
            "}";

    public static String requestRegisterUnsuccsess = "{\n" +
            "    \"email\": \"sydney@fife\"\n" +
            "}";

    public static String updateUser = "{\n" +
            "    \"name\": \"morpheus\",\n" +
            "    \"job\": \"zion resident\"\n" +
            "}";
}
