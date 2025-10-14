package dev.joaq.ancestralpowers.powers.main;

import dev.joaq.ancestralpowers.components.MyComponents;
import dev.joaq.ancestralpowers.components.PlayerTraits;
import dev.joaq.ancestralpowers.dimensions.DimensionalArenaStructure;
import dev.joaq.ancestralpowers.powers.PowerBase;
import dev.joaq.ancestralpowers.util.PlayerUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.UUID;

public class DimensionalArenaPower extends PowerBase {

    @Override
    protected float staminaCost() {
        return 0.25f;
    }

    @Override
    protected String ActivationType() {
        return "PRESS-PERSISTENT";
    }

    @Override
    public void apply(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        execute(player, activate, ActivationType(), traits, "Specific", customIsActive(player));
    }

    private boolean customIsActive(ServerPlayerEntity player) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);
        return traits.getInArena();
    }

    @Override
    protected void disablePowerSpecific(ServerPlayerEntity player) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);

        if (traits.getInArena()) {
            // Teleporta o player de volta
            DimensionalArenaStructure.teleportBack(player);

            // Tenta recuperar o target salvo
            UUID targetUuid = traits.getArenaTarget();
            if (targetUuid != null) {
                ServerPlayerEntity target = player.getServer().getPlayerManager().getPlayer(targetUuid);
                if (target != null) {
                    DimensionalArenaStructure.teleportBack(target);
                    target.sendMessage(Text.literal("§cVocê foi retornado da Dimensional Arena."), false);
                }
            }

            traits.setInArena(false);
            traits.setArenaTarget(null);

            player.sendMessage(Text.literal("§aVocê retornou da Dimensional Arena."), false);
        }

        traits.setActPower_main(false);
    }

    @Override
    protected void executeLogic(ServerPlayerEntity player, boolean activate, float stamina) {
        PlayerTraits traits = MyComponents.TRAITS.get(player);

        if (traits.getInArena()) {
            disablePowerSpecific(player);
            return;
        }

        ServerPlayerEntity target = PlayerUtils.getPlayerLookedAt(player, 20.0D);

        if (target != null) {
            DimensionalArenaStructure.teleportToArena(player, target);
            traits.setInArena(true);
            traits.setArenaTarget(target.getUuid());
        } else {
            player.sendMessage(Text.literal("§cNenhum alvo encontrado."), false);
        }
    }

    @Override
    public void reset(ServerPlayerEntity player) {
        disablePowerSpecific(player);
    }
}
