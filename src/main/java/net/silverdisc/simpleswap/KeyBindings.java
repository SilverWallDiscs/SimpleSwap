package net.silverdisc.simpleswap;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static KeyBinding swapKey;

    public static void register() {
        swapKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.simpleswap.swap",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V, // Default key is 'V'
                "category.simpleswap"
        ));
    }
}
