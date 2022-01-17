package com.paul623.android.speakbyai.Model;

import com.paul623.android.speakbyai.Utils.DateHelper;

import org.litepal.crud.LitePalSupport;

public class PlayListModel extends LitePalSupport {
    String content;
    String dateString;
    private static String dateRule="yyyy年MM月dd日 HH时mm分ss秒";
    public PlayListModel(String content) {
        this.content = content;
        dateString= DateHelper.getDateStringByRule(dateRule);
    }
    public PlayListModel(){
        dateString= DateHelper.getDateStringByRule(dateRule);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
