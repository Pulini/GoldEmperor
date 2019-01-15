package com.goldemperor.ShowCapacity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldemperor.MainActivity.define;
import com.goldemperor.R;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * File Name : ShowTemplateFragment
 * Created by : PanZX on  2018/10/25 11:10
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_show_template)
public class ShowTemplateFragment extends ViewPagerFragment {

    @ViewInject(R.id.IV_Template)
    private ImageView IV_Template;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
        }
        Picasso.get()
                .load("http://192.168.99.79:8083/Picture/shoes.png")
                .placeholder(R.mipmap.downloading_photo)
                .error(R.drawable.loading_failure)
                .into(IV_Template);
        return rootView;
    }
}
