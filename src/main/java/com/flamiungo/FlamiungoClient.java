package com.flamiungo;

import com.flamiungo.config.FlamiungoConfig;
import com.flamiungo.hud.HudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class FlamiungoClient implements ClientModInitializer {

    private static KeyBinding zoomKey;
    private static KeyBinding editorKey;

    @Override
    public void onInitializeClient() {
        // Register HUD
        HudRenderCallback.EVENT.register(new HudOverlay());
        
        // Register China Hat
        com.flamiungo.render.ChinaHatRenderer.register();

        // Register Keys
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flamiungo.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.flamiungo"
        ));

        editorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.flamiungo.editor",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_INSERT,
                "category.flamiungo"
        ));

        // Tick Event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
             FlamiungoConfig.zoom = zoomKey.isPressed();
             
             while (editorKey.wasPressed()) {
                 client.setScreen(new com.flamiungo.screen.HudEditorScreen());
             }
        });

        // Commands
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("flamiungooptimizer")
                .then(ClientCommandManager.argument("feature", com.mojang.brigadier.arguments.StringArgumentType.string())
                    .suggests((context, builder) -> net.minecraft.command.CommandSource.suggestMatching(java.util.List.of(
                        "lowFire", "fullbright", "noWeather", "tntTimer", "inventoryHud", 
                        "noHurtCam", "noExplosions", "targetHud", "showInfoHud", "clearDespawn",
                        "chinaHat", "keystrokes", "reachDisplay"
                    ), builder))
                    .then(ClientCommandManager.argument("value", com.mojang.brigadier.arguments.BoolArgumentType.bool())
                        .executes(context -> {
                            String feature = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "feature");
                            boolean value = com.mojang.brigadier.arguments.BoolArgumentType.getBool(context, "value");
                            boolean success = true;

                            switch (feature) {
                                case "lowFire" -> FlamiungoConfig.lowFire = value;
                                case "fullbright" -> FlamiungoConfig.fullbright = value;
                                case "noWeather" -> FlamiungoConfig.noWeather = value;
                                case "tntTimer" -> FlamiungoConfig.tntTimer = value;
                                case "inventoryHud" -> FlamiungoConfig.inventoryHud = value;
                                case "noHurtCam" -> FlamiungoConfig.noHurtCam = value;
                                case "noExplosions" -> FlamiungoConfig.noExplosions = value;
                                case "targetHud" -> {
                                    FlamiungoConfig.targetHud = value;
                                    getModule("TargetHUD").setEnabled(value);
                                }
                                case "showInfoHud" -> {
                                    FlamiungoConfig.showInfoHud = value;
                                    getModule("InfoHUD").setEnabled(value);
                                }
                                case "clearDespawn" -> FlamiungoConfig.clearDespawn = value;
                                case "chinaHat" -> FlamiungoConfig.chinaHat = value;
                                case "keystrokes" -> {
                                    FlamiungoConfig.keystrokes = value;
                                    getModule("Keystrokes").setEnabled(value);
                                }
                                case "reachDisplay" -> {
                                    FlamiungoConfig.reachDisplay = value;
                                    getModule("ReachDisplay").setEnabled(value);
                                }
                                default -> success = false;
                            }

                            if (success) {
                                context.getSource().sendFeedback(Text.literal("§b[FlamiungoOptimizer] §a" + feature + " set to " + value));
                            } else {
                                context.getSource().sendFeedback(Text.literal("§c[FlamiungoOptimizer] Unknown feature: " + feature));
                            }
                            return 1;
                        })
                    )
                )
                .executes(context -> {
                    context.getSource().sendFeedback(Text.literal("§b[FlamiungoOptimizer] §7Usage: /flamiungooptimizer <feature> <true/false>"));
                    return 1;
                }));
        });
    }

    private com.flamiungo.hud.module.HudModule getModule(String name) {
        for (com.flamiungo.hud.module.HudModule m : com.flamiungo.hud.module.ModuleManager.getModules()) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }
}
