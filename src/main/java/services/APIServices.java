package services;

import constants.DomainURLs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class APIServices {

    public static Response get(String endpoint){
        Response response = null;
        try {
            response = APIHeader.setHeaders()
                    .given().log().all()
                    .get(endpoint)
                    .then().log().all().extract().response();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public static Response get(String endpoint, Map<String, String> params){
        Response response = null;
        try {
            response = APIHeader.setHeaders()
                    .given().log().all()
                    .queryParams(params)
                    .get(endpoint)
                    .then().log().all().extract().response();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public static Response get(String endpoint, Map<String, String> params, Map<String, String> headers){
        Response response = null;
        try {

            RequestSpecification requestSpecification = (headers != null) ? APIHeader.setHeaders(headers) : APIHeader.setHeaders();
            if(params != null)
                requestSpecification.queryParams(params);

            response = requestSpecification
                    .given().log().all()
                    .queryParams(params)
                    .get(endpoint)
                    .then().log().all().extract().response();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }
}
