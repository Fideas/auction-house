package com.nicolascarrasco.www.auctionhouse;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nicolascarrasco.www.auctionhouse.data.AuctionColumns;
import com.nicolascarrasco.www.auctionhouse.data.AuctionProvider;

public class CreateAuctionActivity extends AppCompatActivity {

    public static final String LOG_TAG = CreateAuctionActivity.class.getSimpleName();
    private TextView mTitleView;
    private TextView mPriceView;
    private TextView mDateView;
    private TextView mDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mTitleView = (TextView) findViewById(R.id.auction_title_text);
        mPriceView = (TextView) findViewById(R.id.price_text);
        mDateView = (TextView) findViewById(R.id.date_text);
        mDescriptionView = (TextView) findViewById(R.id.description_text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptInsertAuction();
            }
        });
    }

    private void attemptInsertAuction() {
        boolean error = false;

        mTitleView.setError(null);
        mPriceView.setError(null);
        mDateView.setError(null);

        String title = mTitleView.getText().toString();
        String price = mPriceView.getText().toString();
        String date = mDateView.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(title)) {
            mTitleView.setError(getString(R.string.error_field_required));
            focusView = mTitleView;
            error = true;
        }
        if (!TextUtils.isEmpty(price) && !Utilities.isValidPrice(price)) {
            mPriceView.setError(getString(R.string.error_invalid_price));
            focusView = mPriceView;
            error = true;
        }
        if (TextUtils.isEmpty(date) || !Utilities.isValidDate(date)) {
            mDateView.setError(getString(R.string.error_invalid_date));
            focusView = mDateView;
            error = true;
        }

        if (error) {
            focusView.requestFocus();
        } else {
            insertAuction();
        }
    }

    private void insertAuction() {
        ContentValues values = new ContentValues();
        //TODO: Replace hardcoded user
        values.put(AuctionColumns.AUCTION_OWNER, "foo@example.com");
        values.put(AuctionColumns.PRODUCT_NAME, mTitleView.getText().toString());
        values.put(AuctionColumns.DESCRIPTION, mDescriptionView.getText().toString());
        //Add the price only if its not empty, otherwise insert the default value
        if (!TextUtils.isEmpty(mPriceView.getText())) {
            values.put(AuctionColumns.PRICE, mPriceView.getText().toString());
        }
        values.put(AuctionColumns.EXPIRATION_DATE, Utilities.dateStringToMillis(
                mDateView.getText().toString(),
                LOG_TAG
        ));

        Uri insertUri = getApplicationContext()
                .getContentResolver()
                .insert(AuctionProvider.Auctions.CONTENT_URI, values);

        Log.i(LOG_TAG, insertUri.toString());
    }
}
