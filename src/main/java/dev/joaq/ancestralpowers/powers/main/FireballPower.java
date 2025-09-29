package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.entiy.CustomFireballEntity;
import dev.joaq.ancestralpowers.powers.Power;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class FireballPower implements Power {
    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);


        if (!activate || traits.getStamina() < 25) {
            traits.setActPower_main(false);
            return;
        }

        Vec3d look = player.getRotationVector();
        double startX = player.getX() + look.x;
        double startY = player.getEyeY() + look.y;
        double startZ = player.getZ() + look.z;

        CustomFireballEntity fireball = new CustomFireballEntity(
                player.getWorld(),
                player,
                look.x * 1.5, look.y * 1.5, look.z * 1.5
        );

        fireball.updatePosition(startX, startY, startZ);
        player.getWorld().spawnEntity(fireball);

        traits.setActPower_main(false);
        traits.setStamina(traits.getStamina() - 25);
    }

    @Override
    public void reset(ServerPlayerEntity player) {

    }
}
