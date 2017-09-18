package cinyida.com.car_driver.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Zheng on 2017/8/23.
 */

public class StateTextview extends TextView {
    //1选中 0未选中
    private int state=0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public StateTextview(Context context) {
        super(context);
    }

    public StateTextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateTextview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
