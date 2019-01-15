package com.goldemperor.ScanCode.Show;

import java.util.List;

/**
 * File Name : ReportModel
 * Created by : PanZX on  2018/12/28 08:42
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class ReportModel {
    int RowIndex;
    String BackgroundColor;
    List<list> DataList;

    public int getRowIndex() {
        return RowIndex;
    }

    public void setRowIndex(int rowIndex) {
        RowIndex = rowIndex;
    }

    public String getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public List<list> getDataList() {
        return DataList;
    }

    public void setDataList(List<list> dataList) {
        DataList = dataList;
    }

    class list {
        String FFieldName;
        String FContent;
        int FWidth;
        boolean FVisible;
        int FColIndex;
        String FCaption;
        String  FBackgroundColor;
        String  FForeColor;

        public String getFFieldName() {
            return FFieldName;
        }

        public void setFFieldName(String FFieldName) {
            this.FFieldName = FFieldName;
        }

        public String getFContent() {
            return FContent;
        }

        public void setFContent(String FContent) {
            this.FContent = FContent;
        }

        public int getFWidth() {
            return FWidth;
        }

        public void setFWidth(int FWidth) {
            this.FWidth = FWidth;
        }

        public boolean isFVisible() {
            return FVisible;
        }

        public void setFVisible(boolean FVisible) {
            this.FVisible = FVisible;
        }

        public int getFColIndex() {
            return FColIndex;
        }

        public void setFColIndex(int FColIndex) {
            this.FColIndex = FColIndex;
        }

        public String getFCaption() {
            return FCaption;
        }

        public void setFCaption(String FCaption) {
            this.FCaption = FCaption;
        }

        public String getFBackgroundColor() {
            return FBackgroundColor;
        }

        public void setFBackgroundColor(String FBackgroundColor) {
            this.FBackgroundColor = FBackgroundColor;
        }

        public String getFForeColor() {
            return FForeColor;
        }

        public void setFForeColor(String FForeColor) {
            this.FForeColor = FForeColor;
        }
    }
}
