package com.nicolascarrasco.www.auctionhouse.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Nicolás Carrasco on 22/12/2015.
 */
@ContentProvider(authority = AuctionProvider.AUTHORITY,
        database = AuctionDatabase.class,
        packageName = "com.nicolascarrasco.www.auctionhouse.provider")
public final class AuctionProvider {
    public static final String AUTHORITY =
            "com.nicolascarrasco.www.auctionhouse.data.AuctionProvider";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String AUCTIONS = "auctions";
    }

    @TableEndpoint(table = AuctionDatabase.AUCTIONS)
    public static class Auctions {

        //Uri to match all auctions
        @ContentUri(
                path = Path.AUCTIONS,
                type = "vnd.android.cursor.dir/auction")
        public static final Uri CONTENT_URI = buildUri(Path.AUCTIONS);

        //Uri to match a random row
        @ContentUri(path = Path.AUCTIONS + "/random",
                type = "vnd.android.cursor.item/auction",
                defaultSort = "RANDOM()",
                limit = "1")
        public static final Uri RANDOM = buildUri(Path.AUCTIONS, "random");

        //Uri to match auctions the user owns/has published
        @InexactContentUri(
                name = "AUCTIONS_OWNER",
                path = Path.AUCTIONS + "/owner/*",
                type = "vnd.android.cursor.dir/auction",
                whereColumn = AuctionColumns.AUCTION_OWNER,
                pathSegment = 2)
        public static Uri withOwner(String owner) {
            return buildUri(Path.AUCTIONS, "owner", owner);
        }

        //Uri to match auctions where the user has the highest bid
        @InexactContentUri(
                name = "AUCTIONS_BIDDING",
                path = Path.AUCTIONS + "/bid/*",
                type = "vnd.android.cursor.dir/auction",
                whereColumn = AuctionColumns.CURRENT_BIDDER,
                pathSegment = 2)
        public static Uri withBidder(String bidder) {
            return buildUri(Path.AUCTIONS, "bid", bidder);
        }

        //Uri to match a single auction by id
        @InexactContentUri(
                name = "AUCTION_ID",
                path = Path.AUCTIONS + "/#",
                type = "vnd.android.cursor.item/auction",
                whereColumn = AuctionColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.AUCTIONS, String.valueOf(id));
        }

        //TODO: Implement the Uri to match where the user has put a bid in the past
    }
}
