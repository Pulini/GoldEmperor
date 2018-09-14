package com.goldemperor.Banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import com.goldemperor.R;
import com.goldemperor.Widget.banner.widget.Banner.BaseIndicatorBanner;

public class SimpleTextBanner extends BaseIndicatorBanner<String, SimpleTextBanner> {
    public SimpleTextBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleTextBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTextBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_text, null);
        TextView tv = ViewFindUtils.find(inflate, R.id.tv);
        tv.setText(mDatas.get(position));

        return inflate;
    }
}
