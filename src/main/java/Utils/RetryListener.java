package Utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class RetryListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result){
        Throwable throwable = result.getThrowable();
        System.out.println("Test: "+result.getTestName()+" failed because "+((throwable != null)?throwable.getMessage():""));
    }

    @Override
    public void onStart(ITestContext result){
        System.out.println("Test started: "+ result.getName());
    }

    @Override
    public void onFinish(ITestContext result){
        System.out.println("Test started: "+ result.getName());
    }
}
