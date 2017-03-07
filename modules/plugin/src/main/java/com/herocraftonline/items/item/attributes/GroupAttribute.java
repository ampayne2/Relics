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
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.AttributeContainer;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.AttributeGroup;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttributeContainer;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatAttribute;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GroupAttribute extends BasicAttributeContainer implements AttributeGroup {

    private final Map<String, ItemAttribute> attributes;
    private final boolean spacing;

    public GroupAttribute(String name, Map<String, ItemAttribute> attributes, boolean spacing) {
        super(name, DefaultAttributeType.GROUP);

        this.attributes = attributes;
        this.spacing = spacing;

        setLore((lore, prefix) -> {
            getAttributes().stream()
                    .sorted(Comparator.comparingInt(a -> a.getType().getLorePosition()))
                    .forEachOrdered(a -> {
                        if (!(a instanceof StatAttribute)) {
                            int s = lore.size();
                            a.getLore().addTo(lore, prefix);
                            if (spacing && lore.size() > s) {
                                lore.add("");
                            }
                        }
                    });
            if (spacing && getAttributes().size() > 0) {
                lore.remove(lore.size() - 1);
            }
        });
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        return attributes.values();
    }

    @Override
    public Map<String, ItemAttribute> getAttributesByName() {
        return attributes;
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        for (ItemAttribute attribute : getAttributes()) {
            if (predicate.test(attribute)) {
                return true;
            }
            if (attribute instanceof AttributeContainer) {
                if (((AttributeContainer) attribute).hasAttributeDeep(predicate)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        for (ItemAttribute attribute : getAttributes()) {
            if (clazz.isAssignableFrom(attribute.getClass()) && predicate.test((T) attribute)) {
                return Optional.of((T) attribute);
            }
            if (attribute instanceof AttributeContainer) {
                Optional<T> optional = ((AttributeContainer) attribute).getAttributeDeep(predicate, clazz);
                if (optional.isPresent()) {
                    return optional;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        for (ItemAttribute attribute : getAttributes()) {
            action.accept(attribute);
            if (attribute instanceof AttributeContainer) {
                ((AttributeContainer) attribute).forEachDeep(action);
            }
        }
    }

    @Override
    public void addAttribute(ItemAttribute... attributes) {
        for (ItemAttribute attribute : attributes) {
            this.attributes.put(attribute.getName(), attribute);
        }
    }

    @Override
    public void removeAttribute(ItemAttribute attribute) {
        attributes.remove(attribute.getName());
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        NBTTagCompound attributes = NBTTagCompound.create();
        for (ItemAttribute attribute : getAttributes()) {
            NBTTagCompound attributeCompound = NBTTagCompound.create();
            attribute.saveToNBT(attributeCompound);
            attributes.setBase(attribute.getName(), attributeCompound);
        }
        compound.setBase("attributes", attributes);
        compound.setBoolean("spacing", spacing);
    }

    public static class Factory extends BasicAttributeFactory<AttributeGroup> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public AttributeGroup loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            Map<String, ItemAttribute> attributeMap = new TreeMap<>();
            if (config.isConfigurationSection("attributes")) {
                ConfigurationSection attributes = config.getConfigurationSection("attributes");
                attributes.getKeys(false).stream().filter(attributes::isConfigurationSection).forEach(attributeName -> {
                    ConfigurationSection attributeSection = attributes.getConfigurationSection(attributeName);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeName, attributeSection);
                    if (attribute != null) {
                        attributeMap.put(attributeName, attribute);
                    }
                });
            }

            // Create attribute group
            return new GroupAttribute(name, attributeMap, config.getBoolean("spacing", true));
        }

        @Override
        public AttributeGroup loadFromNBT(String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load attributes
            Map<String, ItemAttribute> attributeMap = new TreeMap<>();
            if (compound.hasKey("attributes")) {
                NBTTagCompound attributes = compound.getCompound("attributes");
                attributes.getKeySet().forEach(attributeName -> {
                    NBTTagCompound attributeCompound = attributes.getCompound(attributeName);
                    ItemAttribute attribute = itemManager.loadAttribute(attributeName, attributeCompound);
                    if (attribute != null) {
                        attributeMap.put(attributeName, attribute);
                    }
                });
            }

            // Create attribute group
            return new GroupAttribute(name, attributeMap, compound.getBoolean("spacing"));
        }

    }

}