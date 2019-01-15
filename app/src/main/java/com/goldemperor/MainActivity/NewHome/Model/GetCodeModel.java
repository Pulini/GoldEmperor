package com.goldemperor.MainActivity.NewHome.Model;

/**
 * File Name : GetCodeModel
 * Created by : PanZX on  2018/5/17 10:00
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class GetCodeModel {
//    {"StatusCode":200,"Info":"{\"taskId\":\"18051709591617929599\",\"returnCode\":\"200\",\"returnMsg\":\"成功\",\"productId\":\"1\"}","Data":"{\"code\":\"794219\",\"phone\":\"15868587600\"}"}
    int StatusCode;
    info Info;
    data Data;

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public info getInfo() {
        return Info;
    }

    public void setInfo(info info) {
        Info = info;
    }

    public data getData() {
        return Data;
    }

    public void setData(data data) {
        Data = data;
    }

    public static class info{
        String taskId;
        String returnCode;
        String returnMsg;
        String productId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getReturnMsg() {
            return returnMsg;
        }

        public void setReturnMsg(String returnMsg) {
            this.returnMsg = returnMsg;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
    public static  class data{
        String code;
        String phone;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
