package com.zxb.secai;

import android.app.Application;

import com.zxb.libnetwork.ApiService;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 咱们的服务器已经部署到公网了.
 * <p>
 * 项目在线Api文档地址：http://123.56.232.18:8080/serverdemo/swagger-ui.html#/
 * <p>
 */
public class JokeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ApiService.init("http://123.56.232.18:8080/serverdemo", null);
//        ApiService.init("http://192.168.11.115:8080/serverdemo", null);
        ApiService.init("http://47.117.124.90:8080/serverdemo", null);

        CrashReport.initCrashReport(getApplicationContext(), "eb455a94a3", true);
    }
}
