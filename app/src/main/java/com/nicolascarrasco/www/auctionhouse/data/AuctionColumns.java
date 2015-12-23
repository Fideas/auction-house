package com.nicolascarrasco.www.auctionhouse.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Nicolás Carrasco on 21/12/2015.
 */
public interface AuctionColumns {

    // TODO: Implement a way to store current and past bidders
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String AUCTION_OWNER = "auction_owner";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String PRODUCT_NAME = "product_name";

    @DataType(DataType.Type.TEXT)
    String DESCRIPTION = "description";

    @DataType(DataType.Type.INTEGER)
    @DefaultValue("0")
    String PRICE = "price";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String EXPIRATION_DATE = "expiration_date";

    @DataType(DataType.Type.TEXT)
    @DefaultValue("None")
    String CURRENT_BIDDER = "current_bidder";

    @DataType(DataType.Type.INTEGER)
    @DefaultValue("1")
    String IS_ACTIVE = "is_active"; //Used as a BOOLEAN
}
