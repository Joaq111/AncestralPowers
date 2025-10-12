package dev.joaq.ancestralpowers.dimensions;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PersonalDimensionCounter;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.Optional;

public class PersonalDimensionStructure {

    private static final int SPAWN_Y = 64;
    private static final Identifier STRUCTURE_ID = Identifier.of("ancestralpowers", "personal_dimension_structure");

    public static void generate(ServerWorld world, int i, int k) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        Optional<StructureTemplate> optional = manager.getTemplate(STRUCTURE_ID);

        if (optional.isEmpty()) {
            System.err.println("[AncestralPowers] Estrutura 'personal_spawn' não encontrada!");
            return;
        }

        StructureTemplate template = optional.get();
        BlockPos pos = new BlockPos(i, SPAWN_Y, k);
        template.place(world, pos, pos, new StructurePlacementData(), world.getRandom(), 2);

        System.out.println("[AncestralPowers] Estrutura 'personal_spawn' gerada na dimensão pessoal.");
    }

    public static void teleportToPersonalDimension(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        PlayerTraits traits = MyComponents.TRAITS.get(player);
        EnumSet<PositionFlag> flags = EnumSet.noneOf(PositionFlag.class);

        ServerWorld personalWorld = server.getWorld(ModDimensions.PERSONAL_WORLD_KEY);
        if (personalWorld == null) {
            player.sendMessage(net.minecraft.text.Text.of("§cDimensão pessoal não encontrada!"), false);
            return;
        }

        // Se o jogador já estiver na dimensão pessoal, volta para a posição original
        if (player.getWorld() == personalWorld) {
            Vec3d returnPos = traits.getUsagePosition();

            if (returnPos != null) {
                ServerWorld overworld = server.getOverworld();
                player.teleport(overworld,
                        returnPos.x,
                        returnPos.y,
                        returnPos.z,
                        flags,
                        player.getYaw(),
                        player.getPitch(),
                        false);
                traits.clearUsagePosition(); // limpa após usar
            } else {
                player.sendMessage(net.minecraft.text.Text.of("§ePosição de origem não encontrada, retornando ao spawn."), false);
                ServerWorld overworld = server.getOverworld();
                player.teleport(overworld,
                        overworld.getSpawnPos().getX(),
                        overworld.getSpawnPos().getY(),
                        overworld.getSpawnPos().getZ(),
                        flags,
                        player.getYaw(),
                        player.getPitch(),
                        false);
            }
            return;
        }

        // Salva a posição de onde ele ativou
        Vec3d currentPos = getUsagePosition(player);
        traits.setUsagePosition(currentPos);

        if(!traits.getPersonalDimensionGenerated()) {

            PersonalDimensionCounter globalCounter = PersonalDimensionCounter.getServerState(server);
            int nextValue = globalCounter.incrementAndGet();
            traits.setPersonalDimensionValue(nextValue);

            int offset = nextValue * 500;
            BlockPos checkPos = new BlockPos(offset, SPAWN_Y, offset);
            if (personalWorld.getBlockState(checkPos).isAir()) {
                generate(personalWorld, offset, offset);
            }

            traits.setPersonalDimensionGenerated(true);
            player.sendMessage(Text.literal("Tu e o numero: "+ traits.getPersonalDimensionValue()));
        }

        int value = traits.getPersonalDimensionValue() * 500;
        player.teleport(personalWorld,
                23.5+value, 89, 23.5+value,  // posição inicial dentro da dimensão
                flags,
                player.getYaw(),
                player.getPitch(),
                false);

        player.sendMessage(net.minecraft.text.Text.of("§aVocê foi transportado para sua dimensão pessoal."), false);
    }

    private static Vec3d getUsagePosition(ServerPlayerEntity player) {
        Vec3d playerPos = player.getPos();
        BlockPos ground = new BlockPos((int) playerPos.x, (int) playerPos.y, (int) playerPos.z);
        return new Vec3d(ground.getX(), ground.getY(), ground.getZ());
    }
}
