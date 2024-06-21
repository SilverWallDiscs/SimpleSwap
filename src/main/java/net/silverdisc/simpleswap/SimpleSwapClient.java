package net.silverdisc.simpleswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSwapClient implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simple-swap");

    @Override
    public void onInitialize() {
        LOGGER.info("SimpleSwapMod initialized!");
    }

    @Override
    public void onInitializeClient() {
        KeyBindings.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindings.swapKey.wasPressed()) {
                LOGGER.info("Swap key pressed");
                swapElytraAndChestplate(client);
            }
        });
    }

    private void swapElytraAndChestplate(MinecraftClient client) {
        if (client.player == null) return;

        ItemStack chestSlot = client.player.getEquippedStack(EquipmentSlot.CHEST);
        boolean isChestplate = chestSlot.getItem() == Items.NETHERITE_CHESTPLATE; // Adjust as needed
        boolean isElytra = chestSlot.getItem() == Items.ELYTRA;

        if (isChestplate || isElytra) {
            PlayerInventory inventory = client.player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack itemStack = inventory.getStack(i);
                if (isChestplate && itemStack.getItem() == Items.ELYTRA) {
                    // Swap chestplate with elytra
                    client.player.equipStack(EquipmentSlot.CHEST, itemStack.copy());
                    inventory.setStack(i, chestSlot.copy());
                    return;
                } else if (isElytra && itemStack.getItem() == Items.NETHERITE_CHESTPLATE) {
                    // Swap elytra with chestplate
                    client.player.equipStack(EquipmentSlot.CHEST, itemStack.copy());
                    inventory.setStack(i, chestSlot.copy());
                    return;
                }
            }
        }
    }
}
