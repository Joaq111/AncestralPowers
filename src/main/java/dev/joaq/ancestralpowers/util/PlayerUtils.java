package dev.joaq.ancestralpowers.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.Entity;

import java.util.function.Predicate;

public class PlayerUtils {

    public static ServerPlayerEntity getPlayerLookedAt(ServerPlayerEntity player, double distance) {
        Vec3d eyePos = player.getCameraPosVec(1.0F);
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d targetPos = eyePos.add(lookVec.multiply(distance));

        Box box = player.getBoundingBox().stretch(lookVec.multiply(distance)).expand(1.0D, 1.0D, 1.0D);

        Predicate<Entity> filter = (entity) -> entity instanceof ServerPlayerEntity && entity != player;

        EntityHitResult entityHit = ProjectileUtil.raycast(
                player,
                eyePos,
                targetPos,
                box,
                filter,
                distance * distance
        );

        if (entityHit != null && entityHit.getEntity() instanceof ServerPlayerEntity targetPlayer) {
            return targetPlayer;
        }

        return null;
    }
}
