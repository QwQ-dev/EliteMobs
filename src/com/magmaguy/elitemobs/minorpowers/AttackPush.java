/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.elitemobs.minorpowers;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.powerstances.MinorPowerPowerStance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Created by MagmaGuy on 05/11/2016.
 */
public class AttackPush extends MinorPowers implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin(MetadataHandler.ELITE_MOBS);
    String powerMetadata = MetadataHandler.ATTACK_PUSH_MD;

    @Override
    public void applyPowers(Entity entity) {

        entity.setMetadata(powerMetadata, new FixedMetadataValue(plugin, true));
        MinorPowerPowerStance minorPowerPowerStance = new MinorPowerPowerStance();
        minorPowerPowerStance.itemEffect(entity);

    }

    @Override
    public boolean existingPowers(Entity entity) {

        return entity.hasMetadata(powerMetadata);

    }

    @EventHandler
    public void attackPush(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();
        Entity damagee = event.getEntity();

        if (damager.hasMetadata(powerMetadata)) {

            int pushbackStrength = 2;

            Vector pushbackDirection = damagee.getLocation().toVector().subtract(damager.getLocation().toVector());
            Vector pushbackApplied = pushbackDirection.normalize().multiply(pushbackStrength);

            damagee.setVelocity(pushbackApplied);

        }

        if (damager instanceof Projectile) {

            if (ProjectileMetadataDetector.projectileMetadataDetector(((Projectile) damager), powerMetadata)) {

                int pushbackStrength = 2;

                Vector pushbackDirection = damagee.getLocation().toVector().subtract(damager.getLocation().toVector());
                Vector pushbackApplied = pushbackDirection.normalize().multiply(pushbackStrength);

                damagee.setVelocity(pushbackApplied);

            }

        }

    }

}
