package com.magmaguy.elitemobs.config;

import org.bukkit.configuration.Configuration;

/**
 * Created by MagmaGuy on 05/05/2017.
 */
public class ConfigValues {

    public static Configuration defaultConfig,
            translationConfig,
            itemsProceduralSettingsConfig,
            playerCacheConfig,
            eventsConfig,
            itemsCustomLootSettingsConfig,
            mobCombatSettingsConfig,
            itemsDropSettingsConfig,
            playerMaxRankData;

    public static void initializeCachedConfigurations() {

        CustomConfigLoader customConfigLoader = new CustomConfigLoader();

        defaultConfig = DefaultConfig.fileConfiguration;

        customConfigLoader = new CustomConfigLoader();
        itemsCustomLootSettingsConfig = customConfigLoader.getCustomConfig(ItemsCustomLootSettingsConfig.CONFIG_NAME);

        customConfigLoader = new CustomConfigLoader();
        translationConfig = customConfigLoader.getCustomConfig(TranslationConfig.CONFIG_NAME);

        customConfigLoader = new CustomConfigLoader();
        itemsProceduralSettingsConfig = customConfigLoader.getCustomConfig(ItemsProceduralSettingsConfig.CONFIG_NAME);

        customConfigLoader = new CustomConfigLoader();
        playerCacheConfig = customConfigLoader.getCustomConfig(PlayerCacheData.CONFIG_NAME, true);

        customConfigLoader = new CustomConfigLoader();
        playerMaxRankData = customConfigLoader.getCustomConfig(PlayerMaxGuildRank.CONFIG_NAME, true);

        customConfigLoader = new CustomConfigLoader();
        eventsConfig = customConfigLoader.getCustomConfig(EventsConfig.CONFIG_NAME);

        customConfigLoader = new CustomConfigLoader();
        mobCombatSettingsConfig = customConfigLoader.getCustomConfig(MobCombatSettingsConfig.CONFIG_NAME);

        customConfigLoader = new CustomConfigLoader();
        itemsDropSettingsConfig = customConfigLoader.getCustomConfig(ItemsDropSettingsConfig.CONFIG_NAME);
    }

    public static void initializeConfigurations() {

        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.loadConfiguration();

        ItemsCustomLootSettingsConfig itemsCustomLootSettingsConfig = new ItemsCustomLootSettingsConfig();
        itemsCustomLootSettingsConfig.initializeConfig();

        TranslationConfig translationConfig = new TranslationConfig();
        translationConfig.initializeConfig();

        ItemsProceduralSettingsConfig itemsProceduralSettingsConfig = new ItemsProceduralSettingsConfig();
        itemsProceduralSettingsConfig.initializeConfig();

        PlayerCacheData playerCacheData = new PlayerCacheData();
        playerCacheData.initializeConfig();

        EventsConfig eventsConfig = new EventsConfig();
        eventsConfig.initializeConfig();

        MobCombatSettingsConfig mobCombatSettingsConfig = new MobCombatSettingsConfig();
        mobCombatSettingsConfig.initializeConfig();

        ItemsDropSettingsConfig itemsDropSettingsConfig = new ItemsDropSettingsConfig();
        itemsDropSettingsConfig.initializeConfig();

        PlayerMaxGuildRank playerMaxGuildRank = new PlayerMaxGuildRank();
        playerMaxGuildRank.initializeConfig();

    }

}