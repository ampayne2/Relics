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
package com.herocraftonline.items.item.attributes.sockets;

import com.herocraftonline.items.api.ItemPlugin;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemManager;
import com.herocraftonline.items.api.item.attribute.ItemAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.AttributeGroup;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.BasicAttributeFactory;
import com.herocraftonline.items.api.item.attribute.attributes.sockets.Gem;
import com.herocraftonline.items.api.item.attribute.attributes.sockets.SocketColor;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.item.attributes.DefaultAttributeType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GemAttribute extends BasicAttribute implements Gem {

    private String displayName;
    private SocketColor color;
    private AttributeGroup attributes;
    private Item item;

    public GemAttribute(String name, String displayName, SocketColor color, AttributeGroup attributes) {
        super(name, DefaultAttributeType.GEM);
        this.displayName = displayName;
        this.color = color;
        this.attributes = attributes;

        setLore((lore, prefix) -> attributes.getLore().addTo(lore, prefix));
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public SocketColor getColor() {
        return color;
    }

    @Override
    public void setColor(SocketColor color) {
        this.color = color;
    }

    @Override
    public boolean hasItem() {
        return item != null;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean hasAttribute(Class<?> clazz) {
        return attributes.hasAttribute(clazz);
    }

    @Override
    public boolean hasAttributeDeep(Class<?> clazz) {
        return attributes.hasAttributeDeep(clazz);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<ItemAttribute> getAttribute(Predicate<ItemAttribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public Optional<ItemAttribute> getAttributeDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<ItemAttribute> getAttributes(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public Collection<ItemAttribute> getAttributesDeep(Predicate<ItemAttribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Class<T> clazz) {
        return attributes.getAttribute(clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(String name, Class<T> clazz) {
        return attributes.getAttribute(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Class<T> clazz) {
        return attributes.getAttributeDeep(clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(String name, Class<T> clazz) {
        return attributes.getAttributeDeep(name, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttribute(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttribute(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Optional<T> getAttributeDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributeDeep(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Class<T> clazz) {
        return attributes.getAttributes(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Class<T> clazz) {
        return attributes.getAttributesDeep(clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributes(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributes(predicate, clazz);
    }

    @Override
    public <T extends ItemAttribute> Collection<T> getAttributesDeep(Predicate<T> predicate, Class<T> clazz) {
        return attributes.getAttributesDeep(predicate, clazz);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEach(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEach(action, predicate);
    }

    @Override
    public void forEachDeep(Consumer<ItemAttribute> action, Predicate<ItemAttribute> predicate) {
        attributes.forEachDeep(action, predicate);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        super.saveToNBT(compound);
        compound.setString("name", getDisplayName());
        compound.setString("color", getColor().getName());
        NBTTagCompound attributesCompound = NBTTagCompound.create();
        attributes.saveToNBT(attributesCompound);
        compound.setBase("attributes", attributesCompound);
        if (hasItem()) {
            NBTTagCompound item = NBTTagCompound.create();
            getItem().saveToNBT(item);
            compound.setBase("item", item);
        }
    }

    public static class Factory extends BasicAttributeFactory<Gem> {

        public Factory(ItemPlugin plugin) {
            super(plugin);
        }

        @Override
        public Gem loadFromConfig(String name, ConfigurationSection config) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load name, color, and attributes
            String displayName = ChatColor.translateAlternateColorCodes('&', config.getString("name"));
            SocketColor color = SocketColor.fromName(config.getString("color"));
            AttributeGroup attributes = (AttributeGroup) DefaultAttributeType.GROUP.getFactory().loadFromConfig("attributes", config);

            // Create gem
            Gem gem = new GemAttribute(name, displayName, color, attributes);

            // Load item
            if (config.isConfigurationSection("item")) {
                ConfigurationSection itemConfig = config.getConfigurationSection("item");
                Item item = itemManager.getItem(itemConfig);
                gem.setItem(item);
            }

            return gem;
        }

        @Override
        public Gem loadFromNBT(String name, NBTTagCompound compound) {
            ItemManager itemManager = getPlugin().getItemManager();

            // Load color and attribute
            String displayName = compound.getString("name");
            SocketColor color = SocketColor.fromName(compound.getString("color"));
            NBTTagCompound attributesCompound = compound.getCompound("attributes");
            AttributeGroup attributes = (AttributeGroup) itemManager.loadAttribute("attributes", attributesCompound);

            // Create gem
            Gem gem = new GemAttribute(name, displayName, color, attributes);

            // Load item
            if (compound.hasKey("item")) {
                NBTTagCompound itemCompound = compound.getCompound("item");
                Item item = itemManager.getItem(itemCompound);
                gem.setItem(item);
            }

            return gem;
        }

    }

}