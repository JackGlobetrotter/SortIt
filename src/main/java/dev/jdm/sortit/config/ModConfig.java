package dev.jdm.sortit.config;

import dev.jdm.sortit.SortIt;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "sortit")
public class ModConfig implements ConfigData {
    public boolean ComposterOverflowEnable = false;
    public boolean ComposterOverflowTimerEnable = false;

}