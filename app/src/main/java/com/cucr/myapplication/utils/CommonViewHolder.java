package com.cucr.myapplication.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

/**
 * 会用即可
 *
 * 核心思路
 * 1 用map集合来代替多个成员变量
 * 2 构造传入条目布局，就可以自己findViewById，不需要外界进行put操作了
 * 3 提供便捷方法 获取TextView 和ImageView
 * 4 利用泛型增强 扩展性
 * 5 提供静态方法createCVH ，可以让外界在使用的时候免去对convertView的判断
 * 6 对于空指针异常的处理，是因为java基础中，传递的参数都是堆内存的引用，而不是变量的引用。
 *   在静态方法中修改的convertView的引用，并不会反应到Adapter的getView方法中。
 *
 */

public class CommonViewHolder {
    HashMap<Integer,View> mViews =new HashMap<>();
    public final View convertView;

    public static CommonViewHolder createCVH(View convertView , Context context, int itemLayout, ViewGroup Root){
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(itemLayout,Root);
            convertView.setTag( new CommonViewHolder(convertView));
        }
        return (CommonViewHolder) convertView.getTag();
    }


    public CommonViewHolder(View convertView) {
        this.convertView = convertView;
    }
//    public void putView(int id ,View v){
//        mViews.put(id,v);
//    }

    private View getV(int id){
        if(mViews.get(id) ==null){
            mViews.put(id, convertView.findViewById(id));
        }

        return mViews.get(id);
//        return mViews.get(id);
    }

//    便捷方法
    public TextView getTv(int id){
        return (TextView) getV(id);
    }
    public ImageView getIv(int id){
        return (ImageView) getV(id);
    }

//    利用泛型，如果外面接受这个方法的返回值的时候， 等号前面是什么类型就转换成什么类型
    public <T extends View> T getView(int id){
        return (T)getV(id);
    }
//    利用泛型，如果参数中传入什么类型的字节码，就强转成什么类型
    public <T extends View> T getView(int id ,Class<T> klass){
        return (T)getV(id);
    }
}
