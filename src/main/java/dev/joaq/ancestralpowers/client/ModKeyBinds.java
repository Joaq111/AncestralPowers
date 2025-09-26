package dev.joaq.ancestralpowers.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeyBinds {
    public static KeyBinding POWER_KEY;
    public static KeyBinding POWER_KEY2;


    public static void registerKeyBinds() {
        POWER_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ancestralpowers.use_power", // tradução no lang
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,                // tecla padrão (R)
                "category.ancestralpowers"      // categoria no menu de controles
        ));
        POWER_KEY2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ancestralpowers.use_secondary_power", // tradução no lang
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,                // tecla padrão (G)
                "category.ancestralpowers"      // categoria no menu de controles
        ));
    }

}
