package com.goldemperor.ScanCode.ScInstock.deleteslide;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldemperor.R;


public class ActionSheet {
	//用户所要删除的条形码字符串
	public static String mSelDelbarcode ;
	public interface OnActionSheetSelected {
		void onClick(int whichButton);
	}

	private ActionSheet() {
	}

	public static Dialog showSheet(Context context, final OnActionSheetSelected actionSheetSelected,
                                   OnCancelListener cancelListener, String SelDelbarcode) {
		final Dialog dlg = new Dialog(context, R.style.ActionSheet);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.cxstockin_actionsheet, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		//获得删除窗体中的控件
		TextView mtitle= (TextView) layout.findViewById(R.id.title);//标题
		TextView mContent = (TextView) layout.findViewById(R.id.btnconfirm);//删除按钮
		TextView mCancel = (TextView) layout.findViewById(R.id.btncancel);//取消按钮


		mContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionSheetSelected.onClick(0);
				dlg.dismiss();
			}
		});

		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				actionSheetSelected.onClick(1);
				dlg.dismiss();
			}
		});
		mSelDelbarcode=SelDelbarcode;
		mtitle.setText("确定要删除条形码："+mSelDelbarcode+"吗?");
		mtitle.setTextSize(25);
		mtitle.setTextColor(context.getResources().getColor(R.color.red));
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		if (cancelListener != null)
			dlg.setOnCancelListener(cancelListener);

		dlg.setContentView(layout);
		dlg.show();

		return dlg;
	}

}
