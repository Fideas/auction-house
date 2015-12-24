package com.nicolascarrasco.www.auctionhouse;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.nicolascarrasco.www.auctionhouse.data.AuctionColumns;
import com.nicolascarrasco.www.auctionhouse.data.AuctionProvider;

public class DetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private final static long DEFAULT_ID = -1;
    private static final int DETAIL_LOADER = 0;
    private long mId;
    private float mPrice;

    private TextView mTitleView;
    private TextView mPriceView;
    private TextView mDateView;
    private TextView mDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mId = getIntent().getLongExtra(Utilities.AUCTION_ID_KEY, DEFAULT_ID);
        getSupportLoaderManager().initLoader(DETAIL_LOADER, null, this);

        mTitleView = (TextView) findViewById(R.id.detail_title_text);
        mPriceView = (TextView) findViewById(R.id.detail_price_text);
        mDateView = (TextView) findViewById(R.id.detail_date_text);
        mDescriptionView = (TextView) findViewById(R.id.detail_description_text);

        findViewById(R.id.detail_bid_button).setOnClickListener(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                AuctionProvider.Auctions.withId(mId),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        String title = data.getString(data.getColumnIndex(AuctionColumns.PRODUCT_NAME));
        mPrice = data.getFloat(data.getColumnIndex(AuctionColumns.PRICE));
        float date = data.getFloat(data.getColumnIndex(AuctionColumns.EXPIRATION_DATE));
        String description = data.getString(data.getColumnIndex(AuctionColumns.DESCRIPTION));

        mTitleView.setText(title);
        mPriceView.setText(Utilities.formatPrice(mPrice));
        mDateView.setText(Utilities.formatDate(this, date));
        mDescriptionView.setText(description);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        ContentValues values = new ContentValues();
        //When bidding we will just add 5 dollars to the actual price
        values.put(AuctionColumns.PRICE, mPrice + 5.0f);
        //TODO: Change hardcoded user
        values.put(AuctionColumns.CURRENT_BIDDER, "foo@example.com");
        getApplicationContext().getContentResolver().update(AuctionProvider.Auctions.withId(mId),
                values,
                null,
                null);
    }
}
