package com.goldemperor.StockCheck.DoneView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.SublimePickerFragment;
import com.goldemperor.StockCheck.sql.stock_check;
import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.define;
import com.goldemperor.Widget.fancybuttons.FancyButton;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Nova on 2017/7/17.
 */

public class DoneView {

    private Activity mContext;

    private MenuAdapter mMenuAdapter;

    private List<stock_check> mDataList;

    private SwipeMenuRecyclerView mMenuRecyclerView;

    private EditText searchEdit;

    private FancyButton btn_search;

    private FancyButton btn_today;

    private FancyButton btn_week;

    private FancyButton btn_month;

    private FancyButton btn_calendar;

    public DoneView(final Activity act, final View view) {
        mContext = act;

        mDataList = new ArrayList<>();


        mMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(act));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(act));// 添加分割线。


        mMenuAdapter = new MenuAdapter(mDataList);

        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        getDataByDate(Utils.getDateStr(Utils.getCurrentTime(), 1, true), Utils.getDateStr(Utils.getCurrentTime(), 7, false));

        searchEdit = (EditText) view.findViewById(R.id.searchEdit);

        btn_search = (FancyButton) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchEdit.getText().toString().trim().isEmpty()){
                    getDataBySupplier(searchEdit.getText().toString().trim());
                }
            }
        });


        btn_today = (FancyButton) view.findViewById(R.id.btn_today);
        btn_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByDate(Utils.getDateStr(Utils.getCurrentTime(), 1, true), Utils.getCurrentTime());
            }
        });

        btn_week = (FancyButton) view.findViewById(R.id.btn_week);
        btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByDate(Utils.getDateStr(Utils.getCurrentTime(), 1, true), Utils.getDateStr(Utils.getCurrentTime(), 7, false));
            }
        });

        btn_month = (FancyButton) view.findViewById(R.id.btn_month);
        btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataByDate(Utils.getDateStr(Utils.getCurrentTime(), 1, true), Utils.getDateStr(Utils.getCurrentTime(), 30, false));
            }
        });
        //日历回调函数
        final SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {

            }

            @Override
            public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                                int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {

                if (selectedDate != null) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    if (selectedDate.getType() == SelectedDate.Type.SINGLE) {
                        getDataByDate(Utils.getDateStr(String.valueOf(format.format(selectedDate.getStartDate().getTime())), 1, true), String.valueOf(format.format(selectedDate.getStartDate().getTime())));
                    } else if (selectedDate.getType() == SelectedDate.Type.RANGE) {
                        getDataByDate(Utils.getDateStr(String.valueOf(format.format(selectedDate.getEndDate().getTime())), 1, true), String.valueOf(format.format(selectedDate.getStartDate().getTime())));
                    }

                }

            }
        };
        btn_calendar = (FancyButton) view.findViewById(R.id.btn_calendar);


        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);
                Pair<Boolean, SublimeOptions> optionsPair = getOptions();


                if (!optionsPair.first) { // If options are not valid
                    Toast.makeText(mContext, "No pickers activated",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Valid options
                Bundle bundle = new Bundle();
                bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                pickerFrag.setArguments(bundle);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(act.getFragmentManager(), "SUBLIME_PICKER");
            }
        });


    }

    //日历设置方法
    // Validates & returns SublimePicker options
    Pair<Boolean, SublimeOptions> getOptions() {
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;


        displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;


        options.setDisplayOptions(displayOptions);

        // Enable/disable the date range selection feature
        options.setCanPickDateRange(true);

        // Example for setting date range:
        // Note that you can pass a date range as the initial date params
        // even if you have date-range selection disabled. In this case,
        // the user WILL be able to change date-range using the header
        // TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

        // If 'displayOptions' is zero, the chosen options are not valid
        return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
    }


    public void getData() {
        RequestParams params = new RequestParams(define.GetData);

        params.addQueryStringParameter("status1", define.DONE);
        params.addQueryStringParameter("status2", define.CASECLOSE);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                //解析result
                //重新设置数据
                if (result.length() > 1) {
                    ArrayList<stock_check> dataTemp = GsonFactory.jsonToArrayList(result, stock_check.class);
                    mDataList.clear();
                    if (!dataTemp.isEmpty()) {
                        for (int i = 0; i < dataTemp.size(); i++) {
                            stock_check temp = dataTemp.get(i);
                            mDataList.add(temp);
                        }
                    }

                    mContext.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mMenuAdapter.notifyDataSetChanged();
                        }
                    });
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


    public void getDataBySupplier(String supplier) {
        RequestParams params = new RequestParams(define.GetDataBySupplier);

        params.addQueryStringParameter("status1", define.DONE);
        params.addQueryStringParameter("status2", define.CASECLOSE);

        params.addQueryStringParameter("supplier", supplier);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                //解析result
                //重新设置数据
                mDataList.clear();
                if (result.length() > 1) {
                    ArrayList<stock_check> dataTemp = GsonFactory.jsonToArrayList(result, stock_check.class);
                    if (!dataTemp.isEmpty()) {
                        for (int i = 0; i < dataTemp.size(); i++) {
                            stock_check temp = dataTemp.get(i);
                            mDataList.add(temp);
                        }
                    }
                }
                mContext.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", "doneView:"+ex.toString());
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


    public void getDataByDate(String endDate, String startDate) {
        RequestParams params = new RequestParams(define.GetDataBydate);

        params.addQueryStringParameter("status1", define.DONE);
        params.addQueryStringParameter("status2", define.CASECLOSE);

        params.addQueryStringParameter("DateTime", endDate);
        params.addQueryStringParameter("DateTime2", startDate);
        LOG.e(define.GetDataBydate+"\nparams"+params.toJSONString());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                LOG.e("onSuccess="+result);
                //解析result
                //重新设置数据
                mDataList.clear();
                if (result.length() > 1) {
                    ArrayList<stock_check> dataTemp = GsonFactory.jsonToArrayList(result, stock_check.class);
                    if (!dataTemp.isEmpty()) {
                        for (int i = 0; i < dataTemp.size(); i++) {
                            stock_check temp = dataTemp.get(i);
                            mDataList.add(temp);
                        }
                    }
                }
                mContext.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mMenuAdapter.notifyDataSetChanged();
                    }
                });

            }

            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("jindi", ex.toString());
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

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent i = new Intent(mContext, DoneLookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(mDataList.get(position).getId()));
            i.putExtras(bundle);
            mContext.startActivityForResult(i, define.UPDATA);
        }
    };


}
