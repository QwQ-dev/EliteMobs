package com.magmaguy.elitemobs.quests;

import com.magmaguy.elitemobs.ChatColorConverter;
import com.magmaguy.elitemobs.items.MobTierCalculator;
import com.magmaguy.elitemobs.mobconstructor.EliteMobEntity;
import com.magmaguy.elitemobs.mobconstructor.mobdata.aggressivemobs.EliteMobProperties;
import com.magmaguy.elitemobs.utils.StringColorAnimator;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class QuestObjective {

    private UUID questUUID;
    private int questTier;
    private int objectiveKills = 0;
    private int currentKills = 0;
    private int minimumEliteMobTier = 0;
    private int minimumEliteMobLevel = 0;
    private double questDifficulty = 0;
    private EntityType entityType;
    private boolean isComplete = false;
    private boolean isTurnedIn = false;

    public QuestObjective(int objectiveKills, int minimumEliteMobTier, EntityType entityType, int questTier) {
        setQuestTier(questTier);
        setObjectiveKills(objectiveKills);
        setMinimumEliteMobTier(minimumEliteMobTier);
        setEntityType(entityType);
        setQuestDifficulty();
    }

    public UUID getQuestUUID() {
        return questUUID;
    }

    public void setQuestUUID(UUID questUUID) {
        this.questUUID = UUID.randomUUID();
    }

    private void setQuestTier(int questTier) {
        this.questTier = questTier;
    }

    public int getQuestTier() {
        return this.questTier;
    }


    private void setObjectiveKills(int objectiveKills) {
        this.objectiveKills = objectiveKills;
    }

    public int getObjectiveKills() {
        return this.objectiveKills;
    }

    public int getCurrentKills() {
        return currentKills;
    }

    public boolean processQuestProgression(EliteMobEntity eliteMobEntity) {
        if (!eliteMobEntity.getHasSpecialLoot()) return false;
        if (!eliteMobEntity.getLivingEntity().getType().equals(getEntityType())) return false;
        if (eliteMobEntity.getLevel() < getMinimumEliteMobTier()) return false;
        addKill();
        return true;
    }

    public void addKill() {
        this.currentKills++;
        if (currentKills >= this.objectiveKills)
            setComplete(true);
    }

    public void setCurrentKills(int currentKills) {
        this.currentKills = currentKills;
    }

    private void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    private void setMinimumEliteMobTier(int minimumEliteMobTier) {
        this.minimumEliteMobTier = minimumEliteMobTier;
        setMinimumEliteMobLevel();
    }

    public int getMinimumEliteMobTier() {
        return minimumEliteMobTier;
    }

    public int getMinimumEliteMobLevel() {
        return minimumEliteMobLevel;
    }

    private void setMinimumEliteMobLevel() {
        this.minimumEliteMobLevel = MobTierCalculator.findMobLevel(getMinimumEliteMobTier());
    }

    public double getQuestDifficulty() {
        return this.questDifficulty;
    }

    public void setQuestDifficulty() {
        this.questDifficulty = getObjectiveKills() / 10 * questTier + 5;

    }

    public boolean isComplete() {
        return isComplete;
    }

    private void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getEliteMobName() {
        return EliteMobProperties.getPluginData(getEntityType()).getName()
                .replace("$level", MobTierCalculator.findMobLevel(getMinimumEliteMobTier()) + "+");
    }

    public void sendQuestStartMessage(Player player) {
        player.sendMessage("Quest accepted");
        StringColorAnimator.startTitleAnimation(player, "You have accepted a quest!",
                "Kill " + getObjectiveKills() + " " + getEliteMobName(), ChatColor.DARK_GREEN, ChatColor.GREEN);
    }

    public void sendQuestCompleteMessage(Player player) {
        if (!player.isOnline()) return;
        StringColorAnimator.startTitleAnimation(player, "Quest complete!",
                "You have killed " + getObjectiveKills() + " " + getEliteMobName(),
                ChatColor.GOLD, ChatColor.YELLOW);
//        player.sendMessage("[EliteMobs] You've completed the a quest!");
    }

    public void sendQuestProgressionMessage(Player player) {
        if (!player.isOnline()) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                TextComponent.fromLegacyText(ChatColorConverter.convert("&7[EM] &aslay " + getCurrentKills() + "&f/&c" + getObjectiveKills() + " &f" + getEliteMobName())));
    }

    public boolean isTurnedIn() {
        return isTurnedIn;
    }

    public void setTurnedIn(boolean turnedIn) {
        isTurnedIn = turnedIn;
    }

    public void doQuestCompletion(Player player) {
        if (isTurnedIn) return;
        setTurnedIn(true);
        setComplete(true);
        sendQuestCompleteMessage(player);
    }

}
