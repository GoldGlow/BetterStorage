package net.mcft.copy.betterstorage.api.goldenglow;

import net.minecraft.nbt.NBTTagCompound;

public class SpawnConditions {
    public EnumTimeOfDay timeOfDay;
    public EnumWeatherCondition weather;

    public SpawnConditions(EnumTimeOfDay timeOfDay, EnumWeatherCondition weather) {
        this.timeOfDay = timeOfDay;
        this.weather = weather;
    }

    public NBTTagCompound writeToNBT() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setInteger("timeOfDay", timeOfDay.ordinal());
        nbtTagCompound.setInteger("weather", weather.ordinal());
        return nbtTagCompound;
    }
}
