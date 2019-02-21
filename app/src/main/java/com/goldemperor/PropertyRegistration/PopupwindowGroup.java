package com.goldemperor.PropertyRegistration;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.PzActivity.DeptModel;
import com.goldemperor.R;
import com.goldemperor.Widget.ClearWriteEditText;
import com.goldemperor.Widget.fancybuttons.FancyButton;

import java.util.ArrayList;
import java.util.List;

/**
 * File Name : PopupwindowGroup
 * Created by : PanZX on  2019/1/11 10:32
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：弹窗选择课组
 */
public class PopupwindowGroup {
    private PopwinListener PL;


    public interface PopwinListener {
        void Confirm(DeptModel dm);
    }

    private Activity mActivity;
    private PopupWindow window;
    private ClearWriteEditText CET_Select;
    private RecyclerView RV_Select;
    private FancyButton FB_Withdraw;
    private FancyButton FB_Confirm;
    private TextView TV_Name;
    private List<DeptModel> DML = new ArrayList<>();
    private List<DeptModel> SDML = new ArrayList<>();
    private SelectAdapter SA;

    public PopupwindowGroup(Activity act, List<DeptModel> list) {
        mActivity = act;
        DML.addAll(list);
        // 创建PopupWindow对象，其中：
        window = new PopupWindow(findiew(mActivity), Utils.dp2px(200), WindowManager.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 设置PopupWindow弹出和消失动画
        window.setAnimationStyle(R.style.AnimationRightFade);
        //设置PopupWindow消失监听
        window.setOnDismissListener(() -> {
            //设置消失恢复亮度
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = 1.0f;
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mActivity.getWindow().setAttributes(lp);
        });
        window.setFocusable(true);
    }

    public void Show(PopwinListener pl) {
        PL = pl;
        //设置显示全屏变暗
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mActivity.getWindow().setAttributes(lp);
        // 显示PopupWindo
        window.showAtLocation(mActivity.getLayoutInflater().inflate(R.layout.activity_property_registration_details_for_pad, null), Gravity.RIGHT, 0, Utils.dp2px(250));

    }

    private View findiew(Activity act) {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(act).inflate(R.layout.popupwindow_select_group, null, false);
        CET_Select = contentView.findViewById(R.id.CET_Select);
        RV_Select = contentView.findViewById(R.id.RV_Select);
        FB_Confirm = contentView.findViewById(R.id.FB_Confirm);
        FB_Withdraw = contentView.findViewById(R.id.FB_Withdraw);
        TV_Name = contentView.findViewById(R.id.TV_Name);

        LinearLayoutManager layoutManager = new LinearLayoutManager(act);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RV_Select.setLayoutManager(layoutManager);
        SDML.addAll(DML);
        SA = new SelectAdapter(DML);
        RV_Select.setAdapter(SA);
        CET_Select.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                selection(s.toString().trim());
            }
        });
        FB_Confirm.setOnClickListener(v -> {
            PL.Confirm(SA.getData());
            window.dismiss();
        });
        FB_Withdraw.setOnClickListener(v -> {
            selection("");
            TV_Name.setText("");
            window.dismiss();
        });
        return contentView;
    }

    /**
     * 筛选数据
     *
     * @param data
     */
    private void selection(String data) {
        SDML.clear();
        if (data.length() == 0) {//搜索框为空，直接添加所有数据
            SDML.addAll(DML);
        } else {
            for (DeptModel deptModel : DML) {
                if (ishave(deptModel.getFName(), data)) {
                    SDML.add(deptModel);
                }
            }
        }
        SA.Updata(SDML);
    }

    /**
     * 是否包含所有输入的字符
     *
     * @param data1 对比对象
     * @param data2 对比值
     * @return
     */
    private boolean ishave(String data1, String data2) {
        if (data1.toUpperCase().contains(data2.toUpperCase())) return true;

        for (int i = 0; i < data2.length(); i++) {
            if (!data1.toUpperCase().contains(String.valueOf(data2.charAt(i)).toUpperCase())) {
                return false;
            }
        }
        return true;
    }

    public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.MyViewHolder> {
        List<DeptModel> data;
        DeptModel dm;
        int index = -1;
        String name;

        public DeptModel getData() {
            return dm;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout LL_BKG;
            TextView TV_Name;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.LL_BKG = itemView.findViewById(R.id.LL_BKG);
                this.TV_Name = itemView.findViewById(R.id.TV_Name);
            }
        }
        public String getName(){
           return name;
        }

        public SelectAdapter(List<DeptModel> data) {
            super();
            this.data = data;
        }

        public void Updata(List<DeptModel> data) {
            this.data = data;
            index = -1;
            notifyDataSetChanged();
        }

        @Override
        public SelectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SelectAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select, null));
        }

        @Override
        public void onBindViewHolder(SelectAdapter.MyViewHolder holder, int position) {
            holder.LL_BKG.setLayoutParams(new ViewGroup.LayoutParams(Utils.dp2px(200),Utils.dp2px(40)));
            if (index == position) {
                holder.LL_BKG.setBackgroundResource(R.drawable.shape_red);
            } else {
                holder.LL_BKG.setBackground(null);
            }
            holder.TV_Name.setText(data.get(position).getFName());
            holder.LL_BKG.setOnClickListener(v -> {
                int ind=index;
                if(index==holder.getLayoutPosition()){
                    index=-1;
                    name="";
                    dm=null;
                    notifyItemChanged(ind);
                }else{
                    index = holder.getLayoutPosition();
                    if (ind >= 0) {
                        notifyItemChanged(ind);
                    }
                    name=data.get(position).getFName();
                    dm=data.get(position);
                    notifyItemChanged(index);
                }
                TV_Name.setText(name);
            });
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            } else {
                return data.size();
            }

        }
    }
}
