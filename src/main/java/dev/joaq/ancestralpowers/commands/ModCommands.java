package dev.joaq.ancestralpowers.commands;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.dimensions.ModDimensions;
import dev.joaq.ancestralpowers.dimensions.PersonalDimensionStructure;
import dev.joaq.ancestralpowers.util.RandomUtils;
import dev.joaq.ancestralpowers.powers.PowersManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class ModCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            // Comando /gettraits
            dispatcher.register(
                    CommandManager.literal("gettraits")
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();

                                if (!(source.getPlayer() instanceof ServerPlayerEntity player)) {
                                    source.sendFeedback(() -> Text.literal("Apenas jogadores podem usar este comando!"), false);
                                    return 0;
                                }

                                PlayerTraits traits = MyComponents.TRAITS.get(player);

                                player.sendMessage(Text.literal(
                                        "Seus poderes: " + traits.getMovementPower() + " | " +
                                                traits.getMainPower() + " | " + traits.getIntelligence()
                                ), false);

                                return 1;
                            })
            );
            // Comando /gettraits
            dispatcher.register(
                    CommandManager.literal("gotopersonal")
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> {
                                ServerPlayerEntity player = context.getSource().getPlayer();
                                if (player == null) return 0;

                                ServerWorld world = player.getServer().getWorld(ModDimensions.PERSONAL_WORLD_KEY);
                                if (world == null) {
                                    player.sendMessage(Text.of("Dimensão pessoal não encontrada!"), false);
                                    return 0;
                                }

                                PersonalDimensionStructure.teleportToPersonalDimension(player);
                                player.sendMessage(Text.of("Teleportado para sua dimensão pessoal."), false);
                                return 1;
                            })
            );

            // Comando /rerolltraits
            dispatcher.register(
                    CommandManager.literal("rerolltraits")
                            .executes(context -> {
                                ServerCommandSource source = context.getSource();

                                if (!(source.getPlayer() instanceof ServerPlayerEntity player)) {
                                    source.sendFeedback(() -> Text.literal("Apenas jogadores podem usar este comando!"), false);
                                    return 0;
                                }

                                PowersManager.resetAll(player);

                                PlayerTraits traits = MyComponents.TRAITS.get(player);

                                traits.setMainPower(RandomUtils.randomMain());
                                traits.setMovementPower(RandomUtils.randomMovement(traits.getMainPower()));
                                traits.setIntelligence(RandomUtils.randomIntelligence());

                                // Informa o jogador
                                player.sendMessage(Text.literal(
                                        "Novos poderes: " + traits.getMovementPower() + " | " +
                                                traits.getMainPower() + " | " + traits.getIntelligence()
                                ), false);

                                return 1;
                            })
            );
        });
    }
}

