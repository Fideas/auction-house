package com.nicolascarrasco.www.auctionhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nicolascarrasco.www.auctionhouse.DetailActivity;
import com.nicolascarrasco.www.auctionhouse.R;
import com.nicolascarrasco.www.auctionhouse.Utilities;
import com.nicolascarrasco.www.auctionhouse.data.AuctionColumns;

/**
 * Created by Nicolás Carrasco on 23/12/2015.
 */
public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.AuctionViewHolder> {

    private Cursor mCursor;
    private Context mContext;
    private View mEmptyView;

    public AuctionAdapter(Context context, View emptyView){
        mEmptyView = emptyView;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public AuctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_auction, parent, false);
        return new AuctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Could not move cursor to position " + position);
        }
        String title = mCursor.getString(mCursor.getColumnIndex(AuctionColumns.PRODUCT_NAME));
        String price = mCursor.getString(mCursor.getColumnIndex(AuctionColumns.PRICE));
        String description = mCursor.getString(mCursor.getColumnIndex(AuctionColumns.DESCRIPTION));
        float date = mCursor.getFloat(mCursor.getColumnIndex(AuctionColumns.EXPIRATION_DATE));

        holder.mTitleView.setText(title);
        holder.mPriceView.setText(Utilities.formatPrice(price));
        holder.mDescriptionView.setText(description);
        holder.mDateView.setText(Utilities.formatDate(mContext, date));

    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void swapCursor(Cursor newCursor) {
        this.mCursor = newCursor;
        notifyDataSetChanged();
        if(getItemCount() == 0){
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    public class AuctionViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public final TextView mTitleView;
        public final TextView mPriceView;
        public final TextView mDescriptionView;
        public final TextView mDateView;

        public AuctionViewHolder(View view) {
            super(view);
            mTitleView = (TextView) view.findViewById(R.id.list_item_title_text);
            mPriceView = (TextView) view.findViewById(R.id.list_item_price_text);
            mDescriptionView = (TextView) view.findViewById(R.id.list_item_description_text);
            mDateView = (TextView) view.findViewById(R.id.list_item_date_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mCursor.moveToPosition(getAdapterPosition());
            int id = mCursor.getInt(mCursor.getColumnIndex(AuctionColumns._ID));
            mContext.startActivity(new Intent(mContext, DetailActivity.class));
        }
    }
}
