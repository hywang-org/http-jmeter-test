package com.wan;

import java.util.Random;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wan on 19/5/21.
 */
public class Second implements JavaSamplerClient {

    private static final String[] apps = {"109000", "109001", "109002", "109003", "109004", "109005", "109006",
            "109007", "109008", "109009",};
    private static final String[] mobiles = {"19956596670", "19956596671", "19956596672", "19956596673", "19956596674",
            "19956596675", "19956596676", "19956596677", "19956596678", "19956596679",};

    // URLNAME 就是在图形化界面当中显示的变量名称
    private static final String ACCOUNT = "account";

    private static final String PSWD = "pswd";

    private static final String MOBILE = "mobile";

    private static final String MSG = "msg";

    private static final String NEEDSTATUS = "needstatus";

    // 设置界面当中默认显示的变量的值
    /**
     * 这个方法决定了在jmeter当中要显示哪些属性
     * 
     * @return arguments
     */

    // 用来存储响应的数据，目的是将响应结果放到察看结果树当中
    private String resultData;

    public Arguments getDefaultParameters() {
        System.out.println("getDefaultParameters run");
        Arguments arguments = new Arguments();
        arguments.addArgument(PSWD, "123456");
        arguments.addArgument(MSG, "【云树科技】测试短信");
        arguments.addArgument(NEEDSTATUS, "true");
        return arguments;
    }

    /**
     * 这个方法就是一个初始化方法，我们所有的初始化的动作都可以在这里写
     * 
     * @param javaSamplerContext
     */
    public void setupTest(JavaSamplerContext javaSamplerContext) {
    }

    /**
     * 这个方法就是实现你具体功能逻辑的方法
     * 
     * @param javaSamplerContext
     * @return
     */
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        System.out.println("runTest run");
        String pswd = javaSamplerContext.getParameter(PSWD);
        String msg = javaSamplerContext.getParameter(MSG);
        String needStatus = javaSamplerContext.getParameter(NEEDSTATUS);
        String url = "http://127.0.0.1:9089/message/sendHttpMsg";
        JSONObject json = new JSONObject();
        json.put(ACCOUNT, apps[new Random().nextInt(10)]);
        json.put(PSWD, pswd);
        json.put(MOBILE, mobiles[new Random().nextInt(10)]);
        json.put(MSG, msg);
        json.put(NEEDSTATUS, needStatus);
        // resultData = "这就是响应结果";
        resultData = HttpUtil.sendJsonPost(url, json);
        System.out.println(resultData);
        result.setSampleLabel("自定义java请求访问");
        result.setSuccessful(true);// 告诉查看结果树访问是否成功
        result.setResponseData(resultData, null);
        result.setDataType(SampleResult.TEXT);
        return result;
    }

    /**
     * 这个方法就是来做一些收尾的工作的。
     * 
     * @param javaSamplerContext
     */
    public void teardownTest(JavaSamplerContext javaSamplerContext) {
        System.out.println("teardownTest run");
    }

    public static void main(String[] args) {
        Second request = new Second();
        Arguments arguments = new Arguments();
        arguments.addArgument(PSWD, "123456");
        arguments.addArgument(MSG, "【云树科技】测试短信");
        arguments.addArgument(NEEDSTATUS, "true");
        JavaSamplerContext javaSamplerContext = new JavaSamplerContext(arguments);
        request.runTest(javaSamplerContext);
    }

}
