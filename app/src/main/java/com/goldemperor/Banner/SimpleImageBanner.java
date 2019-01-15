package com.goldemperor.Banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.goldemperor.R;
import com.goldemperor.Utils.LOG;
import com.goldemperor.Widget.banner.widget.Banner.BaseIndicatorBanner;
import com.squareup.picasso.Picasso;

public class SimpleImageBanner extends BaseIndicatorBanner<BannerItem, SimpleImageBanner> {
    private ColorDrawable colorDrawable;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
        final BannerItem item = mDatas.get(position);
        tv.setText(item.title);
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(mContext, R.layout.adapter_simple_image, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);

        final BannerItem item = mDatas.get(position);
        int itemWidth = mDisplayMetrics.widthPixels;
        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

        String imgUrl = item.imgUrl;

        if (!TextUtils.isEmpty(imgUrl)) {
            LOG.e("imgUrl="+imgUrl);
            LOG.e("itemWidth="+itemWidth);
            LOG.e("itemHeight="+itemHeight);
            Picasso.get()
                    .load(imgUrl)
                    .resize(itemWidth,itemHeight)
//                    .placeholder(R.mipmap.downloading_photo)
                    .error(R.drawable.loading_failure)
                    .into(iv);
//            Glide.with(mContext)
//                    .load(imgUrl)
//                    .override(itemWidth, itemHeight)
//                    .centerCrop()
//                    .placeholder(colorDrawable)
//                    .into(iv);
        } else {
            iv.setImageDrawable(colorDrawable);
        }

        return inflate;
    }
}
