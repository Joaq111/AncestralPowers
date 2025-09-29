    package dev.joaq.ancestralpowers.powers.teleport;

    import net.minecraft.entity.player.PlayerEntity;
    import net.minecraft.particle.ParticleTypes;
    import net.minecraft.server.world.ServerWorld;
    import net.minecraft.util.math.Vec3d;
    import net.minecraft.world.World;

    public class TeleportParticles {

        /**
         * Desenha partículas do player até a posição final, passo a passo.
         */
        public static void spawnLookLineParticles(PlayerEntity player, Vec3d startPos, Vec3d endPos, int steps) {
            World world = player.getWorld();
            if (!(world instanceof ServerWorld serverWorld)) return;

            Vec3d diff = endPos.subtract(startPos);
            double stepX = diff.x / steps;
            double stepY = diff.y / steps;
            double stepZ = diff.z / steps;

            for (int i = 0; i <= steps; i++) {
                double x = startPos.x + stepX * i;
                double y = startPos.y + stepY * i;
                double z = startPos.z + stepZ * i;

                serverWorld.spawnParticles(
                        ParticleTypes.PORTAL,
                        x, y, z,
                        1, // quantidade
                        0, 0, 0, // spread
                        0 // velocidade
                );
            }
        }

        /**
         * Chamando apenas para o ponto final.
         */
        public static void spawnTargetParticle(PlayerEntity player, Vec3d targetPos) {
            World world = player.getWorld();
            if (!(world instanceof ServerWorld serverWorld)) return;

            serverWorld.spawnParticles(
                    ParticleTypes.PORTAL,
                    targetPos.x,
                    targetPos.y,
                    targetPos.z,
                    10,
                    0.1, 0.1, 0.1,
                    0.01
            );
        }
    }
