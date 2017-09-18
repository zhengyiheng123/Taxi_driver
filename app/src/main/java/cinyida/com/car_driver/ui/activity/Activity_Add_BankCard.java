package cinyida.com.car_driver.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Pattern;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.ui.present.AddBankPresent;
import cinyida.com.car_driver.ui.view.AddBankView;
import cinyida.com.car_driver.utils.ToastUtils;
import cinyida.com.car_driver.utils.check_bank.domain.BankCardInfo;
import cinyida.com.car_driver.utils.check_bank.util.CheckBankCardUtil;
import cinyida.com.car_driver.utils.check_bank.util.CheckBankNameUtil;

/**
 * Created by Zheng on 2017/5/4.
 */

public class Activity_Add_BankCard extends BaseActivity implements AddBankView{
    private EditText et_bank_num;
    private EditText et_real_name;
    private TextView tv_belong;
    private String bankText;
    private AddBankPresent present;
    private Button btn_login;
    private String bank;
    private String name;

    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                String bankNum=et_bank_num.getText().toString();
                Pattern pattern = Pattern.compile("[0-9]*");
                if (!pattern.matcher(bankNum).matches()) {
                    ToastUtils.show(getApplicationContext(),"银行卡号必须是数字",0);

                } else if (bankNum.length() < 15 || bankNum.length() > 19) {
                    ToastUtils.show(getApplicationContext(),"银行卡位数必须是15到19位",0);
                } else if (!CheckBankCardUtil.checkBankCard(bankNum)) {
                    ToastUtils.show(getApplicationContext(),"卡号不合规",0);
                }
                else if (bank.equals(bankNum) && name.equals(et_real_name.getText().toString())){
                    ToastUtils.show(getApplicationContext(),"没有改变",0);
                }
                else {
                    present.addBank();
                }
                break;
        }
    }

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        et_bank_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                bankText = editable.toString();
                if (bankText.length() >= 12) {
                    checkBankName();
                }
            }
        });
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("添加银行卡");
    }

    @Override
    protected void initView() {
        bank = getIntent().getStringExtra("bank");
        name = getIntent().getStringExtra("name");
        present = new AddBankPresent(this);
        et_bank_num = (EditText) findViewById(R.id.et_bank_num);
        et_real_name = (EditText) findViewById(R.id.et_realname);
        et_bank_num.setText(bank);
        et_real_name.setText(name);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_belong = (TextView) findViewById(R.id.tv_belong);
    }

    @Override
    protected int getResourcesId() {
        return R.layout.activity_add_banl_card;
    }

    /**
     * 识别银行卡名称
     */
    private void checkBankName() {
        BankCardInfo bankCardInfo = null;
        try {
            bankCardInfo = new CheckBankNameUtil().getBankCardInfo(this, bankText);

            if (bankCardInfo == null) {//返回为null，代表没有识别到
                tv_belong.setText("没有识别到");
            } else {//识别到了
                String bankName = bankCardInfo.getBankName();
                String bankType = bankCardInfo.getCardType();
                String bankCode = bankCardInfo.getBankCode();
                String cardTypeName = bankCardInfo.getCardTypeName();
                tv_belong.setText("银行:" + bankName  + "  类别:" + cardTypeName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public String getName() {
        return et_real_name.getText().toString();
    }

    @Override
    public String getBanknum() {
        return et_bank_num.getText().toString();
    }

    @Override
    public void finished() {
        finish();
    }
}
