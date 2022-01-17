package com.paul623.android.speakbyai.Utils;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;

public class KqwSpeechSynthesizer {
    // Log标签
    private static final String TAG = "KqwSpeechSynthesizer";

    private Context mContext;
    int scode;

    // 语音合成对象
    private SpeechSynthesizer mTts;

    public KqwSpeechSynthesizer(Context context) {
        mContext = context;
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(mContext, new InitListener() {
            @Override
            public void onInit(int i) {
                scode=i;
            }
        });
        //Toast.makeText(mContext,"出错代码:"+scode,Toast.LENGTH_LONG).show();
    }

    /**
     * 开始语音合成
     */
    public void start(String text) {
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            Toast.makeText(mContext, "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 结束语音合成
     */
    public void stop() {
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            Log.i(TAG, "开始合成");
        }

        @Override
        public void onSpeakPaused() {
            Log.i(TAG, "暂停合成");
        }

        @Override
        public void onSpeakResumed() {
            Log.i(TAG, "继续合成");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            Log.i(TAG, "传冲进度 ：" + percent);
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
//            Log.i(TAG, "合成进度 ： " + percent);
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                Log.i(TAG, "合成完成");
            } else {
                Log.i(TAG, "error : " + speechError.toString());
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

    };

    /**
     * 参数设置
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 设置使用本地引擎
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
        // 设置发音人资源路径
        mTts.setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath());
        // 设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaofeng");

        // 设置语速
        mTts.setParameter(SpeechConstant.SPEED, "50");

        // 设置音调
        mTts.setParameter(SpeechConstant.PITCH, "50");

        // 设置音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");

        // 设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
    }

    // 获取发音人资源路径
    private String getResourcePath() {
        // 合成通用资源
        // 发音人资源
        return ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/common.jet") + ";" +
                ResourceUtil.generateResourcePath(mContext, ResourceUtil.RESOURCE_TYPE.assets, "tts/xiaofeng.jet");
    }

}
