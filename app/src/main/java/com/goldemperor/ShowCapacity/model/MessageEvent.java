package com.goldemperor.ShowCapacity.model;

/**
 * File Name : MessageEvent
 * Created by : PanZX on  2018/10/18 08:42
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class MessageEvent {
    private String type;
    private String message;

    public MessageEvent(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
