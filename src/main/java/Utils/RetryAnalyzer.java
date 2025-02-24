package Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static int retryCount = 0;
    private static final int maxRetry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if(retryCount < maxRetry){
            retryCount++;
            return true;
        }
        return false;
    }
}
