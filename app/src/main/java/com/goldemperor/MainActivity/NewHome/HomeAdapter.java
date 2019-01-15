package com.goldemperor.MainActivity.NewHome;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.goldemperor.MainActivity.Utils;
import com.goldemperor.ShowCapacity.ViewUtils;
import com.goldemperor.Widget.fancybuttons.FancyButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * File Name : HomeAdapter
 * Created by : PanZX on  2018/9/30 09:14
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
public class HomeAdapter extends BaseAdapter {
    private HomeItemClickListener HICL;
    private Activity mActivity;
    List<ButtonData> BD;


    public interface HomeItemClickListener {
        void click(int position);
    }

    public HomeAdapter(Activity activity, List<ButtonData> bd) {
        mActivity = activity;
        BD = bd;
        if (BD == null) {
            BD = new ArrayList<>();
        }
    }

    public void setOnItemClick(HomeItemClickListener hicl) {
        HICL = hicl;
    }

    @Override
    public int getCount() {
        return BD.size();
    }

    @Override
    public Object getItem(int position) {
        return BD.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FancyButton FB = new FancyButton(mActivity);
        FB.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        FB.setText(BD.get(position).getName());
        FB.setTextSize(20);
        FB.setIconResource(BD.get(position).getImageId());
        FB.setFontIconSize(20);
        FB.setTextColor(Color.parseColor("#ffffff"));
        FB.setBorderColor(Color.parseColor("#2DC6E6"));
        if (position >= ViewUtils.getColor().size() - 1) {
            FB.setBackgroundColor(ViewUtils.getColor().get(position));
        } else {
            FB.setBackgroundColor(ViewUtils.getColor().get(new Random().nextInt((ViewUtils.getColor().size()))));
        }
        FB.setFocusBackgroundColor(Color.parseColor("#FFF78C"));
        FB.setBorderWidth(Utils.dp2px(2));
        FB.setRadius(Utils.dp2px(10));
        FB.setPadding(0, Utils.dp2px(10), 0, Utils.dp2px(10));
        FB.setIconPosition(FancyButton.POSITION_TOP);
        FB.setOnClickListener(v -> {
            if (HICL != null) HICL.click(position);
        });
        return FB;
    }


}
