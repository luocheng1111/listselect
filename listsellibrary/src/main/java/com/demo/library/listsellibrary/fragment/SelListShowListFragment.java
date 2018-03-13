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
import com.demo.library.listsellibrary.adapter.GroupHeaderItemDecoration;
import com.demo.library.listsellibrary.adapter.SelListShowAdapter;
import com.demo.library.listsellibrary.bean.SelListBean;
import com.demo.library.listsellibrary.view.SideBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class SelListShowListFragment extends Fragment implements SelListShowAdapter.OnItemClickListener {

	RecyclerView recyclerView;
	SideBar sideBar;
	TextView tip;

	final List<SelListBean> datas = new ArrayList<>();
	SelListShowAdapter adapter;

	public static SelListShowListFragment newInstance(List<SelListBean> datas) {
		SelListShowListFragment fragment = new SelListShowListFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("datas", (Serializable) datas);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getArguments();
		if(bundle != null){
			datas.addAll((List<SelListBean>)bundle.getSerializable("datas"));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sellist_showlist_fragment, null, false);
		ButterKnife.bind(this, view);
		initView(view);
		return view;
	}

	public void initView(View view) {

		recyclerView = view.findViewById(R.id.list);
		sideBar = view.findViewById(R.id.side_bar);
		tip = view.findViewById(R.id.tip);

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new SelListShowAdapter(getActivity(), datas);

		//按照字母顺序排列String
//		new StringSortUtil().sortByLetter(datas);
		List<String> tags = new ArrayList<>();
		for (SelListBean data111111 : datas) {
			tags.add(data111111.getTheFisrtPinYin());
			Log.e("222", "getTitle: " + data111111.getTitle());
			Log.e("222", "getTitle: " + data111111.getTheFisrtPinYin());
		}

		Log.e("2221", "getTitle: " + tags.size());
		sideBar.setDataSource(tags);
		Log.e("2222", "getTitle: " + tags.size());
		adapter = new SelListShowAdapter(getActivity(), datas);
		final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);

		recyclerView.addItemDecoration(new GroupHeaderItemDecoration(getActivity())
						.setTags(tags)
						.setGroupHeaderHeight(34)
						.setGroupHeaderLeftPadding(16)
//                .setOnDrawItemDecorationListener(new OnDrawItemDecorationListener() {
//                    @Override
//                    public void onDrawGroupHeader(Canvas c, Paint paint, TextPaint textPaint, int[] params, int position) {
//                        c.drawRect(params[0], params[1], params[2], params[3], paint);
//
//                        int x = params[0] + Utils.dip2px(context, 20);
//                        int y = params[1] + (Utils.dip2px(context, 30) + Utils.getTextHeight(textPaint, tags.get(position))) / 2;
//
//                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, null);
//                        Bitmap icon1 = Bitmap.createScaledBitmap(icon, Utils.dip2px(context, 20), Utils.dip2px(context, 20), true);
//                        c.drawBitmap(icon1, x, params[1] + Utils.dip2px(context, 5), paint);
//
//                        c.drawText(tags.get(position), x + Utils.dip2px(context, 25), y, textPaint);
//                    }
//
//                    @Override
//                    public void onDrawSuspensionGroupHeader(Canvas c, Paint paint, TextPaint textPaint, int[] params, int position) {
//                        c.drawRect(params[0], params[1], params[2], params[3], paint);
//                        int x = params[0] + Utils.dip2px(context, 20);
//                        int y = params[1] + (Utils.dip2px(context, 30) + Utils.getTextHeight(textPaint, tags.get(position))) / 2;
//
//                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, null);
//                        Bitmap icon1 = Bitmap.createScaledBitmap(icon, Utils.dip2px(context, 20), Utils.dip2px(context, 20), true);
//                        c.drawBitmap(icon1, x, params[1] + Utils.dip2px(context, 5), paint);
//
//                        c.drawText(tags.get(position), x + Utils.dip2px(context, 25), y, textPaint);
//                    }
//                })
		);
		recyclerView.addItemDecoration(new DivideItemDecoration().setTags(tags));
		recyclerView.setAdapter(adapter);

		sideBar.setOnSideBarTouchListener(tags, new SideBar.OnSideBarTouchListener() {
			@Override
			public void onTouch(String text, int position) {
				tip.setVisibility(View.VISIBLE);
				tip.setText(text);
				if ("↑".equals(text)) {
					layoutManager.scrollToPositionWithOffset(0, 0);
					return;
				}
				if (position != -1) {
					layoutManager.scrollToPositionWithOffset(position, 0);
				}
			}

			@Override
			public void onTouchEnd() {
				tip.setVisibility(View.GONE);
			}
		});
		adapter.setOnItemClickListener(this);
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

