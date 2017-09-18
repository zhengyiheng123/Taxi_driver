package cinyida.com.car_driver.ui.holder;

import android.content.Context;
import android.view.View;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseViewHolder;

/**
 * Created by Zheng on 2017/5/4.
 */

public class BankHolder extends BaseViewHolder<String> {
    @Override
    public BaseViewHolder onCreateViewHolder(View v) {
        return this;
    }

    @Override
    public void onBindViewHolder(Context context, String s) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.item_bank;
    }
}
