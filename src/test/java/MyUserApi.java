import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class MyUserApi {
    public MyUserApi() throws IOException {
        readConfigFile();
    }


@Test
    public void doLogin() throws ConfigurationException {
        RestAssured.baseURI="http://dmoney.roadtocareer.net";
        Response res= given().contentType("application/json").body("\n" +
                "{\n" +
                "    \"email\":\"admin@roadtocareer.net\",\n" +
                "    \"password\":\"1234\"\n" +
                "}").post("/user/login");

        System.out.println("res full message: "+res.asString());
        JsonPath jsonPath = res.jsonPath();
        String token = jsonPath.get("token");
        System.out.println(token);
        setEnvVariable("token",token);
    }


@Test
    public void searchUser() throws IOException {

        RestAssured.baseURI="http://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json").header("Authorization", prop.get("token"))
                .when().get("/user/search/id/444");
        System.out.println("searchUser = "+res.asString());
    }

//    public static void main(String[] args) throws IOException {
//        MyUserApi api = new MyUserApi();
//        api.readConfigFile();
//    }

    public void setEnvVariable(String key, String value) throws ConfigurationException {

        PropertiesConfiguration config = new PropertiesConfiguration("./src/test/resources/config.properties");
        config.setProperty(key, value);
        config.save();

    }

    Properties prop;
    FileInputStream fs;

    public void readConfigFile() throws IOException {
        prop= new Properties();
        fs= new FileInputStream("./src/test/resources/config.properties");
        prop.load(fs);
        System.out.println(prop.get("token"));
    }

}
