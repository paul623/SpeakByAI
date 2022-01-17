package com.paul623.android.speakbyai;

import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.stacklabelview.StackLabel;
import com.kongzue.stacklabelview.interfaces.OnLabelClickListener;
import com.paul623.android.speakbyai.Model.PlayListModel;
import com.paul623.android.speakbyai.Utils.KqwSpeechSynthesizer;

@Layout(R.layout.activity_ttsactivity)
@DarkStatusBarTheme(value = true)
public class TTSActivity extends BaseActivity {

    @BindView(R.id.sl_ttl)
    StackLabel stackLabel;
    @BindView(R.id.btn_tts_speak)
    Button btn_speak;
    @BindView(R.id.btn_tts_clear)
    Button btn_clear;
    @BindView(R.id.et_tts_input)
    EditText et_input;
    @BindView(R.id.tb_tts_head)
    Toolbar toolbar;
    KqwSpeechSynthesizer kqwSpeechSynthesizer;
    String content="";
    String []labels={"xxx","快来吃饭了","今天要开会","我在食堂吃了","我还有事，先走了","别调皮了,快点睡觉","我去学校了","小区东门"};

    @Override
    public void initViews() {
        kqwSpeechSynthesizer=new KqwSpeechSynthesizer(me);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void initDatas(JumpParameter parameter) {
        stackLabel.setLabels(labels);
    }

    @Override
    public void setEvents() {
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content=et_input.getText().toString();
                if("".equals(content)){
                    Toast.makeText(me, "输入内容为空哦~", Toast.LENGTH_SHORT).show();
                }else {
                    kqwSpeechSynthesizer.start(content);
                    PlayListModel playListModel=new PlayListModel(content);
                    playListModel.save();
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tts_item_delete:
                        //删除
                        et_input.getText().clear();
                        content="";
                        break;
                    case R.id.tts_item_history:
                        //历史
                        jump(HistoryListViewActivity.class);
                        break;
                    case R.id.tts_item_about:
                        jump(AboutActivity.class);
                        break;
                }
                return false;
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_input.getText().clear();
                content="";
            }
        });
        stackLabel.setOnLabelClickListener(new OnLabelClickListener() {
            @Override
            public void onClick(int index, View v, String s) {
                content=et_input.getText().toString();
                content=content+labels[index];
                et_input.setText(content);
            }
        });
    }


    @Override
    protected void onDestroy() {
        kqwSpeechSynthesizer.stop();
        super.onDestroy();
    }
}