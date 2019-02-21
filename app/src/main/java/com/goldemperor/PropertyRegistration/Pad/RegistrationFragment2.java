package com.goldemperor.PropertyRegistration.Pad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goldemperor.PropertyRegistration.PropertyDetailsModel;
import com.goldemperor.PropertyRegistration.RegistrationUtils;
import com.goldemperor.R;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * File Name : RegistrationFragment1
 * Created by : PanZX on  2019/1/21 10:18
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_registration_2)
public class RegistrationFragment2 extends ViewPagerFragment {


    @ViewInject(R.id.LV_CardEntry)
    private ListView LV_CardEntry;
    private RegistrationUtils RU;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
        }
        RU = new RegistrationUtils(getActivity());
        return rootView;
    }

    public void setList(List<PropertyDetailsModel.list> cardEntry) {
        LV_CardEntry.setAdapter(new RegistrationUtils.MyAdapterForPad(cardEntry));
    }


}