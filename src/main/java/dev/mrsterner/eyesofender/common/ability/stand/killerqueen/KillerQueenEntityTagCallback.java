package dev.mrsterner.eyesofender.common.ability.stand.killerqueen;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Deprecated(forRemoval = true)
public class KillerQueenEntityTagCallback {

    public static ActionResult tagEntity(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        /*
        if(StandUtils.getStand(player) == Stand.KILLER_QUEEN && hand == Hand.MAIN_HAND){
            if(!player.getInventory().contains(new ItemStack(PBObjects.KILLER_QUEEN_TRIGGER))){
                ItemStack trigger = new ItemStack(PBObjects.KILLER_QUEEN_TRIGGER);
                KillerQueenTriggerItem.setData(trigger, KillerQueenTriggerItem.TYPE.ENTITY.getName(), entity.getUuid().toString(), 0, 0, 0);
                if(player.getStackInHand(Hand.MAIN_HAND).isEmpty()){
                    player.setStackInHand(Hand.MAIN_HAND, trigger);
                }else{
                    player.dropItem(trigger, false, true);
                }
            }else{
                PlayerInventory inventory = player.getInventory();
                List<ItemStack> mainInventory = inventory.main;
                for(ItemStack trigger : mainInventory) {
                    if(trigger.getItem() == PBObjects.KILLER_QUEEN_TRIGGER) {
                        KillerQueenTriggerItem.setData(trigger, KillerQueenTriggerItem.TYPE.ENTITY.getName(), entity.getUuid().toString(), 0, 0, 0);
                        break;
                    }
                }
            }
        }
         */
        return ActionResult.PASS;
    }
}
