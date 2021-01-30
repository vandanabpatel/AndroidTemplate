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

import com.android.R;
import com.android.databinding.ListDropdownBinding;
import com.android.model.object.UserDataModel;

import java.util.ArrayList;

public class UserDataAdapter extends RecyclerView.Adapter<UserDataAdapter.ViewHolder> implements Filterable {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;

    private AdapterView.OnItemClickListener onItemClickListener;
    private OnDropdownClickListener listener;
    private ArrayList<UserDataModel> list_data;
    private ArrayList<UserDataModel> list_filter;

    public UserDataAdapter(Activity mActivity, OnDropdownClickListener listener) {
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
        ListDropdownBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_dropdown, parent, false);
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
                        if (object.getType().toLowerCase().contains(charString.toLowerCase())) {
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

    public interface OnDropdownClickListener {
        void onCallClickListener(UserDataModel object);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListDropdownBinding layoutBinding;
        private UserDataAdapter mAdapter;

        public ViewHolder(ListDropdownBinding layoutBinding, final UserDataAdapter mAdapter) {
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
            layoutBinding.tvName.setText(object.getType());

            layoutBinding.llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCallClickListener(object);
                }
            });
        }
    }
}