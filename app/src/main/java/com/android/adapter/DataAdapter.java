package com.android.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.BR;
import com.android.R;
import com.android.databinding.ListDataBinding;
import com.android.model.object.UserDataModel;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;

    private AdapterView.OnItemClickListener onItemClickListener;
    private OnUserDataClickListener listener;
    private ArrayList<UserDataModel> list_data;
    private ArrayList<UserDataModel> list_filter;

    public DataAdapter(Activity mActivity, OnUserDataClickListener listener) {
        this.mActivity = mActivity;
        this.listener = listener;
    }

    public void doRefresh(ArrayList<UserDataModel> list_data) {
        this.list_data = list_data;
        this.list_filter = list_data;
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
        ListDataBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutBinding, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserDataModel item = list_filter.get(position);
        try {
            holder.setData(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_filter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    list_filter = list_data;
                } else {
                    ArrayList<UserDataModel> filteredList = new ArrayList<>();
                    for (UserDataModel object : list_data) {
                        if (object.getLogin().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(object);
                        }
                    }
                    list_filter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list_filter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list_filter = (ArrayList<UserDataModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnUserDataClickListener {
        void onCallClickListener(UserDataModel object);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListDataBinding layoutBinding;
        private DataAdapter mAdapter;

        public ViewHolder(ListDataBinding layoutBinding, final DataAdapter mAdapter) {
            super(layoutBinding.getRoot());
            this.layoutBinding = layoutBinding;
            this.mAdapter = mAdapter;
            layoutBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setData(final UserDataModel object) throws Exception {
            layoutBinding.setVariable(BR.model, object);
            layoutBinding.executePendingBindings();
        }
    }
}