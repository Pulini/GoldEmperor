package com.goldemperor.StockCheck.RequestView;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.beardedhen.androidbootstrap.BootstrapButton;
import com.goldemperor.Widget.CheckBox;
import com.goldemperor.R;
import com.goldemperor.StockCheck.StockCheckActivity;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;



import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Nova on 2017/7/17.
 */

public class RequestView {

    private BootstrapButton submit;

    private SharedPreferences dataPref;
    private SharedPreferences.Editor dataEditor;
    private EditText edit_number;
    private EditText edit_proposer;

    private EditText edit_supplier;

    private com.goldemperor.Widget.CheckBox checkMessage;

    public RequestView(final StockCheckActivity act, final View view) {

        dataPref = act.getSharedPreferences(define.SharedName, 0);
        dataEditor = dataPref.edit();

        edit_number=(EditText)view.findViewById(R.id.edit_number);
        edit_proposer=(EditText)view.findViewById(R.id.edit_proposer);

        edit_supplier=(EditText)view.findViewById(R.id.edit_supplier);

        edit_number.setText(dataPref.getString(define.SharedCheckNumber,""));
        edit_proposer.setText(dataPref.getString(define.SharedUser,""));

        edit_supplier.setText(dataPref.getString(define.SharedSupplier,""));

        submit = (BootstrapButton) view.findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dataEditor.putString(define.SharedCheckNumber, edit_number.getText().toString());
                dataEditor.putString(define.SharedProposer, edit_proposer.getText().toString());

                dataEditor.putString(define.SharedSupplier, edit_supplier.getText().toString());

                dataEditor.commit();

                if (edit_number.getText().toString().trim().isEmpty()) {
                    LemonHello.getWarningHello("请输入正确的单号", "")
                            .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                } else if (edit_proposer.getText().toString().isEmpty()) {
                    LemonHello.getWarningHello("请输入正确的申请人姓名", "")
                            .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                                @Override
                                public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                    helloView.hide();
                                }
                            }))
                            .show(act);
                } else {
                    RequestParams params = new RequestParams(define.SubmitRequest);
                    params.addQueryStringParameter("number", edit_number.getText().toString().trim());
                    params.addQueryStringParameter("proposer", edit_proposer.getText().toString().trim());
                    params.addQueryStringParameter("supplier", edit_supplier.getText().toString().trim());
                    Log.e("jindi",params.toString());
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(final String result) {
                            //解析result
                            //重新设置数据
                            Log.e("jindi",result);
                            LemonHello.getSuccessHello(result, "")
                                    .addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
                                        @Override
                                        public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                            helloView.hide();
                                        }
                                    }))
                                    .show(act);
                            //提交后刷新稽查数据
                            if(act.waitView!=null){
                                act.waitView.getData();
                                act.pager.setCurrentItem(1);
                            }
                        }

                        //请求异常后的回调方法
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        //主动调用取消请求的回调方法
                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                }
            }

        });

        checkMessage = (CheckBox) view.findViewById(R.id.CheckNoti);
        checkMessage.setChecked(dataPref.getBoolean(define.SharedCheckMessage, false));

//        if(dataPref.getBoolean(define.SharedCheckMessage, false)){
//            MiPushClient.subscribe(act, "CheckMessage", null);
//        }
//
//        checkMessage.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
//            @Override
//            public void onChange(boolean checked) {
//                dataEditor.putBoolean(define.SharedCheckMessage, checked);
//                dataEditor.commit();
//                if(checked){
//                    MiPushClient.subscribe(act, "CheckMessage", null);
//                }else{
//                    MiPushClient.unsubscribe(act, "CheckMessage", null);
//                }
//            }
//        });

    }
}
