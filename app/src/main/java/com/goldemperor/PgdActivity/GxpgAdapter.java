/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.goldemperor.PgdActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.MainActivity.Utils;
import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Utils.SPUtils;
import com.goldemperor.Utils.WebServiceUtils;
import com.goldemperor.Widget.NiceSpinner.NiceSpinner;
import com.goldemperor.Widget.ScrollListenerHorizontalScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.goldemperor.PgdActivity.PgdActivity.selectWorkCardPlan;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class GxpgAdapter extends RecyclerView.Adapter<GxpgAdapter.DefaultViewHolder> {

    private List<ProcessWorkCardPlanEntry> ls;

    private OnItemClickListener mOnItemClickListener;

    public static List<Integer> CheckBoxList;
    public static List<Double> maxlist = new ArrayList<>();


//    private String[][] nameList;

    private static List<NameModel> dropStrings = new ArrayList<>();

    public static List<ScrollListenerHorizontalScrollView> ScrollViewList = new ArrayList<ScrollListenerHorizontalScrollView>();

    public static GxpgActivity gxpgActivity;

    public static DbManager dbManager;

    public static int listSize;

    public GxpgAdapter(List<ProcessWorkCardPlanEntry> ls, List<NameModel> nameList, GxpgActivity gxpgActivity, DbManager dbManager) {
        this.ls = ls;
        CheckBoxList = new ArrayList<Integer>();
        for (int i = 0; i < 500; i++) {
            CheckBoxList.add(1);
        }
        dropStrings = new ArrayList<>();
        dropStrings.addAll(nameList);
        this.gxpgActivity = gxpgActivity;
        this.dbManager = dbManager;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        listSize = ls.size();
        return ls == null ? 0 : ls.size();

    }


    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gpxg_item, parent, false),dropStrings);
    }

    @Override
    public void onBindViewHolder(GxpgAdapter.DefaultViewHolder holder, int position) {
        if (gxpgActivity.sc_ProcessWorkCardEntryList != null && gxpgActivity.sc_ProcessWorkCardEntryList.size() > 0) {
            for (ProcessWorkCardPlanEntry l : ls) {
                maxlist.add(l.getFmustqty());
            }

            setData(holder,ls, position);
        }

    }


    public void setData(final DefaultViewHolder holder, final List<ProcessWorkCardPlanEntry> list, final int position) {
        //关闭工单行设置不可操作
        if (!gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getIsOpen()) {
            holder.edit_dispatchingnumber.setEnabled(false);
            holder.edit_userNumber.setEnabled(false);
            holder.nameDropDown.setEnabled(false);
        } else {
            holder.edit_dispatchingnumber.setEnabled(true);
            holder.edit_userNumber.setEnabled(true);
            holder.nameDropDown.setEnabled(true);
        }

        holder.tv_fmustqty.setText(String.valueOf(gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getFmustqty()));
        holder.tv_readyRecordCount.setText(String.valueOf((int) gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getReportedqty()));
        if (holder.getAdapterPosition() < gxpgActivity.sc_ProcessWorkCardEntryList.size()) {

            holder.tv_noReportednumber.setText(String.valueOf(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getFqty()));
//            gxpgActivity.sc_ProcessWorkCardEntryList.get(position).setFpreschedulingqty( gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getFpreschedulingqty());
            holder.edit_dispatchingnumber.setText( gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getFpreschedulingqty()+"");

            holder.edit_dispatchingnumber.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    //这个应该是在改变的时候会做的动作吧，具体还没用到过。
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub
                    //这是文本框改变之前会执行的动作
                }

                @Override
                public void afterTextChanged(Editable s) {
//                        edit_dispatchingnumber.setText(Integer.parseInt(s.toString()));
                    if (s.toString().length()>1&&s.toString().substring(0, 1).equals("0")) {
                        holder.edit_dispatchingnumber.setText("0");
                        return;
                    }
                    if (holder.edit_dispatchingnumber.isFocused() && s.toString().length() > 0 && Utils.isNumeric(s.toString())) {
                        int qtyNumber=(int) Double.parseDouble(holder.edit_dispatchingnumber.getText().toString().trim());
                        if (qtyNumber!= 0) {
                            if(selectWorkCardPlan.getFcanreportbynostockin()){
                                gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpreschedulingqty(qtyNumber);
                            }else {
                                //先不管超标设置总数
//                                if (Double.parseDouble(edit_dispatchingnumber.getText().toString().trim()) > list.get(position).getFmustqty()) {
                                if (qtyNumber > gxpgActivity.norecord) {
//                                    edit_dispatchingnumber.setText((int) list.get(position).getFmustqty() + "");
                                    holder.edit_dispatchingnumber.setText(gxpgActivity.norecord + "");
                                    holder.edit_dispatchingnumber.setSelection(holder.edit_dispatchingnumber.getText().length());
                                }
                                gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpreschedulingqty(qtyNumber);
                                //设置后判断总数
                                float count = 0;
                                for (int i = 0; i < gxpgActivity.sc_ProcessWorkCardEntryList.size(); i++) {
                                    if (gxpgActivity.sc_ProcessWorkCardEntryList.get(i).getFprocessname().equals(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getFprocessname())) {

                                        count += gxpgActivity.sc_ProcessWorkCardEntryList.get(i).getFpreschedulingqty();

                                    }

                                }
//                                LOG.e("工序：" + gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).getFprocessname() + "count:" + count + " mustqty:" + list.get(position).getFmustqty());
//                                LOG.e("position："+position);
                                int max = gxpgActivity.norecord;
                                if (selectWorkCardPlan.getFcanreportbynostockin()) {
                                    max = (int) gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getFmustqty();
                                }
                                //超标就将数量设为0
                                if (count > max) {//总数大于已汇报未计工数
//                                    LOG.e(""+count+"====="+maxlist.get(position));
                                    //Log.e("jindi", "afterTextChanged:" + getAdapterPosition());

                                    holder.edit_dispatchingnumber.setText("0");
                                    holder.edit_dispatchingnumber.setSelection(holder.edit_dispatchingnumber.getText().length());

                                    gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpreschedulingqty(0);
                                    Toast.makeText(gxpgActivity, "无法派工,派工总数超标", Toast.LENGTH_LONG).show();
                                }

                            }
//                                float per = gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).getFpreschedulingqty() / (gxpgActivity.processWorkCardPlanEntryList.get(getAdapterPosition()).getFmustqty());
//                                float per = gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).getFpreschedulingqty() / gxpgActivity.norecord;

                            LOG.e("预排数量=" + gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getFpreschedulingqty());
//                                LOG.e("Fmustqty=" + gxpgActivity.processWorkCardPlanEntryList.get(getAdapterPosition()).getFmustqty());
                            LOG.e("已汇报未计工数=" + gxpgActivity.norecord);

                            try {
                                GxpgPlan gxpgPlan = dbManager.selector(GxpgPlan.class).where("style", " = ", selectWorkCardPlan.getPlantbody()).and("processname", "=", gxpgActivity.processWorkCardPlanEntryList.get(holder.getAdapterPosition()).getProcessname()).and("username", "=", gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getName()).findFirst();

                                if (gxpgPlan != null) {
                                    gxpgPlan.setPer(0);
                                    gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpartitioncoefficient(0);
                                    dbManager.saveOrUpdate(gxpgPlan);
                                } else {
                                    GxpgPlan newGxpgPlan = new GxpgPlan();
                                    newGxpgPlan.setStyle(gxpgActivity.processWorkCardPlanEntryList.get(holder.getAdapterPosition()).getPlantbody());
                                    newGxpgPlan.setProcessname(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getFprocessname());
                                    newGxpgPlan.setUsername(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getName());
                                    newGxpgPlan.setUsernumber(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getJobNumber());
                                    newGxpgPlan.setEmpid(gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).getFempid());
//                                        newGxpgPlan.setPer((float) (gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).getFpreschedulingqty() / (gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).getFmustqty())));
                                    newGxpgPlan.setPer(0);
                                    gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpartitioncoefficient(0);
                                    dbManager.saveOrUpdate(newGxpgPlan);
                                }
                            } catch (DbException e) {
                                Log.e("jindi", e.toString());

                            }
                        } else {
                            gxpgActivity.sc_ProcessWorkCardEntryList.get(holder.getAdapterPosition()).setFpreschedulingqty(0);
                        }

                    }
                }
            });

            holder. tv_processcode.setText(list.get(position).getProcesscode());
            holder. tv_processname.setText(list.get(position).getProcessname());

            //数据加载有延迟需要先判断
            if (gxpgActivity.sc_ProcessWorkCardEntryList.get(position) != null) {

                if (gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getIsOpen()) {
                    holder.edit_userNumber.setText(gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getJobNumber());
                    holder. nameDropDown.setText(gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getName());
                } else {
                    holder. edit_userNumber.setText("000000");
                    holder.  edit_dispatchingnumber.setText("0");
                    holder.  tv_noReportednumber.setText("0");
                    holder. tv_readyRecordCount.setText("0");
                    holder. tv_fmustqty.setText("0");
                    holder. nameDropDown.setText("关闭");
                }
            }


            holder. tv_noReportednumber.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    //do something
                    final EditText editText = new EditText(gxpgActivity);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    AlertDialog.Builder inputDialog =
                            new AlertDialog.Builder(gxpgActivity);
                    inputDialog.setTitle("修改计工数").setView(editText);
                    inputDialog.setNegativeButton("取消", null);
                    inputDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int inputNumber = 0;
                                    if (editText.getText().toString() != null && !editText.getText().toString().trim().isEmpty()) {
                                        inputNumber = Integer.parseInt(editText.getText().toString());
                                    }
                                    gxpgActivity.sc_ProcessWorkCardEntryList.get(position).setFqty(inputNumber);
                                    float count = 0;
                                    for (int i = 0; i < gxpgActivity.sc_ProcessWorkCardEntryList.size(); i++) {
                                        if (gxpgActivity.processWorkCardPlanEntryList.get(i).getProcessname().equals(gxpgActivity.processWorkCardPlanEntryList.get(position).getProcessname())) {
                                            count += gxpgActivity.sc_ProcessWorkCardEntryList.get(i).getFqty();
                                        }
                                    }
                                    if (!selectWorkCardPlan.getFcanreportbynostockin()) {
                                        if (count > gxpgActivity.norecord) {
                                            Toast.makeText(gxpgActivity, "计工数超过汇报数", Toast.LENGTH_LONG).show();
                                            gxpgActivity.sc_ProcessWorkCardEntryList.get(position).setFqty(inputNumber);
                                        } else if (count < gxpgActivity.norecord) {
                                            Toast.makeText(gxpgActivity, "计工数少于汇报数", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    gxpgActivity.mMenuAdapter.notifyDataSetChanged();


                                }
                            }).show();
                }
            });
        }
        if (!gxpgActivity.sc_ProcessWorkCardEntryList.get(position).getIsOpen()) {
            holder.ScrollView.setBackgroundColor(Color.parseColor("#C0C0C0"));
            holder.tv_noReportednumber.setBackgroundColor(Color.parseColor("#C0C0C0"));
        } else {
            holder. ScrollView.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tv_noReportednumber.setBackgroundColor(Color.parseColor("#FFF68F"));
        }
        ScrollViewList.add(holder.ScrollView);
        holder.ScrollView.setOnScrollListener(new ScrollListenerHorizontalScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX) {
                for (int i = 0; i < ScrollViewList.size(); i++) {
                    ScrollViewList.get(i).scrollTo(scrollX, holder.ScrollView.getScrollY());
                }
                gxpgActivity.ScrollView.scrollTo(scrollX, holder.ScrollView.getScrollY());
            }
        });
//        holder. ScrollView.scrollTo(GxpgActivity.mScrollX, holder.ScrollView.getScrollY());
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder  {

        NiceSpinner nameDropDown;
        EditText edit_userNumber;
        EditText edit_dispatchingnumber;


        TextView tv_processcode;
        TextView tv_processname;
        TextView tv_readyRecordCount;
        TextView tv_noReportednumber;

        TextView tv_fmustqty;

        public ScrollListenerHorizontalScrollView ScrollView;

        public DefaultViewHolder(View itemView, final List<NameModel> nameList) {
            super(itemView);
            nameDropDown = (NiceSpinner) itemView.findViewById(R.id.XLarge);
            edit_userNumber = (EditText) itemView.findViewById(R.id.edit_userNumber);
            edit_dispatchingnumber = (EditText) itemView.findViewById(R.id.edit_dispatchingnumber);
            edit_dispatchingnumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ScrollView = (ScrollListenerHorizontalScrollView) itemView.findViewById(R.id.ScrollView);


            tv_processcode = (TextView) itemView.findViewById(R.id.tv_processcode);
            tv_processname = (TextView) itemView.findViewById(R.id.tv_processname);
            //tv_havedispatchingnumber = (TextView) itemView.findViewById(R.id.tv_havedispatchingnumber);
            tv_readyRecordCount = (TextView) itemView.findViewById(R.id.tv_readyRecordCount);
            tv_noReportednumber = (TextView) itemView.findViewById(R.id.tv_noReportednumber);

            tv_fmustqty = (TextView) itemView.findViewById(R.id.tv_fmustqty);
            List<String> dataset = new ArrayList<>();
            LOG.e("nameList=" + nameList.size());
            for (NameModel nameModel : nameList) {
                dataset.add(nameModel.getCode() + "   " + nameModel.getName());
//                LOG.e("dataset="+nameModel.getName());
            }

            nameDropDown.attachDataSource(dataset);
            nameDropDown.addOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    LOG.e("Name=" + nameList.get(position).getName());
                    nameDropDown.setText(nameList.get(position).getName());
                    edit_userNumber.setText(nameList.get(position).getCode());
                    gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setName(nameList.get(position).getName());
                    gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setJobNumber(nameList.get(position).getCode());
                    gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setFempid(Integer.valueOf(nameList.get(position).getID()));

                }
            });

            edit_userNumber.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    //这个应该是在改变的时候会做的动作吧，具体还没用到过。
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub
                    //这是文本框改变之前会执行的动作
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                    /**这是文本框改变之后 会执行的动作
                     */
                    if (s.toString().trim().length() >= 6 && edit_userNumber.isFocused()) {
//                        getUserInfo(edit_userNumber.getText().toString().trim());
                        getUser(edit_userNumber.getText().toString().trim());
                    }
                }
            });


        }
        private void getUser(String FNumber) {
            HashMap<String, String> map = new HashMap<>();
            map.put("FDeptmentID", "");
            map.put("FEmpNumber", FNumber);
            map.put("suitID", "1");
            WebServiceUtils.WEBSERVER_NAMESPACE = define.tempuri;// 命名空间
            WebServiceUtils.callWebService(
                    SPUtils.getServerPath() + define.ErpForAndroidStockServer,
                    define.GetEmpByFnumber,
                    map,
                    new WebServiceUtils.WebServiceCallBack() {
                        @Override
                        public void callBack(String result) {
                            if (result != null) {
                                try {
                                    result = URLDecoder.decode(result, "UTF-8");
                                    LOG.e("getUser:" + result);
                                    Gson g = new Gson();
                                    JSONObject jsonObject = new JSONObject(result);
                                    String ReturnType = jsonObject.getString("ReturnType");
                                    String ReturnMsg = jsonObject.getString("ReturnMsg");
                                    if (ReturnType.equals("success")) {
                                        List<Emp> user = g.fromJson(ReturnMsg, new TypeToken<List<Emp>>() {
                                        }.getType());
                                        String empname = user.get(0).getEmp_Name();
                                        int id = Integer.valueOf(user.get(0).getEmp_ID());
                                        String Code = user.get(0).getEmp_Code();
                                        int Departid = user.get(0).getEmp_Departid();
                                        nameDropDown.setText(empname);
                                        gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setFempid(id);
                                        gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setName(empname);
                                        gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setJobNumber(Code);
                                        gxpgActivity.sc_ProcessWorkCardEntryList.get(getAdapterPosition()).setFdeptmentid(Departid);
                                    } else {
                                        nameDropDown.setText("无此工号");
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(gxpgActivity, "员工:" + edit_userNumber.getText().toString().trim() + "  数据异常，请联系管理员确定数据。", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                nameDropDown.setText("无此工号");
                            }
                        }
                    });
        }
    }


}
