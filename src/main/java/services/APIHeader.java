package services;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class APIHeader {

    public static RequestSpecification setHeaders(){
        return RestAssured.given()
                .header("Content-type","Application/json")
                .header("Accept","Application/json");
    }

    public static RequestSpecification setHeaders(Map<String, String> headers){
        return setHeaders()
                .headers(headers);
    }
}
