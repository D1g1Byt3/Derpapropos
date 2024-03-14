package net.d1g1byt3.derpapropos.item.custom;

import net.d1g1byt3.derpapropos.util.ModTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Vanishable;

public class PaxelItem extends DiggerItem implements Vanishable {
    public PaxelItem( Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier,  Properties pProperties) {
        super( pAttackDamageModifier, pAttackSpeedModifier, pTier,  ModTags.Blocks.PAXEL_MINEABLE, pProperties);
    }

}
