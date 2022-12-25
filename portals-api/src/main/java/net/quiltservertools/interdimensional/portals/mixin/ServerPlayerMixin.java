package net.quiltservertools.interdimensional.portals.mixin;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.quiltservertools.interdimensional.portals.InterdimensionalPortals;
import net.quiltservertools.interdimensional.portals.interfaces.EntityInCustomPortal;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin implements EntityInCustomPortal {

    @Redirect(method = "moveToWorld", at = @At(value = "INVOKE", target = "net/minecraft/server/world/ServerWorld.getRegistryKey ()Lnet/minecraft/registry/RegistryKey;", ordinal = 0))
    public RegistryKey<World> CPApreventEndCredits(ServerWorld serverWorld) {
        if (this.hasTeleported())
            return RegistryKey.of(RegistryKeys.WORLD, new Identifier(InterdimensionalPortals.MOD_ID, "nullworld"));
        return serverWorld.getRegistryKey();
    }

    @Inject(method = "createEndSpawnPlatform", at = @At("HEAD"), cancellable = true)
    public void CPAcancelEndPlatformSpawn(ServerWorld world, BlockPos centerPos, CallbackInfo ci) {
        if (this.hasTeleported()) ci.cancel();
    }
}
