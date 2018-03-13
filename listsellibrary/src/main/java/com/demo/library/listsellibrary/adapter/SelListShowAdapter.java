package com.demo.library.listsellibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.demo.library.listsellibrary.R;
import com.demo.library.listsellibrary.bean.SelListBean;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/9.
 */

public class SelListShowAdapter extends RecyclerView.Adapter<SelListShowAdapter.ViewHolder> {


    private Context context;
    private List<SelListBean> data;

    public SelListShowAdapter(Context context, List<SelListBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sellist_recycle_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }

        SelListBean ddd = data.get(position);
        Log.e("12321ada", "search: "+ddd.getTitle());
        holder.title.setText(ddd.getTitle());

    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        return count;
    }

    //自定义Holder类,并通过findViewById绑定控件
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            ButterKnife.bind(this, view);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
