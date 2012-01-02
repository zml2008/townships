package com.zml.town;

import com.sk89q.regionbook.flags.EnumFlag;
import com.sk89q.regionbook.flags.FlagManager;
import com.sk89q.regionbook.flags.IntegerFlag;

public class TownFlags {
    public final EnumFlag<TownType> TOWN;
    public final IntegerFlag TOWN_MAX_MEMBERS;
    public final IntegerFlag TOWN_RENT;

    public TownFlags(FlagManager manager) {
        TOWN = manager.register(new EnumFlag<TownType>("town", TownType.class));
        TOWN_MAX_MEMBERS = manager.register(new IntegerFlag("town-max-members"));
        TOWN_RENT = manager.register(new IntegerFlag("town-rent"));

    }
}
