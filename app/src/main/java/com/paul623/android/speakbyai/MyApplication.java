package com.paul623.android.speakbyai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.litepal.LitePal;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=1d7d266b");
        LitePal.initialize(this);
    }
}