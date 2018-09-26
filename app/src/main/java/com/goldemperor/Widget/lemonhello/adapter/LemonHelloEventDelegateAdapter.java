package com.goldemperor.Widget.lemonhello.adapter;


import com.goldemperor.Widget.lemonhello.interfaces.LemonHelloEventDelegate;

import com.goldemperor.Widget.lemonhello.LemonHelloAction;
import com.goldemperor.Widget.lemonhello.LemonHelloInfo;
import com.goldemperor.Widget.lemonhello.LemonHelloView;

/**
 * LemonHello 事件代理适配器
 * Created by LiuRi on 2017/1/11.
 */

public abstract class LemonHelloEventDelegateAdapter implements LemonHelloEventDelegate {

    @Override
    public void onActionDispatch(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {

    }

    @Override
    public void onMaskTouch(LemonHelloView helloView, LemonHelloInfo helloInfo) {

    }
    
}
