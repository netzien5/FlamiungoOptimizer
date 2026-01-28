package com.flamiungo.hud.module;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<HudModule> modules = new ArrayList<>();

    static {
        modules.add(new BrandModule());
        modules.add(new InfoHudModule());
        modules.add(new TargetHudModule());
        modules.add(new KeystrokesModule());
        modules.add(new ReachDisplayModule());
    }

    public static List<HudModule> getModules() {
        return modules;
    }

    public static List<HudModule> getEnabledModules() {
        List<HudModule> enabled = new ArrayList<>();
        for (HudModule m : modules) {
            if (m.isEnabled()) enabled.add(m);
        }
        return enabled;
    }
}
