package dev.joaq.ancestralpowers.commands;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.util.RandomUtils;
import dev.joaq.ancestralpowers.powers.PowersManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
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

