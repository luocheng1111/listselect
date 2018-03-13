package com.demo.library.demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.demo.library.listsellibrary.FragmentContentActivity;
import com.demo.library.listsellibrary.fragment.SelListFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainActivity extends Activity {

    ArrayList<String> strList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initStrListData();
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn1:
                FragmentContentActivity.startForResultActivity(this, true, "请选择名称", strList);
                break;
            case R.id.btn2:
                FragmentContentActivity.startForResultActivity(this, false, "请选择名称", strList);
                break;
            case R.id.btn3:
//                SelListActivity.startForResultActivity(this, strList);
                break;
            default:

        }

    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==111){
            int pos = data.getIntExtra("initPos", -1);
            Toast.makeText(MainActivity.this, strList.get(pos), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<String> initStrListData(){
        strList.clear();
        strList.add("北京");
        strList.add("上海");
        strList.add("广州");
        strList.add("深圳");
        strList.add("西安");
        strList.add("成都");
        strList.add("南京");
        strList.add("三亚");
        strList.add("开封");
        strList.add("杭州");
        strList.add("嘉兴");
        strList.add("兰州");
        strList.add("新疆");
        strList.add("西藏");
        strList.add("昆明");
        strList.add("大理");
        strList.add("桂林");
        strList.add("东莞");
        strList.add("台湾");
        strList.add("香港");
        strList.add("澳门");
        strList.add("宝鸡");
        strList.add("蚌埠");
        strList.add("钓鱼岛");
        strList.add("安康");
        strList.add("苏州");
        strList.add("青岛");
        strList.add("郑州");
        strList.add("洛阳");
        strList.add("石家庄");
        strList.add("乌鲁木齐");
        strList.add("武汉");
        strList.add("←_←");
        strList.add("⊙﹏⊙");
        strList.add("Hello China4");
        strList.add("Hello China1");
        strList.add("Hello China3");
        strList.add("Hello China2");
        strList.add("宁波");
        return strList;
    }
}

