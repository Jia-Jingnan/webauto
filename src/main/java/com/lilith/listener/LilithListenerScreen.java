package com.lilith.listener;

import com.google.common.io.Files;
import com.lilith.cases.BaseCase;
import com.lilith.util.PropertiesUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:JiaJingnan
 * @Date: 上午10:36 2021/5/25
 */
public class LilithListenerScreen extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        // System.out.println("第一个case失败执行的");
        // 从实例中获取driver

        BaseCase baseCase = (BaseCase) iTestResult.getInstance();
        WebDriver webDriver = baseCase.driver;

        // 获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH时mm分ss秒");
        String currentTime = simpleDateFormat.format(new Date());
        // 获取当前类名
        String currentClassName = this.getClass().getName();
        // 获取当前testng中test标签的name
        String testName = iTestResult.getTestContext().getCurrentXmlTest().getName();
        //图片路径及名称
        String pngPath = testName + "-" +currentTime  + ".png";

        // 路径
        String currentPath = System.getProperty("user.dir") + PropertiesUtil.getProperty("screen.folder.name");

        this.takeScreenShot(webDriver,currentPath,pngPath);
        super.onTestFailure(iTestResult);
    }

    public void takeScreenShot(WebDriver webDriver, String currentPath, String pngPath) {

        File screenshotAs = ((RemoteWebDriver) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshotAs, new File(currentPath + "/" + pngPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}