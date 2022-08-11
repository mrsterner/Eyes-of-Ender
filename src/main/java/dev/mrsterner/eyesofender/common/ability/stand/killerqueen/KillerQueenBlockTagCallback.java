package dev.mrsterner.eyesofender.common.ability.stand.killerqueen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class KillerQueenBlockTagCallback {
    public static ActionResult tagBlock(PlayerEntity player, World world, Hand hand, BlockPos blockPos, Direction direction) {
        /*
        if(StandUtils.getStand(player) == Stand.KILLER_QUEEN && hand == Hand.MAIN_HAND) {
            if(!player.getInventory().contains(new ItemStack(PBObjects.KILLER_QUEEN_TRIGGER))){
                HitResult hitResult = MinecraftClient.getInstance().crosshairTarget;

                float newPosX = pos.getX();
                float newPosY = pos.getY();
                float newPosZ = pos.getZ();

                if (hitResult instanceof BlockHitResult) {
                    direction = ((BlockHitResult) hitResult).getSide();
                    switch (direction) {
                        case UP:
                            newPosY = pos.getY() + 1.0F;
                            break;
                        case DOWN:
                            newPosY = pos.getY() - 1.0F;
                            break;
                        case NORTH:
                            newPosX = pos.getX() + 1.0F;
                            break;
                        case SOUTH:
                            newPosX = pos.getX() - 1.0F;
                            break;
                        case EAST:
                            newPosZ = pos.getZ() + 1.0F;
                            break;
                        case WEST:
                            newPosZ = pos.getZ() - 1.0F;
                            break;
                        default:
                    }
                }

                ItemStack trigger = new ItemStack(PBObjects.KILLER_QUEEN_TRIGGER);
                KillerQueenTriggerItem.setData(trigger, KillerQueenTriggerItem.TYPE.BLOCK.getName(),"empty",newPosX, newPosY, newPosZ);

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
                        KillerQueenTriggerItem.setData(trigger, KillerQueenTriggerItem.TYPE.BLOCK.getName(),"empty",pos.getX(), pos.getY(), pos.getZ());
                        break;
                    }
                }
            }
        }

         */
        return ActionResult.PASS;
    }
}
