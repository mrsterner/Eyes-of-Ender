package dev.mrsterner.eyesofender.common.utils;

import dev.mrsterner.eyesofender.EyesOfEnder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.StructureWorldAccess;

import java.util.Optional;


public class WorldGenUtils {
    /** This method places and nbt structure at a given origin
     * @author - MrSterner
     * @param nbtLocation the location of the structure file about to be placed
     * @param world the structures world access
     * @param origin the blockpos of the structure corner
     * @param chance the probability of a successful placement
     * @return true if the placement of the structure was successfully
     */
    public static boolean generateNbtFeature(Identifier nbtLocation, StructureWorldAccess world, BlockPos origin, float chance){
        StructureTemplateManager templateManager = world.getServer().getStructureManager();
        //Try fetch the nbt with the structure manager
        Optional<Structure> structureOptional = templateManager.getStructure(nbtLocation);
        if (structureOptional.isEmpty()) {
            EyesOfEnder.LOGGER.info("NBT " + nbtLocation + " does not exist!");
        }else if(world.getRandom().nextFloat() < (1.0F / chance)){
            //Unless structureOptional.isEmpty() not catches, get the structure from the optional
            Structure structure = structureOptional.get();
            //Change the origin from the corner of the structure to the middle of the structure
            BlockPos normalizeOrigin = origin.subtract(new Vec3i(Math.floor((double) structure.getSize().getX() / 2), 0, Math.floor((double) structure.getSize().getX() / 2)));
            //Get basic placementData
            StructurePlacementData placementData = (new StructurePlacementData()).setMirror(BlockMirror.NONE).setRotation(BlockRotation.NONE).setIgnoreEntities(false);
            //Place the structure at the normalized origin
            structure.place(world, normalizeOrigin, normalizeOrigin, placementData, world.getRandom(), 2);
            return true;
        }
        return false;
    }
}