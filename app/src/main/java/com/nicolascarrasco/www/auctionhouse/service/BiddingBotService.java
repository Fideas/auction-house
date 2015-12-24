package com.nicolascarrasco.www.auctionhouse.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.nicolascarrasco.www.auctionhouse.data.AuctionColumns;
import com.nicolascarrasco.www.auctionhouse.data.AuctionProvider;

public class BiddingBotService extends IntentService {

    public final static String TAG = BiddingBotService.class.getSimpleName();

    public BiddingBotService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Cursor cursor = getContentResolver().query(AuctionProvider.Auctions.RANDOM,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            long id = cursor.getLong(cursor.getColumnIndex(AuctionColumns._ID));
            float price = cursor.getFloat(cursor.getColumnIndex(AuctionColumns.PRICE));

            ContentValues values = new ContentValues();
            values.put(AuctionColumns.CURRENT_BIDDER, "Bidding Bot");
            values.put(AuctionColumns.PRICE, price + 5.0f);
            getApplicationContext().getContentResolver().update(AuctionProvider.Auctions.withId(id),
                    values,
                    null,
                    null);
        }
        cursor.close();
    }

    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, BiddingBotService.class));
        }
    }
}
