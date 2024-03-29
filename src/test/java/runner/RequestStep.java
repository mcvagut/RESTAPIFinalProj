package runner;

import FactoryRequest.FactoryRequest;
import FactoryRequest.RequestInfo;
import config.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

    public class RequestStep {
        Response response;
        RequestInfo requestInfo = new RequestInfo();

        Map<String, String> variables = new HashMap<>();
        @Given("using token in localhost")
        public void usingTokenInTodoLy() {
            // Construir la URL usando el puerto proporcionado en el escenario
            requestInfo.setUrl("http://localhost:8800/api/users/loginAdmin")
                    .setContentType("application/json");  // Añadir el tipo de contenido si es necesario

            // Construir el cuerpo de la solicitud para iniciar sesión (podría ser vacío en algunos casos)
            String requestBody = "{\n" +
                    "    \"usuario\": \"moises\",\n" +
                    "    \"password\": \"moises\"\n" +
                    "}";

            // Configurar la solicitud para el inicio de sesión
            requestInfo.setBody(requestBody);

            // Enviar la solicitud y obtener la respuesta
            response = FactoryRequest.make("post").send(requestInfo);

            // Extraer el token de la respuesta
            String token = response.then().extract().path("token");

            requestInfo.setHeader("Authorization", "Bearer " + token);
        }

        @Given("using new token in localhost")
        public void usingNewTokenInTodoLy() {
            // Construir la URL usando el puerto proporcionado en el escenario
            requestInfo.setUrl("http://localhost:8800/api/users/loginAdmin")
                    .setContentType("application/json");  // Añadir el tipo de contenido si es necesario

            // Construir el cuerpo de la solicitud para iniciar sesión (podría ser vacío en algunos casos)
            String requestBody = "{\n" +
                    "    \"usuario\": \"diplomado\",\n" +
                    "    \"password\": \"diplomado\"\n" +
                    "}";

            // Configurar la solicitud para el inicio de sesión
            requestInfo.setBody(requestBody);

            // Enviar la solicitud y obtener la respuesta
            response = FactoryRequest.make("post").send(requestInfo);

            // Extraer el token de la respuesta
            String token = response.then().extract().path("token");

            requestInfo.setHeader("Authorization", "Bearer " + token);
        }


//        @Given("using token in todo.ly")
//        public void usingTokenInTodoLy() {
//            String credentials = Base64.getEncoder().encodeToString((Configuration.user+":"+Configuration.password).getBytes());
//
//            //request
//            requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
//                    .setHeader("Authorization", "Basic "+credentials);
//            response = FactoryRequest.make("get").send(requestInfo);
//
//            //get token
//            String token = response.then().extract().path("TokenString");
//
//            // Update the requestInfo with the new token
//            requestInfo = new RequestInfo();
//            requestInfo.setHeader("Token", token);
//
//        }

//        @Given("using new token in todo.ly")
//        public void usingNewTokenInTodoLy() {
//            String credentials = Base64.getEncoder().encodeToString((Configuration.user2+":"+Configuration.password2).getBytes());
//
//            //request
//            requestInfo.setUrl(Configuration.host+"/api/authentication/token.json")
//                    .setHeader("Authorization", "Basic "+credentials);
//            response = FactoryRequest.make("get").send(requestInfo);
//
//            //get token
//            String token = response.then().extract().path("TokenString");
//
//            // Update the requestInfo with the new token
//            requestInfo = new RequestInfo();
//            requestInfo.setHeader("Token", token);
//
//        }

        @When("send {} request {string} with body")
        public void sendPOSTRequestWithBody(String method, String url, String body) {
            requestInfo.setUrl(Configuration.host+this.replaceVariables(url)).setBody(body);
            response = FactoryRequest.make(method).send(requestInfo);

        }

        @Then("response code should be {int}")
        public void responseCodeShouldBe(int expectedCode) {
            response.then().statusCode(expectedCode);
        }

        @And("the attribute {string} should be equal to {string}")
        public void theAttributeShouldBeEqualTo(String attribute, String expectedValue) {
            response.then().body(attribute,equalTo(expectedValue));
        }

        @And("save {string} as {string} from response")
        public void saveAs(String attribute, String nameVariable) {
            String value = response.then().extract().path(attribute).toString();
            System.out.println("EL VALUE ES"+value);
            variables.put(nameVariable,value);
            System.out.println("lAS VARIABLES SON:"+variables);
        }


        private String replaceVariables(String value){
            for (String key:variables.keySet()){
                value = value.replace(key,variables.get(key));
            }
            return value;
        }

    }

