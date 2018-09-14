package com.goldemperor.ScanCode.ScInstock.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.os.Message;

import com.goldemperor.ScanCode.ScInstock.model.MessageEnum;
import com.goldemperor.ScanCode.ScInstock.model.MessageObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xufanglou on 2016-07-22.
 * 对话框类
 */
public class Dialog {
    public Context myContext;
    public AlertDialog.Builder builder = null;
    private int counter = 0;
    private int TimeStep = 1000;
    private AlertDialog alertDialog = null;//私有的对话框
    public DialogHandler dialogHandler;
    private android.os.Handler myHandler;
    public Dialog(Context context) {
        myContext = context;
        dialogHandler = new DialogHandler(myContext.getMainLooper());
    }
    public Dialog(Context context, android.os.Handler handler) {
        myContext = context;
        myHandler=handler;
        dialogHandler = new DialogHandler(myContext.getMainLooper());
    }
    public void ShowAlertDialog(String Title, String ConfirmInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage(ConfirmInfo);
        builder.setTitle(Title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog= builder.create();
        alertDialog.show();
    }
    public void ShowYesOrNoDialog(String Title, String ConfirmInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage(ConfirmInfo);
        builder.setTitle(Title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendMessage("ClearData", MessageEnum.ClearBarCodeData);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog= builder.create();
        alertDialog.show();
    }
    public void SendMessage(String Content, int messageEnum)
    {
        SendMessage(Content,messageEnum,"");
    }
    public void SendMessage(String Content, int messageEnum, String MessageType)
    {
        Message message = new Message();
        MessageObject myMessageObject = new MessageObject();
        myMessageObject.Content = Content;
        myMessageObject.context =myContext;
        myMessageObject.MessageType=MessageType;
        message.what = messageEnum;
        message.obj = myMessageObject;
        myHandler.sendMessage(message);
    }
    public void ClearBarCodeDataDialog(String Title, String ConfirmInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage(ConfirmInfo);
        builder.setTitle(Title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendMessage("ClearData", MessageEnum.ClearBarCodeData);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendMessage("ClearData", MessageEnum.ClearBarCodeData);
                dialog.dismiss();
            }
        });
        alertDialog= builder.create();
        alertDialog.show();
    }
    public void ClearSubmitDataDialog(String Title, String ConfirmInfo, final String MessageType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage(ConfirmInfo);
        builder.setTitle(Title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendMessage("ClearSubmitData", MessageEnum.ClearSubmitData,MessageType);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog= builder.create();
        alertDialog.show();
    }
    public void ShowAutoCloseDialog(String Title, String ConfirmInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);

        //创建定时器
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                counter++;
                if (counter == 2) {//2秒关闭
                    Message message = new Message();
                    message.what = 1;
                    dialogHandler.sendMessage(message);

                }
            }
        };
        timer.schedule(task, TimeStep, TimeStep);
        builder.setMessage(ConfirmInfo);
        builder.setTitle(Title);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog= builder.create();
        alertDialog.show();
    }

    public class DialogHandler extends android.os.Handler {

        private DialogHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (alertDialog != null & alertDialog.isShowing()) {
                        alertDialog.dismiss();  //关闭对话框
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
