package com.flamiungo;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlamiungoOptimizer implements ModInitializer {
    public static final String MOD_ID = "flamiungooptimizer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("FlamiungoOptimizer Ultimate initialized for Minecraft 1.21.1 - 1.21.10!");
    }
}
