package com.paul623.android.speakbyai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.paul623.android.speakbyai.Model.PlayListModel;
import com.paul623.android.speakbyai.R;
import com.paul623.android.speakbyai.Utils.KqwSpeechSynthesizer;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class HistoryListViewAdapter extends BaseAdapter {
    DataSetChangedListener dataSetChangedListener;
    public interface DataSetChangedListener{
        void notifyDataSetHasChanged();
    }
    List<PlayListModel> playListModels;
    Context context;
    public HistoryListViewAdapter(Context context,DataSetChangedListener dataSetChangedListener){
        this.context=context;
        this.dataSetChangedListener=dataSetChangedListener;
        playListModels=LitePal.order("dateString desc").find(PlayListModel.class);
        if(playListModels==null){
            playListModels=new ArrayList<>();
        }
    }
    public void refresh(){
        playListModels= LitePal.order("dateString desc").find(PlayListModel.class);;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return playListModels.size();
    }

    @Override
    public Object getItem(int i) {
        return playListModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.history_list_view_item,null);
        TextView content,date;
        ImageView img_delete,img_play;
        content=view.findViewById(R.id.tv_history_list_view_content);
        date=view.findViewById(R.id.tv_history_list_view_time);
        content.setText(playListModels.get(i).getContent());
        date.setText(playListModels.get(i).getDateString());
        img_delete=view.findViewById(R.id.img_history_list_view_delete);
        img_play=view.findViewById(R.id.img_history_list_view_play);
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                playListModels.get(i).delete();
                refresh();
                dataSetChangedListener.notifyDataSetHasChanged();
            }
        });
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KqwSpeechSynthesizer(context).start(playListModels.get(i).getContent());
            }
        });
        return view;
    }
}
