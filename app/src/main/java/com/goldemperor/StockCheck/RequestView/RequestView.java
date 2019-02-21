package com.goldemperor.StockCheck.RequestView;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;



import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Widget.CheckBox;
import com.goldemperor.R;
import com.goldemperor.StockCheck.StockCheckActivity;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Widget.lemonhello.LemonHello;
import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;
import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloActionDelegate;
import com.panzx.pulini.Bootstrap.BootstrapButton;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Nova on 2017/7/17.
 */

public class RequestView {

    private BootstrapButton submit;

    private EditText edit_number;
    private EditText edit_proposer;

    private EditText edit_supplier;

    private com.goldemperor.Widget.CheckBox checkMessage;

    public RequestView(final StockCheckActivity act, final View view) {


        edit_number=(EditText)view.findViewById(R.id.edit_number);
        edit_proposer=(EditText)view.findViewById(R.id.edit_proposer);

        edit_supplier=(EditText)view.findViewById(R.id.edit_supplier);

        edit_number.setText((String)SPUtils.get(define.SharedCheckNumber,""));
        edit_proposer.setText((String)SPUtils.get(define.SharedUser,""));

        edit_supplier.setText((String)SPUtils.get(define.SharedSupplier,""));

        submit = (BootstrapButton) view.findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SPUtils.put(define.SharedCheckNumber, edit_number.getText().toString());
                SPUtils.put(define.SharedProposer, edit_proposer.getText().toString());

                SPUtils.put(define.SharedSupplier, edit_supplier.getText().toString());


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
        checkMessage.setChecked((boolean)SPUtils.get(define.SharedCheckMessage, false));

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
