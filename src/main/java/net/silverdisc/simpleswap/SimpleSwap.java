package net.silverdisc.simpleswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSwap implements ModInitializer, ClientModInitializer {
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
			for (Hand hand : Hand.values()) {
				ItemStack handItem = client.player.getStackInHand(hand);
				if (isChestplate && handItem.getItem() == Items.ELYTRA) {
					client.player.equipStack(EquipmentSlot.CHEST, handItem.copy());
					client.player.setStackInHand(hand, chestSlot.copy());
					return;
				} else if (isElytra && handItem.getItem() == Items.NETHERITE_CHESTPLATE) {
					client.player.equipStack(EquipmentSlot.CHEST, handItem.copy());
					client.player.setStackInHand(hand, chestSlot.copy());
					return;
				}
			}
		}
	}
}
