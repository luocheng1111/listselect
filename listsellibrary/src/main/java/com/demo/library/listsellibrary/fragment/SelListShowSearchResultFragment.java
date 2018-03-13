package com.demo.library.listsellibrary.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.library.listsellibrary.R;
import com.demo.library.listsellibrary.adapter.DivideItemDecoration;
import com.demo.library.listsellibrary.adapter.SelListShowAdapter;
import com.demo.library.listsellibrary.bean.SelListBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class SelListShowSearchResultFragment extends Fragment implements SelListShowAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    TextView tvSearchhint;

    String category;
    final List<SelListBean> allData = new ArrayList<>();

    final List<SelListBean> datas = new ArrayList<>();
    SelListShowAdapter adapter;


    public static SelListShowSearchResultFragment newInstance(List<SelListBean> datas) {
        SelListShowSearchResultFragment fragment = new SelListShowSearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("datas", (Serializable) datas);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            allData.addAll((List<SelListBean>) bundle.getSerializable("datas"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sellist_searchresult_fragment, null, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    public void initView(View view) {

        recyclerView = view.findViewById(R.id.list);
        tvSearchhint = view.findViewById(R.id.tv_searchhint);


//		LiveListFragment liveListFragment = LiveListFragment.newInstance(category, false);
//
//		//替换
//		getFragmentManager().beginTransaction().replace(R.id.fl_live, liveListFragment).commit();
        Log.e("1123123", "initView: ");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter = new SelListShowAdapter(getActivity(), datas));
        recyclerView.addItemDecoration(new DivideItemDecoration());
        adapter.setOnItemClickListener(this);

    }

    public void setPrepareSearchState(){
        recyclerView.setVisibility(View.GONE);
        tvSearchhint.setVisibility(View.VISIBLE);
        tvSearchhint.setText("正在搜索中....");
    }

    /**
     * 模糊查询
     *
     * @param str
     * @return
     */
    public void search(String str) {
        Log.e("12321", "search: " + str);
        List<SelListBean> filterList = new ArrayList<SelListBean>();// 过滤后的list
        //if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (SelListBean contact : allData) {
                if (contact.getTitle().contains(simpleStr)) {
                    if (!filterList.contains(contact)) {
                        filterList.add(contact);
                    }
                }
            }
        } else {
            for (SelListBean contact : allData) {
                if (contact.getTitle().toLowerCase().contains(str.toLowerCase())) {
                    if (!filterList.contains(contact)) {
                        filterList.add(contact);
                    }
                }
            }
        }

        for (SelListBean aa : filterList) {
            Log.e("12321", "search: " + aa.getTitle());
        }
        datas.clear();
        datas.addAll(filterList);
        adapter.notifyDataSetChanged();

        if(datas.size()==0){
            tvSearchhint.setText("没有找到, 请换个试试");
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            tvSearchhint.setVisibility(View.GONE);
        }


    }


    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent();
        i.putExtra("initPos", datas.get(position).getInitPos());
        i.putExtra("result", datas.get(position).getTitle());
        // 设置结果，并进行传送
        getActivity().setResult(111, i);
        getActivity().finish();

    }
}

