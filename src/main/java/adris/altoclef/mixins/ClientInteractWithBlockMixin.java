package adris.altoclef.mixins;

// ActionResult ClientPlayerInteractionManager.interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult);

import adris.altoclef.AltoClef;
import adris.altoclef.Debug;
import adris.altoclef.StaticMixinHookups;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public final class ClientInteractWithBlockMixin {
    @Inject(
            method = "interactBlock",
            at = @At("HEAD")
    )
    private void onClientBlockInteract(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> ci) {
        //Debug.logMessage("(client) INTERACTED WITH: " + (hitResult != null? hitResult.getBlockPos() : "(nothing)"));
        if (hitResult != null) {
            StaticMixinHookups.onBlockInteract(hitResult, world.getBlockState(hitResult.getBlockPos()));
        }

    }
}
