package Base;

import constants.DomainURLs;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class BaseClass {

    @BeforeMethod(alwaysRun = true)
    public void setBaseURI(){
        RestAssured.baseURI = DomainURLs.BLOCKSTREAM;
        RestAssured.useRelaxedHTTPSValidation();
    }
}
