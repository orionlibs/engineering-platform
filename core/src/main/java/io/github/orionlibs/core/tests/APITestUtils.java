package io.github.orionlibs.core.tests;

import static io.restassured.RestAssured.given;

import io.github.orionlibs.core.Logger;
import io.github.orionlibs.core.json.JSONService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class APITestUtils implements JWTBuilderForTests
{
    @Value("${jwt.secret}")
    private String base64Secret;


    public Response makeGetAPICall(HttpHeaders headers)
    {
        Logger.info("[JUnit] making GET call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();
    }


    public Response makeGetAPICall(HttpHeaders headers, String subject, String commaSeparatedAuthorities)
    {
        Logger.info("[JUnit] making GET call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .auth().oauth2(jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")))
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .get()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePostAPICall(Object objectToSave, HttpHeaders headers)
    {
        Logger.info("[JUnit] making POST call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePostAPICall(Object objectToSave, HttpHeaders headers, String subject, String commaSeparatedAuthorities)
    {
        Logger.info("[JUnit] making POST call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .auth().oauth2(jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")))
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .post()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePutAPICall(Object objectToSave, HttpHeaders headers)
    {
        Logger.info("[JUnit] making PUT call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .put()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePutAPICall(Object objectToSave, HttpHeaders headers, String subject, String commaSeparatedAuthorities)
    {
        Logger.info("[JUnit] making PUT call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .auth().oauth2(jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")))
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .put()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePatchAPICall(Object objectToSave, HttpHeaders headers)
    {
        Logger.info("[JUnit] making PATCH call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .patch()
                        .then()
                        .extract()
                        .response();
    }


    public Response makePatchAPICall(Object objectToSave, HttpHeaders headers, String subject, String commaSeparatedAuthorities)
    {
        Logger.info("[JUnit] making PATCH call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .auth().oauth2(jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")))
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .body(JSONService.convertObjectToJSON(objectToSave))
                        .when()
                        .patch()
                        .then()
                        .extract()
                        .response();
    }


    public Response makeDeleteAPICall(HttpHeaders headers)
    {
        Logger.info("[JUnit] making DELETE call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .delete()
                        .then()
                        .extract()
                        .response();
    }


    public Response makeDeleteAPICall(HttpHeaders headers, String subject, String commaSeparatedAuthorities)
    {
        Logger.info("[JUnit] making DELETE call");
        RestAssured.defaultParser = Parser.JSON;
        headers = getHttpHeaders(headers);
        return given()
                        .auth().oauth2(jwtWithAuthorities(base64Secret, subject, commaSeparatedAuthorities.split(",")))
                        .contentType(ContentType.JSON)
                        .headers(headers)
                        .accept(ContentType.JSON)
                        .when()
                        .delete()
                        .then()
                        .extract()
                        .response();
    }


    private HttpHeaders getHttpHeaders(HttpHeaders headers)
    {
        if(headers == null)
        {
            headers = new HttpHeaders();
        }
        return headers;
    }
}
