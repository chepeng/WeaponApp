package com.weapon.joker.app.message.office;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.app.message.databinding.FragmentOfficeBinding;
import com.weapon.joker.lib.mvvm.common.BaseFragment;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.OfficeFragment
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   : 官方消息界面
 * </pre>
 */

public class OfficeFragment extends BaseFragment<OfficeViewModel, OfficeModel> implements OfficeContact.View {

    private FragmentOfficeBinding mDataBinding;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_office;
    }

    @Override
    public void initView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mDataBinding = ((FragmentOfficeBinding) getViewDataBinding());
        setToolbar();
        getViewModel().init();
    }

    /**
     * Toolbar 相关设置
     */
    private void setToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mDataBinding.toolbar);
        // 设置 toolbar 具有返回按钮
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        mDataBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    @Override
    public int getBR() {
        return BR.officeModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 注册消息接收事件
        JMessageClient.registerEventReceiver(this);
    }

    @Override
    public void onDestroy() {
        // 取消消息接收事件的注册
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }


    /**
     * 接收消息事件
     * 目前只支持文字消息，后面再进行优化
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        Message message = event.getMessage();
        switch (message.getContentType()) {
            case text:
                // 处理文字消息
                TextContent textContent = (TextContent) message.getContent();
                String text = textContent.getText();
                Logger.t("Office").i(text);
            default:
                Logger.t("Office").i(message.getFromType());
                break;
        }
    }

    public void onEvent(NotificationClickEvent event) {
        Logger.t("Office").i("消息被点击");
    }
}
