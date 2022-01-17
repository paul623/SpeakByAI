package com.paul623.android.speakbyai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.allen.library.SuperTextView;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.paul623.android.speakbyai.Adapter.HistoryListViewAdapter;

import org.litepal.LitePal;

@DarkStatusBarTheme(value = true)
@Layout(R.layout.activity_history_list_view)
public class HistoryListViewActivity extends BaseActivity {

    @BindView(R.id.stv_history_toolbar)
    SuperTextView stv_toolbar;
    @BindView(R.id.lv_history_list)
    ListView lv_playlist;
    @BindView(R.id.img_history_bg)
    ImageView img_bg;
    HistoryListViewAdapter historyListViewAdapter;
    @Override
    public void initViews() {

    }

    @Override
    public void initDatas(JumpParameter parameter) {
        historyListViewAdapter=new HistoryListViewAdapter(me,new HistoryListViewAdapter.DataSetChangedListener() {
            @Override
            public void notifyDataSetHasChanged() {
                setListView();
            }
        });
        lv_playlist.setAdapter(historyListViewAdapter);
        setListView();
    }

    @Override
    public void setEvents() {
        stv_toolbar.setLeftImageViewClickListener(new SuperTextView.OnLeftImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                    HistoryListViewActivity.this.finish();
            }
        });
    }
    private void setListView(){
        if(historyListViewAdapter.getCount()==0){
            lv_playlist.setVisibility(View.GONE);
            img_bg.setVisibility(View.VISIBLE);
        }else {
            lv_playlist.setVisibility(View.VISIBLE);
            img_bg.setVisibility(View.GONE);
        }
    }
}