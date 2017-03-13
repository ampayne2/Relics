/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item.attributes;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.attribute.attributes.Rarity;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.base.BaseAttributeFactory;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.DefaultAttribute;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class RarityAttribute extends BaseAttribute<Rarity> implements Rarity {

    private int rarity;

    public RarityAttribute(String name, int rarity, String text) {
        super(name, DefaultAttribute.RARITY);

        this.rarity = rarity;

        setLore((lore, prefix) -> lore.add(prefix + text));
    }

    @Override
    public Integer getValue() {
        return rarity;
    }

    @Override
    public void setValue(Integer value) {
        rarity = value;
    }

    @Override
    public int compareTo(Rarity o) {
        return getValue() - o.getValue();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setInt("tier", getValue());
    }

    public static class Factory extends BaseAttributeFactory<Rarity> {
        private final List<String> tiers;
        private final String unknown;

        public Factory(ItemPlugin plugin) {
            super(plugin);

            FileConfiguration config = plugin.getConfigManager().getConfig(DefaultAttribute.RARITY);
            tiers = config.getStringList("tiers");
            tiers.replaceAll(tier -> ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', tier));
            unknown = ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', config.getString("unknown", "&8Unknown"));
        }

        @Override
        public Rarity loadFromConfig(String name, ConfigurationSection config) {
            // Load rarity
            int rarity = Math.max(config.getInt("tier", 0), 0);
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }

        @Override
        public Rarity loadFromNBT(String name, NBTTagCompound compound) {
            // Load rarity
            int rarity = compound.getInt("tier");
            String text = rarity < tiers.size() ? tiers.get(rarity) : unknown;

            // Create rarity attribute
            return new RarityAttribute(name, rarity, text);
        }
    }

}
