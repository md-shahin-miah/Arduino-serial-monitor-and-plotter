package com.ardunioSerial.crux.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardunioSerial.crux.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public List<String> mItemList;


    public RecyclerViewAdapter(List<String> itemList) {

        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_dataincoming_item, parent, false);
            return new ItemViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
//            return new LoadingViewHolder(view);
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
//
//        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
//        } else if (viewHolder instanceof LoadingViewHolder) {
//            showLoadingView((LoadingViewHolder) viewHolder, position);
//        }

    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_item_id);
        }
    }

//    private class LoadingViewHolder extends RecyclerView.ViewHolder {
//
//        ProgressBar progressBar;
//
//        public LoadingViewHolder(@NonNull View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar);
//        }
//    }

//    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
//        //ProgressBar would be displayed
//
//    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {

        String item = mItemList.get(position);
        viewHolder.textView.setText(item);

    }


}
