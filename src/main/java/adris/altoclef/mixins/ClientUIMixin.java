package adris.altoclef.mixins;

import adris.altoclef.StaticMixinHookups;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public final class ClientUIMixin {
    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void clientRender(MatrixStack stack, float tickDelta, CallbackInfo ci) {
        StaticMixinHookups.onClientRenderOverlay(stack);
    }
}
