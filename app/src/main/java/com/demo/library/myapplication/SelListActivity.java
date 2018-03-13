package com.demo.library.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.demo.library.listsellibrary.fragment.SelListFragment;

import java.util.ArrayList;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/2/20
 */

public class SelListActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.demo.library.listsellibrary.R.layout.activity_fragmentcontent);

        initView();
    }

    public void initView(){
        ArrayList<String> datas = getIntent().getStringArrayListExtra("datas");
        replaceFragment(SelListFragment.newInstance(datas));

    }


    public static void startForResultActivity(Activity context, ArrayList<String> titleList) {
        Intent i = new Intent(context, SelListActivity.class);
        i.putExtra("fragment_flag", "");
        i.putStringArrayListExtra("datas", titleList);
        context.startActivityForResult(i, 100);
    }

    public void replaceFragment(Fragment fragmnet){
        replaceFragment(com.demo.library.listsellibrary.R.id.fl_,fragmnet);
    }

    public void replaceFragment(int id, Fragment fragment) {
        getFragmentManager().beginTransaction().replace(id, fragment).commit();
    }



    //用于某些子Fragment界面中 点击EditText之外隐藏键盘的功能
    private OnHideKeyboardListener onHideKeyboardListener;
    public interface OnHideKeyboardListener{
        public boolean hideKeyboard();
    }

    public void setOnHideKeyboardListener(OnHideKeyboardListener onHideKeyboardListener){
        this.onHideKeyboardListener = onHideKeyboardListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                onHideKeyboardListener.hideKeyboard();
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
