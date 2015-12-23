package com.nicolascarrasco.www.auctionhouse.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Nicolás Carrasco on 22/12/2015.
 */
@Database(version = AuctionDatabase.VERSION,
        packageName = "com.nicolascarrasco.www.auctionhouse.provider")
public final class AuctionDatabase {

    public static final int VERSION = 1;

    @Table(AuctionColumns.class) public static final String AUCTIONS = "auctions";
}
