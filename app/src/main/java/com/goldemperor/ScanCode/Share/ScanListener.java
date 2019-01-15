package com.goldemperor.ScanCode.Share;

import com.goldemperor.MainActivity.CodeDataModel;

import java.util.List;

/**
 * File Name : ScanListener
 * Created by : PanZX on  2018/9/29 09:50
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public interface ScanListener {
    void CodeResult(String code);
    void GetBarCodeStatusResult(boolean ReturnType,Object ReturnMsg);
    void GetReport(boolean ReturnType,Object ReturnMsg);
    void DeleteItem(int position);
    void AddItem(List<CodeDataModel> Cdm , String Code);
    void ClearItem();
}
