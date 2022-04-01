package com.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.R;
import com.android.databinding.ListSquareBinding;
import com.android.model.DataModel;

import java.util.ArrayList;

public class SquareAdapter extends RecyclerView.Adapter<SquareAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;

    private AdapterView.OnItemClickListener onItemClickListener;
    private OnDataClickListener listener;
    private ArrayList<DataModel> list_data;

    public SquareAdapter(Activity mActivity, OnDataClickListener listener) {
        this.mActivity = mActivity;
        this.listener = listener;
    }

    public void doRefresh(ArrayList<DataModel> list_data) {
        this.list_data = list_data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(ViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListSquareBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_square, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutBinding, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DataModel item = list_data.get(position);
        try {
            holder.setData(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public interface OnDataClickListener {
        void onItemClickListener(DataModel object);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListSquareBinding layoutBinding;
        private SquareAdapter mAdapter;

        public ViewHolder(ListSquareBinding layoutBinding, final SquareAdapter mAdapter) {
            super(layoutBinding.getRoot());
            this.layoutBinding = layoutBinding;
            this.mAdapter = mAdapter;
            layoutBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setData(final DataModel object) throws Exception {
            if (object.isSelect()) {
                layoutBinding.btnClick.setBackgroundColor(mActivity.getResources().getColor(R.color.blue));
                layoutBinding.btnClick.setEnabled(true);
            } else if (object.isRed()) {
                layoutBinding.btnClick.setBackgroundColor(mActivity.getResources().getColor(R.color.red));
                layoutBinding.btnClick.setEnabled(false);
            } else if (object.isGray()) {
                layoutBinding.btnClick.setBackgroundColor(mActivity.getResources().getColor(R.color.gray));
                layoutBinding.btnClick.setEnabled(false);
            } else {
                layoutBinding.btnClick.setBackgroundColor(mActivity.getResources().getColor(R.color.black));
                layoutBinding.btnClick.setEnabled(false);
            }

            layoutBinding.btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClickListener(object);
                }
            });
        }
    }
}