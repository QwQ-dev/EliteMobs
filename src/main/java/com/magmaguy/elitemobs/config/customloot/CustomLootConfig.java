package com.magmaguy.elitemobs.config.customloot;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.config.UnusedNodeHandler;
import com.magmaguy.elitemobs.config.customloot.premade.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomLootConfig {

    private static ArrayList<CustomLootConfigFields> customLootConfigFieldsList = new ArrayList<>(Arrays.asList(
            new BerserkerCharmConfig(),
            new ChameleonCharmConfig(),
            new CheetahCharmConfig(),
            new DepthsSeekerConfig(),
            new DwarvenGreedConfig(),
            new LuckyCharmsConfig(),
            new ElephantCharmConfig(),
            new FireflyCharmConfig(),
            new FishyCharmConfig(),
            new MagmaguysToothpickConfig(),
            new OwlCharmConfig(),
            new RabbitCharmConfig(),
            new SalamanderCharmConfig(),
            new ScorpionCharm(),
            new ShulkerCharmConfig(),
            new SlowpokeCharmConfig(),
            new TheFellerConfig(),
            new VampiricCharmConfig(),
            new ZombieKingsAxeConfig()
    ));

    public static void initializeConfigurations() {
        //Check if the directory doesn't exist
        if (!Files.isDirectory(Paths.get(MetadataHandler.PLUGIN.getDataFolder().getPath() + "/customitems"))) {
            generateFreshConfigurations();
            return;
        }
        //Runs if the directory exists

        //Check if all the defaults exist
        for (File file : (new File(MetadataHandler.PLUGIN.getDataFolder().getPath() + "/customitems")).listFiles()) {
            boolean isPremade = false;
            for (CustomLootConfigFields customLootConfigFields : customLootConfigFieldsList) {
                if (file.getName().equalsIgnoreCase(customLootConfigFields.getFileName())) {
                    customLootConfigFieldsList.remove(customLootConfigFields);
                    initializeConfiguration(file.getName(), customLootConfigFields);
                    isPremade = true;
                    break;
                }
            }
            if (!isPremade)
                initializeConfiguration(file);
        }

        if (!customLootConfigFieldsList.isEmpty())
            generateFreshConfigurations();
    }

    /**
     * Called when the appropriate configurations directory does not exist
     *
     * @return
     */
    private static void generateFreshConfigurations() {
        for (CustomLootConfigFields customLootConfigFields : customLootConfigFieldsList)
            initializeConfiguration(customLootConfigFields.getFileName(), customLootConfigFields);
    }

    /**
     * Initializes a single instance of a premade configuration using the default values.
     *
     * @param customLootConfigFields
     * @return
     */
    private static FileConfiguration initializeConfiguration(String fileName, CustomLootConfigFields customLootConfigFields) {

        File file = new File(MetadataHandler.PLUGIN.getDataFolder().getPath() + "/customitems", customLootConfigFields.getFileName());

        if (!file.exists())
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException ex) {
                Bukkit.getLogger().warning("[EliteMobs] Error generating the plugin file: " + file.getName());
            }

        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.addDefaults(customLootConfigFields.generateConfigDefaults(fileConfiguration));
        fileConfiguration.options().copyDefaults(true);
        UnusedNodeHandler.clearNodes(fileConfiguration);

        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CustomLootConfigFields.addCustomLootConfigField(fileName, customLootConfigFields);

        return fileConfiguration;

    }

    /**
     * Called when a user-made loot is detected.
     *
     * @return
     */
    private static FileConfiguration initializeConfiguration(File file) {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);
        CustomLootConfigFields.addCustomLootConfigField(file.getName(), new CustomLootConfigFields(file.getName(), fileConfiguration));
        return fileConfiguration;
    }


}
