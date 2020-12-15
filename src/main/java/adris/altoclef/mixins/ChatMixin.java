package adris.altoclef.mixins;

import adris.altoclef.AltoClef;
import adris.altoclef.Debug;
import baritone.api.event.events.ChatEvent;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerEntity.class)
public final class ChatMixin {
    @Inject(
            method = "sendChatMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void sendChatMessage(String msg, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(msg);
        AltoClef.getInstance().onChat(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}