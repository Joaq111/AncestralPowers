package dev.joaq.ancestralpowers.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeyBinds {
    public static KeyBinding G_KEY;
    public static KeyBinding R_KEY;

    public static void registerKeyBinds() {
        G_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.ancestralpowers.toggle_g",
                        InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.ancestralpowers")
        );

        R_KEY = KeyBindingHelper.registerKeyBinding(
                new KeyBinding("key.ancestralpowers.toggle_r",
                        InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "key.categories.ancestralpowers")
        );
    }


}
