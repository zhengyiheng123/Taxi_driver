package cinyida.com.car_driver.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cinyida.com.car_driver.R;
import cinyida.com.car_driver.app.MyApplication;
import cinyida.com.car_driver.net.result.Driver_Bean;

/**
 * Created by Zheng on 2017/6/5.
 */

public class MyGridView extends BaseAdapter {
    List<String> biaoqian;
    public MyGridView(List<String> biaoqian){
        this.biaoqian=biaoqian;
    }
    @Override
    public int getCount() {
        return biaoqian.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VieHolder holder;
        if (view==null){
            holder=new VieHolder();
            view= LayoutInflater.from(MyApplication.getCtx()).inflate(R.layout.item_gridview_mypage,null);
            holder.tv_comment= (TextView) view.findViewById(R.id.tv_comment);
            view.setTag(holder);
        }else {
            holder= (VieHolder) view.getTag();
        }
        holder.tv_comment.setText(biaoqian.get(i));
        return view;
    }
  class VieHolder{
      TextView tv_comment;
    }
}
