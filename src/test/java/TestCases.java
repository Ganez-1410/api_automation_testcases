import Base.BaseClass;
import Utils.RetryAnalyzer;
import Utils.RetryListener;
import constants.DomainURLs;
import constants.Endpoints;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import services.APIServices;

import java.util.ArrayList;
import java.util.List;

@Listeners(RetryListener.class)
public class TestCases extends BaseClass {

    public static final int block_height = 680000;
    public static final int expectedTransactionsCount = 2875;
    public static final String txid_1 = "96d92f03000f625a38bf8cb91c01188a02b7972238cc6c4e0c6f334cf755004d";
    public static final String txid_2 = "6dd68336c085d5b7b694e2bf6f6c11bca589aea07b6f1c0232bd627c3d217074";

    public static String block_hash;

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void scenario1_assertTransactions() throws JsonPathException {

        String get_blockHash_endpoint = Endpoints.BLOCK_HASH + block_height;
        Response get_blockHash_response = APIServices.get(get_blockHash_endpoint);

        block_hash = get_blockHash_response.getBody().asString();
        System.out.println("Block hash = " + block_hash);

        String get_blockInformation_endpoint = Endpoints.BLOCK_INFORMATION + block_hash;
        Response get_blockInformation_response = APIServices.get(get_blockInformation_endpoint);

        int actualTransactionsCount = get_blockInformation_response.jsonPath().get("tx_count");
        System.out.println("Actual Transactions count: "+actualTransactionsCount);
        Assert.assertEquals(actualTransactionsCount, expectedTransactionsCount, "Transactions count is not as expected");

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void scenario2_validateTxid() throws JsonPathException{

        List<List<String>> all_txids = new ArrayList<>();

        for(int index=0;index < (expectedTransactionsCount-25)+1; index += 25){
            String get_txid_endpoint = Endpoints.BLOCK_INFORMATION + block_hash + "/txs/"+index;

            Response res = APIServices.get(get_txid_endpoint);
            List<String> txid = res.jsonPath().getList("txid");
            all_txids.add(txid);
        }

        System.out.println("Total txids: "+(all_txids.size()*25));
        Assert.assertEquals(all_txids.size()*25,expectedTransactionsCount,"Not extracted all the txids");

        boolean txid1_flag = false;
        boolean txid2_flag = false;

        for(List<String> txid_list: all_txids){
            for(String txid: txid_list){
                if(txid.equals(txid_1)){
                    txid1_flag = true;
                    System.out.println("Tx id 1 is found");
                }
                else if(txid.equals(txid_2)){
                    txid2_flag = true;
                    System.out.println("Tx id 2 is found");
                }
            }
        }

        if(txid1_flag == true && txid2_flag == true){
            Assert.assertTrue(txid1_flag,txid_1+" not found");
            Assert.assertTrue(txid2_flag,txid_2+" not found");
        }
        else{
            Assert.fail("Expected txids are not found");
        }
    }
}
