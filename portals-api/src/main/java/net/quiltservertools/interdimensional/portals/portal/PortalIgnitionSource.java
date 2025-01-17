package net.quiltservertools.interdimensional.portals.portal;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.function.BiFunction;

public class PortalIgnitionSource {
    public final static PortalIgnitionSource FIRE = new PortalIgnitionSource(SourceType.BLOCKPLACED, Registries.BLOCK.getId(Blocks.FIRE));
    public final static PortalIgnitionSource WATER = FluidSource(Fluids.WATER);

    public enum SourceType {
        USEITEM, BLOCKPLACED, FLUID, CUSTOM
    }

    private static final HashSet<Item> USEITEMS = new HashSet<>();
    public SourceType sourceType;
    public Identifier ignitionSourceID;

    private PortalIgnitionSource(SourceType sourceType, Identifier ignitionSourceID) {
        this.sourceType = sourceType;
        this.ignitionSourceID = ignitionSourceID;
    }

    public static PortalIgnitionSource ItemUseSource(Item item) {
        USEITEMS.add(item);
        return new PortalIgnitionSource(SourceType.USEITEM, Registries.ITEM.getId(item));
    }

    public static PortalIgnitionSource FluidSource(Fluid fluid) {
        return new PortalIgnitionSource(SourceType.FLUID, Registries.FLUID.getId(fluid));
    }

    public static PortalIgnitionSource CustomSource(Identifier ignitionSourceID) {
        return new PortalIgnitionSource(SourceType.CUSTOM, ignitionSourceID);
    }

    // TODO: implement
    @Deprecated
    public void withCondition(BiFunction<World, BlockPos, Boolean> condition) {

    }

    public boolean isWater() {
        return Registries.FLUID.getEntry(RegistryKey.of(Registries.FLUID.getKey(), ignitionSourceID)).get().isIn(FluidTags.WATER);
    }

    public boolean isLava() {
        return Registries.FLUID.getEntry(RegistryKey.of(Registries.FLUID.getKey(), ignitionSourceID)).get().isIn(FluidTags.LAVA);
    }

    public static boolean isRegisteredIgnitionSourceWith(Item item) {
        return USEITEMS.contains(item);
    }
}
