package com.goldemperor.ShowCapacity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goldemperor.R;
import com.goldemperor.ShowCapacity.model.ProOrderOfCastReport;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.verticalviewpager.ViewPagerFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * File Name : RankingListDayFragment
 * Created by : PanZX on  2018/10/23 23:24
 * Email : 644173944@qq.com
 * Github : https://github.com/Pulini
 * Remark：
 */
@ContentView(R.layout.fragment_ranking_list_month)
public class RankingListMonthFragment extends ViewPagerFragment {

    @ViewInject(R.id.LV_Form_M)
    private ListView LV_Form_M;


    private Activity mActivity;
    private List<ProOrderOfCastReport.Month> POOCR;
    private FormMonthAdapter FMA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = x.view().inject(this, inflater, container);
            mActivity = getActivity();
        }
        return rootView;
    }

    /**
     * 接收表格数据
     *
     * @param pdlfsrs
     */
    public void setFormMonthData(List<ProOrderOfCastReport.Month> pdlfsrs) {
        LOG.e("setCapacityListData");
        POOCR = pdlfsrs;
        LOG.e("月="+pdlfsrs.size());
        if (FMA == null) {
            FMA = new FormMonthAdapter(mActivity, POOCR);
            LV_Form_M.setAdapter(FMA);
        }
        FMA.notifyDataSetChanged();
    }


}
