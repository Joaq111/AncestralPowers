package dev.joaq.ancestralpowers.dimensions;

import net.minecraft.block.Blocks;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;

public class PersonalDimensionStructure {

    // Altura do spawn da dimensão
    private static final int SPAWN_Y = 64;

    // Tamanho da estrutura
    private static final int SIZE_X = 7;
    private static final int SIZE_Z = 7;

    public static void generate(ServerWorld world) {
        for (int x = 0; x < SIZE_X; x++) {
            for (int z = 0; z < SIZE_Z; z++) {
                BlockPos pos = new BlockPos(x, SPAWN_Y, z);
                world.setBlockState(pos, Blocks.STONE.getDefaultState());
            }
        }

        // Criando paredes simples
        for (int x = 0; x < SIZE_X; x++) {
            world.setBlockState(new BlockPos(x, SPAWN_Y + 1, 0), Blocks.COBBLESTONE_WALL.getDefaultState());
            world.setBlockState(new BlockPos(x, SPAWN_Y + 1, SIZE_Z - 1), Blocks.COBBLESTONE_WALL.getDefaultState());
        }
        for (int z = 0; z < SIZE_Z; z++) {
            world.setBlockState(new BlockPos(0, SPAWN_Y + 1, z), Blocks.COBBLESTONE_WALL.getDefaultState());
            world.setBlockState(new BlockPos(SIZE_X - 1, SPAWN_Y + 1, z), Blocks.COBBLESTONE_WALL.getDefaultState());
        }

        // Colocando um bloco de glowstone no centro
        world.setBlockState(new BlockPos(SIZE_X / 2, SPAWN_Y + 1, SIZE_Z / 2), Blocks.GLOWSTONE.getDefaultState());

        System.out.println("[AncestralPowers] Estrutura pessoal gerada na dimensão pessoal.");
    }

    public static void teleportToPersonalDimension(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        ServerWorld personalWorld = server.getWorld(ModDimensions.PERSONAL_WORLD_KEY);
        if (personalWorld == null) return;

        EnumSet<PositionFlag> flags = EnumSet.noneOf(PositionFlag.class);

        if (player.getWorld() == personalWorld) {
            // Se já estiver na dimensão, manda pro overworld
            ServerWorld overworld = server.getOverworld();
            player.teleport(overworld,
                    overworld.getSpawnPos().getX() + 0.5,
                    overworld.getSpawnPos().getY(),
                    overworld.getSpawnPos().getZ() + 0.5,
                    flags,
                    player.getYaw(),
                    player.getPitch(),
                    false);
            return;
        }

        // Gera a estrutura se estiver vazia
        BlockPos spawnPos = new BlockPos(0, SPAWN_Y, 0);
        if (personalWorld.getBlockState(spawnPos).isAir()) {
            generate(personalWorld);
        }

        // Teleporta para o centro da estrutura
        player.teleport(personalWorld,
                SIZE_X / 2 + 0.5,
                SPAWN_Y + 1,
                SIZE_Z / 2 + 0.5,
                flags,
                player.getYaw(),
                player.getPitch(),
                false);
    }
}
