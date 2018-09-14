package com.goldemperor.StockCheck.ExceptionalView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.goldemperor.StockCheck.sql.stock_check;
import com.goldemperor.MainActivity.GsonFactory;
import com.goldemperor.MainActivity.ListViewDecoration;
import com.goldemperor.MainActivity.OnItemClickListener;
import com.goldemperor.R;
import com.goldemperor.MainActivity.define;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nova on 2017/7/17.
 */

public class ExceptionalView {

    private Activity mContext;

    private MenuAdapter mMenuAdapter;

    private List<stock_check> mDataList;

    private SwipeMenuRecyclerView mMenuRecyclerView;

    public ExceptionalView(final Activity act, final View view) {
        mContext = act;

        mDataList = new ArrayList<>();


        mMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_view);
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(act));// 布局管理器。
        mMenuRecyclerView.addItemDecoration(new ListViewDecoration(act));// 添加分割线。

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mMenuRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);


        mMenuAdapter = new MenuAdapter(mDataList);

        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mMenuRecyclerView.setAdapter(mMenuAdapter);

        getData();

    }

    public void getData() {
        RequestParams params = new RequestParams(define.GetData);

        params.addQueryStringParameter("status1",  define.EXCEPTIONAL);
        params.addQueryStringParameter("status2", define.EXCEPTIONAL_RESULT);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                //解析result
                //重新设置数据
                mDataList.clear();

                if (result.length()>1) {
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
            Intent i = new Intent(mContext, ExceptionalLookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", String.valueOf(mDataList.get(position).getId()));
            i.putExtras(bundle);
            mContext.startActivityForResult(i, define.UPDATA);
        }
    };

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.wait)
                        .setText("处理") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。


            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //Toast.makeText(mContext, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }

            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 处理按钮被点击。
                Intent i = new Intent(mContext, DisposeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(mDataList.get(adapterPosition).getId()));
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivityForResult(i, define.UPDATA);

                //Toast.makeText(mContext,String.valueOf(mDataList.get(adapterPosition).getId()),Toast.LENGTH_LONG).show();
            }
        }


    };
}
