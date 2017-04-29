package com.example.demo.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.demo.R;

/**
 * 描述：自定义一个View，继承了TextView，根据自定义属性去判断并输错个人信息
 *
 * @author RA
 * @blog http://blog.csdn.net/vipzjyno1
 */
public class PersonView extends TextView {
    public PersonView(Context context) {
        super(context);
    }

    public PersonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PersonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.PersonAttr);
        String name = tArray.getString(R.styleable.PersonAttr_name);
        int age = tArray.getInt(R.styleable.PersonAttr_age, 15);
        Boolean adult = tArray.getBoolean(R.styleable.PersonAttr_adult, false);

        // 默认是中等身材，属性为:1
        int weight = tArray.getInt(R.styleable.PersonAttr_weight, 1);

        // 如果你设置为DP等单位，会做像素转换
        float textSize = tArray.getDimension(R.styleable.PersonAttr_textSize,
                getResources().getDimension(R.dimen.default_person_view_text_size));

        String adultStr = getAdultStatus(adult);
        String weightStr = getWeightStatus(weight);

        tArray.recycle();
        setTextSize(textSize);

        String text = String.format("姓名：%s\n年龄：%s\n是否成年：%s\n体形：%s",
                                        name,age,adultStr,weightStr);
        setText(text);
    }

    /**
     * 根据传入的值判断是否成年
     */
    public String getAdultStatus(Boolean adult) {
        String adultStr = "未成年";
        if (adult) {
            adultStr = "成年";
        }
        return adultStr;
    }

    /**
     * 根据传入的值判断肥胖状态
     */
    public String getWeightStatus(int weight) {
        String weightStr = "中等";
        switch (weight) {
            case 0:
                weightStr = "瘦";
                break;
            case 1:
                weightStr = "中等";
                break;
            case 2:
                weightStr = "肥胖";
                break;
            default:
                break;
        }
        return weightStr;
    }
}
