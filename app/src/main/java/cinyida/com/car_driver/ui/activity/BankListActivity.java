package cinyida.com.car_driver.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.base.BaseActivity;
import cinyida.com.car_driver.base.BaseArrayAdapter;
import cinyida.com.car_driver.net.result.MybankBean;
import cinyida.com.car_driver.ui.holder.BankHolder;
import cinyida.com.car_driver.ui.present.MyBankPresent;
import cinyida.com.car_driver.ui.view.MybankView;
import cinyida.com.car_driver.utils.check_bank.domain.BankCardInfo;
import cinyida.com.car_driver.utils.check_bank.util.CheckBankCardUtil;
import cinyida.com.car_driver.utils.check_bank.util.CheckBankNameUtil;

/**
 * Created by Zheng on 2017/5/4.
 */

public class BankListActivity extends BaseActivity implements MybankView{

    private Button btn_login;
    public MyBankPresent present;
    private TextView card_num,card_type,bank_name;
    private RelativeLayout rl_bg;
    private MybankBean bean;

    @Override
    protected void bindEvent() {
        btn_login.setOnClickListener(this);
        rl_bg.setOnClickListener(this);
    }

    @Override
    protected void initToolBar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        TextView tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的银行卡");
        TextView tv_extra_text= (TextView) findViewById(R.id.tv_extra_text);
        tv_extra_text.setText("修改");
        tv_extra_text.setVisibility(View.VISIBLE);
        tv_extra_text.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        present=new MyBankPresent(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        card_num = (TextView) findViewById(R.id.card_num);
        card_type= (TextView) findViewById(R.id.card_type);
        bank_name= (TextView) findViewById(R.id.bank_name);
        rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
    }


    @Override
    protected int getResourcesId() {
        return R.layout.activity_banklist;
    }



    @Override
    protected void BaseOnclick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                startActivity(Activity_Add_BankCard.class);
                break;
            case R.id.tv_extra_text:
                Intent intent =new Intent(BankListActivity.this,Activity_Add_BankCard.class);
                if (bean!=null){
                    intent.putExtra("bank",bean.getBank());
                    intent.putExtra("name",bean.getName());
                }
                startActivity(intent);
                break;
            case R.id.rl_bg:

                break;
        }
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public void initData() {
        bean = present.getData();
        String num= bean.getBank();
        checkBankName(num);
    }

    @Override
    public void showAdd() {
        btn_login.setVisibility(View.VISIBLE);
        rl_bg.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.getNetData();
    }

    private void checkBankName(String bankText) {

        Pattern pattern = Pattern.compile("[0-9]*");
        if (!pattern.matcher(bankText).matches()) {
//            bankNumInput.setError("银行卡号必须是数字");

        } else if (bankText.length() < 15 || bankText.length() > 19) {
//            bankNumInput.setError("银行卡位数必须是15到19位");

        } else if (!CheckBankCardUtil.checkBankCard(bankText)) {
//            bankNumInput.setError("卡号不合规");

        } else {
            BankCardInfo bankCardInfo = null;
            try {
                bankCardInfo = new
                        CheckBankNameUtil().getBankCardInfo(this, bankText);

                if (bankCardInfo == null) {//返回为null，代表没有识别到
//                    tvBankName.setText("没有识别到");
                } else {//识别到了
                    String bankName = bankCardInfo.getBankName();
                    String bankType = bankCardInfo.getCardType();
                    String bankCode = bankCardInfo.getBankCode();
                    String cardTypeName = bankCardInfo.getCardTypeName();
                    bank_name.setText(bankName);
                    card_type.setText(cardTypeName);
                    card_num.setText(bankText);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
