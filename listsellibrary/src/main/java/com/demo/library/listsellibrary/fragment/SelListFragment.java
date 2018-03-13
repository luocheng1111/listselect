package com.demo.library.listsellibrary.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.demo.library.listsellibrary.FragmentContentActivity;
import com.demo.library.listsellibrary.R;
import com.demo.library.listsellibrary.adapter.SelListShowAdapter;
import com.demo.library.listsellibrary.bean.SelListBean;
import com.demo.library.listsellibrary.utils.StringSortUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class SelListFragment extends Fragment implements View.OnClickListener{

	Toolbar toolbar;
	TextView tvTitle;
	EditText etSearch;
	ImageView clear;
	FrameLayout layoutContainer;
	FrameLayout fl;

	SelListShowAdapter adapter;
	SelListShowSearchResultFragment searchResultFragment;
	SelListShowListFragment selectListFragment;

//
List<String> datas = new ArrayList<>();
	String pageTitle =" ";
	boolean isShowTitle = false;

	public static SelListFragment newInstance(ArrayList<String> datas) {
		SelListFragment fragment = new SelListFragment();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("datas", datas);
		fragment.setArguments(bundle);
		return fragment;
	}

	public static SelListFragment newInstance(ArrayList<String> datas, boolean isShowTitle, String pageTitle) {
		SelListFragment fragment = new SelListFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isShowTitle", isShowTitle);
		bundle.putString("pageTitle", pageTitle);
		bundle.putStringArrayList("datas", datas);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if(bundle != null){
			isShowTitle = bundle.getBoolean("isShowTitle", false);
			pageTitle = bundle.getString("pageTitle", " ");
			datas.addAll(bundle.getStringArrayList("datas"));

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sellist_fragment, null, false);
		ButterKnife.bind(this, view);
		initView(view);
		return view;
	}

	public void initView(View view) {

		toolbar = view.findViewById(R.id.toolbar);
		tvTitle = view.findViewById(R.id.tv_head_title);
		etSearch = view.findViewById(R.id.et_search);
		clear = view.findViewById(R.id.ivClearText);
		clear.setOnClickListener(this);
		layoutContainer = view.findViewById(R.id.layoutContainer);
		fl = view.findViewById(R.id.fl);

		if(isShowTitle){
			toolbar.setVisibility(View.VISIBLE);
			initHead(pageTitle, true);
		}else {
			toolbar.setVisibility(View.GONE);
		}

		List<SelListBean> stringSortBeanList = datas2SortBeanList(datas);

		selectListFragment = SelListShowListFragment.newInstance(stringSortBeanList);
		searchResultFragment = SelListShowSearchResultFragment.newInstance(stringSortBeanList);

		getFragmentManager().beginTransaction().add(R.id.fl, selectListFragment).commit();
		getFragmentManager().beginTransaction().add(R.id.fl, searchResultFragment, "searchResultFragment").commit();
		getFragmentManager().beginTransaction().hide(searchResultFragment).commit();

		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String keyword = s.toString();
				if (TextUtils.isEmpty(keyword)) {
					clear.setVisibility(View.INVISIBLE);
					getFragmentManager().beginTransaction().hide(searchResultFragment).commit();
					getFragmentManager().beginTransaction().show(selectListFragment).commit();
				} else {
					clear.setVisibility(View.VISIBLE);
					getFragmentManager().beginTransaction().show(searchResultFragment).commit();
					getFragmentManager().beginTransaction().hide(selectListFragment).commit();
					searchResultFragment.setPrepareSearchState();
				}
				if (mHandler.hasMessages(MESSAGE_SEARCH)) {
					mHandler.removeMessages(MESSAGE_SEARCH);
				}
				mHandler.sendEmptyMessageDelayed(MESSAGE_SEARCH, INTERVAL);
			}
		});
	}

	public void initHead(String title, boolean isShowBack) {
		((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
		//把返回键改成自己要的样式 默认返回键颜色为白色
		((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.sellist_head_back_sel);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				getActivity().finish();
			}
		});

		tvTitle.setVisibility(View.VISIBLE);
		tvTitle.setText(title);
		if (isShowBack) {
			((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}
	}


	private static final int MESSAGE_SEARCH = 0x1;
	private static long INTERVAL = 300; // 输入变化时间间隔

	Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MESSAGE_SEARCH){
//                mTextView.setText("搜索key:" + mEditText.getText().toString());
				searchResultFragment.search(etSearch.getText().toString());
			}
		}
	};


	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		FragmentContentActivity.OnHideKeyboardListener onHideKeyboardListener = new FragmentContentActivity.OnHideKeyboardListener() {
			@Override
			public boolean hideKeyboard() {
				// TODO Auto-generated method stub
				if(imm.isActive(etSearch)){
					getView().requestFocus();
					imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					return true;
				}
				return false;
			}
		};
		((FragmentContentActivity)getActivity()).setOnHideKeyboardListener(onHideKeyboardListener);
		super.onAttach(activity);
	}

	@Override
	public void onClick(View v) {
		etSearch.setText("");
	}

	//	@OnClick({R.id.ivClearText})
//	public void onClick(View view) {
//		switch (view.getId()) {
//			case R.id.ivClearText:
//				Log.e("12321", "search:111111121 ");
//				etSearch.setText("");
//				break;
//		}
//	}


	//将List<String>进行排序并转换为List<SelListBean>
	public List<SelListBean> datas2SortBeanList(List<String> datas) {
		List<SelListBean> stringSortBeanList = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			stringSortBeanList.add(new SelListBean(datas.get(i), i));
		}
		new StringSortUtil().sortByLetter(stringSortBeanList);
		return  stringSortBeanList;

	}
}

