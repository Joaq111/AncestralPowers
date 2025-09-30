package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.powers.Power;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ImortalPower implements Power {
    private boolean registered = false;

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        if (!registered) { // registra só uma vez
            registered = true;

            ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, amount) -> {
                if (entity instanceof ServerPlayerEntity serverPlayer) {
                    PlayerTraits traits = MyComponents.TRAITS.get(serverPlayer);

                    // Verifica se esse player tem o poder Imortalidade
                    if ("Imortalidade".equals(traits.getMainPower())) {
                        turnIntoGhost(serverPlayer);
                        return false; // Cancela a morte
                    }
                }
                return true; // morte normal
            });
        }
    }

    private void dropInventory(ServerPlayerEntity player) {
        player.getInventory().dropAll();
    }

    private void turnIntoGhost(ServerPlayerEntity player) {
        dropInventory(player);

        // Marca como fantasma no componente
        // traits.setGhost(true);

        // Restaura a vida mínima para não "morrer" de novo
        player.setHealth(1.0f);

        // Ajusta habilidades do "fantasma"
        player.getAbilities().allowFlying = true;
        player.getAbilities().invulnerable = true;
        player.getAbilities().flying = true;
        player.getAbilities().setWalkSpeed(0.05f);
        player.sendAbilitiesUpdate();

        player.sendMessage(Text.literal("Você agora é um fantasma!"), false);
    }

    @Override
    public void reset(ServerPlayerEntity player) { }
}
