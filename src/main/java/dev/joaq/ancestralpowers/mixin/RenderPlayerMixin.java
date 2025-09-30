package dev.joaq.ancestralpowers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import static java.awt.Color.*;

//public class RenderPlayerMixin {
//
//    @Override
//    public void render(AbstractClientPlayerEntity player, float yaw, float tickDelta, MatrixStack matrices,
//                       VertexConsumerProvider vertexConsumers, int light) {
//        PlayerTraits traits = MyComponents.TRAITS.get(player);
//
//        // Valores controlados pelo servidor via componente
////        float red   = traits.getRed();   // 0.0 a 1.0
////        float green = traits.getGreen(); // 0.0 a 1.0
////        float blue  = traits.getBlue();  // 0.0 a 1.0
////        float alpha = traits.getAlpha(); // 0.0 a 1.0
//
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//
//        // 🔥 Aplica cor + transparência
//        RenderSystem.setShaderColor(red, green, blue, alpha);
//
//        super.render(player, yaw, tickDelta, matrices, vertexConsumers, light);
//
//        // Reset para não afetar outros renders
//        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
//        RenderSystem.disableBlend();
//    }
//
//
//}
