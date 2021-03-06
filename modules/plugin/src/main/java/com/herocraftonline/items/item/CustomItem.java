/*
 * This file is part of Relics.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.item;

import com.herocraftonline.items.api.item.Equippable;
import com.herocraftonline.items.api.item.Item;
import com.herocraftonline.items.api.item.ItemFactory;
import com.herocraftonline.items.api.item.ItemType;
import com.herocraftonline.items.api.item.attribute.Attribute;
import com.herocraftonline.items.api.item.attribute.attributes.Group;
import com.herocraftonline.items.api.item.attribute.attributes.Minecraft;
import com.herocraftonline.items.api.item.attribute.attributes.Model;
import com.herocraftonline.items.api.item.attribute.attributes.crafting.Blueprint;
import com.herocraftonline.items.api.item.attribute.attributes.requirements.Requirement;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatAttribute;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatGroup;
import com.herocraftonline.items.api.item.attribute.attributes.stats.StatType;
import com.herocraftonline.items.api.storage.nbt.NBTTagCompound;
import com.herocraftonline.items.api.storage.nbt.NBTTagList;
import com.herocraftonline.items.api.storage.value.StoredValue;
import com.herocraftonline.items.api.storage.value.Value;
import com.herocraftonline.items.api.storage.value.variables.BaseVariableContainer;
import com.herocraftonline.items.api.storage.value.variables.VariableContainer;
import com.herocraftonline.items.item.attributes.GroupAttribute;
import com.herocraftonline.items.nms.NMSHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class CustomItem implements Item {

    // Base item tags to avoid overriding other custom nbt
    private static final String ITEM_TAG = "relics-item";

    // Specific item tags for various item information
    private static final String ID_TAG = "item";
    private static final String TYPE_TAG = "item-type";
    private static final String UUID_TAG = "id";
    private static final String NAME_TAG = "name";
    private static final String MATERIAL_TAG = "material";
    private static final String ENCHANTMENTS_TAG = "enchantments";
    private static final String UNBREAKABLE_TAG = "unbreakable";
    private static final String STACKABLE_TAG = "stackable";
    private static final String ATTRIBUTES_TAG = "attributes";
    private static final String INSTANCE_TAG = "item-instance";

    // Generic minecraft attribute tags
    private static final String MODIFIERS = "AttributeModifiers";
    private static final String MODIFIER_ATTRIBUTE_NAME = "AttributeName";
    private static final String MODIFIER_NAME = "Name";
    private static final String MODIFIER_AMOUNT = "Amount";
    private static final String MODIFIER_SLOT = "Slot";
    private static final String MODIFIER_OPERATION = "Operation";
    private static final String MODIFIER_UUID_MOST = "UUIDMost";
    private static final String MODIFIER_UUID_LEAST = "UUIDLeast";

    private final UUID id;
    private final Value<String> name;
    private final Material material;
    private final Map<Enchantment, Integer> enchantments;
    private final boolean unbreakable;
    private final boolean stackable;
    private final ItemType type;
    private final VariableContainer variables;
    private final Group attributes;
    private boolean equipped = false;

    private CustomItem(UUID id, Value<String> name, Material material, Map<Enchantment, Integer> enchantments, boolean unbreakable, boolean stackable, ItemType type, VariableContainer variables, Group attributes) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.enchantments = enchantments;
        this.unbreakable = unbreakable;
        this.stackable = stackable;
        this.type = type;
        this.variables = variables;
        this.attributes = attributes;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return ChatColor.translateAlternateColorCodes('&', name.getValue());
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable || hasAttribute(Model.class);
    }

    @Override
    public boolean isStackable() {
        return stackable;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public boolean isType(ItemType itemType) {
        return getType().isType(itemType);
    }

    @Override
    public boolean hasAttribute(String name) {
        return attributes.hasAttribute(name);
    }

    @Override
    public boolean hasAttribute(Class<? extends Attribute> type) {
        return attributes.hasAttribute(type);
    }

    @Override
    public boolean hasAttribute(Predicate<Attribute> predicate) {
        return attributes.hasAttribute(predicate);
    }

    @Override
    public Optional<Attribute> getAttribute(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public Optional<Attribute> getAttribute(Predicate<Attribute> predicate) {
        return attributes.getAttribute(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type) {
        return attributes.getAttribute(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, String name) {
        return attributes.getAttribute(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttribute(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttribute(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributes() {
        return attributes.getAttributes();
    }

    @Override
    public Collection<Attribute> getAttributes(Predicate<Attribute> predicate) {
        return attributes.getAttributes(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type) {
        return attributes.getAttributes(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributes(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributes(type, predicate);
    }

    @Override
    public void forEach(Consumer<Attribute> action) {
        attributes.forEach(action);
    }

    @Override
    public void forEach(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEach(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Consumer<T> action) {
        attributes.forEach(type, action);
    }

    @Override
    public <T extends Attribute> void forEach(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEach(type, predicate, action);
    }

    @Override
    public boolean hasAttributeDeep(String name) {
        return attributes.hasAttributeDeep(name);
    }

    @Override
    public boolean hasAttributeDeep(Class<? extends Attribute> type) {
        return attributes.hasAttributeDeep(type);
    }

    @Override
    public boolean hasAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.hasAttributeDeep(predicate);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(String name) {
        return attributes.getAttributeDeep(name);
    }

    @Override
    public Optional<Attribute> getAttributeDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributeDeep(predicate);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type) {
        return attributes.getAttributeDeep(type);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, String name) {
        return attributes.getAttributeDeep(type, name);
    }

    @Override
    public <T extends Attribute> Optional<T> getAttributeDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributeDeep(type, predicate);
    }

    @Override
    public Collection<Attribute> getAttributesDeep() {
        return attributes.getAttributesDeep();
    }

    @Override
    public Collection<Attribute> getAttributesDeep(Predicate<Attribute> predicate) {
        return attributes.getAttributesDeep(predicate);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type) {
        return attributes.getAttributesDeep(type);
    }

    @Override
    public <T extends Attribute> Collection<T> getAttributesDeep(Class<T> type, Predicate<T> predicate) {
        return attributes.getAttributesDeep(type, predicate);
    }

    @Override
    public void forEachDeep(Consumer<Attribute> action) {
        attributes.forEachDeep(action);
    }

    @Override
    public void forEachDeep(Predicate<Attribute> predicate, Consumer<Attribute> action) {
        attributes.forEachDeep(predicate, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Consumer<T> action) {
        attributes.forEachDeep(type, action);
    }

    @Override
    public <T extends Attribute> void forEachDeep(Class<T> type, Predicate<T> predicate, Consumer<T> action) {
        attributes.forEachDeep(type, predicate, action);
    }

    @Override
    public void addAttribute(Attribute... attributes) {
        this.attributes.addAttribute(attributes);
    }

    @Override
    public void removeAttribute(Attribute attribute) {
        attributes.removeAttribute(attribute);
    }

    @Override
    public Optional<Object> getValue(String name) {
        return variables.getValue(name);
    }

    @Override
    public void setValue(String name, Object value) {
        variables.setValue(name, value);
    }

    @Override
    public void addDynamicValue(Value value) {
        variables.addDynamicValue(value);
    }

    @Override
    public void resetDynamicValues() {
        variables.resetDynamicValues();
    }

    @Override
    public boolean isEquipped() {
        return equipped;
    }

    @Override
    public boolean canEquip(Player player) {
        return getAttributesDeep(Requirement.class).stream().allMatch(a -> a.test(player, this));
    }

    @Override
    public boolean onEquip(Player player) {
        equipped = true;
        Collection<Attribute> attributes = getAttributesDeep(attribute -> attribute instanceof Equippable);
        boolean update = false;
        for (Attribute attribute : attributes) {
            if (((Equippable) attribute).onEquip(player)) {
                update = true;
            }
        }
        return update;
    }

    @Override
    public boolean onUnEquip(Player player) {
        equipped = false;
        Collection<Attribute> attributes = getAttributesDeep(attribute -> attribute instanceof Equippable);
        boolean update = false;
        for (Attribute attribute : attributes) {
            if (((Equippable) attribute).onUnEquip(player)) {
                update = true;
            }
        }
        return update;
    }

    @Override
    public ItemStack getItem() {
        // Create item
        ItemStack item = new ItemStack(getMaterial());

        // Get ItemMeta
        ItemMeta meta = item.getItemMeta();

        // Set item name
        meta.setDisplayName(getName());
        // meta.setDisplayName(getName() + " " + ChatColor.DARK_GRAY + ChatColor.ITALIC + (type.isTransient() ? ChatColor.STRIKETHROUGH : "") + type.getName());

        // Set item lore
        meta.setLore(createLore());

        // Set item enchantments
        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
        }

        // Set unbreakable
        if (isUnbreakable()) {
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }

        // Check for generic attributes
        if (hasAttributeDeep(Minecraft.class)) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        // Set ItemMeta
        item.setItemMeta(meta);

        // Check for model or blueprint attributes
        attributes.getAttributesDeep(Model.class).stream().reduce(Model.PRIORITY).ifPresent(model -> model.apply(item));
        attributes.getAttribute(Blueprint.class).ifPresent(blueprint -> blueprint.apply(item));

        // Get NBTTagCompound
        NBTTagCompound compound = NMSHandler.instance().getTag(item);

        // Save item to compound
        NBTTagCompound itemTag = NBTTagCompound.create();
        saveToNBT(itemTag);
        compound.setBase(ITEM_TAG, itemTag);

        // Save minecraft attribute modifiers to compound
        setModifiers(compound);

        // Set NBTTagCompound and return item
        return NMSHandler.instance().setTag(item, compound).orElse(item);
    }

    @Override
    public Optional<ItemStack> updateItem(final ItemStack item) {
        // Get updated item in NBT form
        NBTTagCompound updated = NMSHandler.instance().toNBT(getItem());

        // Update and return item
        return NMSHandler.instance().replaceNBT(item, updated);
    }

    private List<String> createLore() {
        List<String> lore = new ArrayList<>();

        // Add stats to top of lore
        if (attributes.hasAttributeDeep(StatAttribute.class)) {
            addStatGroup(lore, StatType.Position.FARTHEST_TOP);
            addStatGroup(lore, StatType.Position.TOP);
        }
        // Add lore of all attributes
        attributes.getLore().addTo(lore, ChatColor.GRAY.toString());
        // Add stats to bottom of lore
        if (attributes.hasAttributeDeep(StatAttribute.class)) {
            lore.add("");
            addStatGroup(lore, StatType.Position.BOTTOM);
            addStatGroup(lore, StatType.Position.FARTHEST_BOTTOM);
        }

        return lore;
    }

    @SuppressWarnings("unchecked")
    private void addStatGroup(List<String> lore, StatType.Position position) {
        StatGroup stats = new StatGroup();
        attributes.forEachDeep(StatAttribute.class,
                a -> a.getStatType().getPosition() == position,
                (Consumer<StatAttribute>) stats::addStat);
        stats.addTo(lore, "");
    }

    private void setModifiers(NBTTagCompound compound) {
        // Get current attribute modifiers
        NBTTagList modifiers = compound.hasKey(MODIFIERS) ? compound.getList(MODIFIERS, 10) : NBTTagList.create();

        // Remove current relics attribute modifiers
        int i = 0;
        while (i < modifiers.size()) {
            if (modifiers.getCompound(i).getString(MODIFIER_NAME).startsWith(ITEM_TAG)) {
                modifiers.removeBase(i);
            } else {
                i++;
            }
        }

        // Set relics attribute modifiers if item is equipped
        if (isEquipped()) {
            forEachDeep(Minecraft.class, attribute -> modifiers.addBase(createModifier(attribute)));
        }

        // Set attribute modifiers
        compound.setBase(MODIFIERS, modifiers);
    }

    private NBTTagCompound createModifier(Minecraft attribute) {
        NBTTagCompound modifier = NBTTagCompound.create();
        modifier.setString(MODIFIER_ATTRIBUTE_NAME, attribute.getMinecraftType().getName());
        modifier.setString(MODIFIER_NAME, ITEM_TAG + ":" + attribute.getName());
        modifier.setDouble(MODIFIER_AMOUNT, attribute.getAmount());
        if (attribute.getSlot() != Minecraft.Slot.ANY) {
            modifier.setString(MODIFIER_SLOT, attribute.getSlot().getName());
        }
        modifier.setInt(MODIFIER_OPERATION, attribute.getOperation().ordinal());
        modifier.setLong(MODIFIER_UUID_MOST, attribute.getUUID().getMostSignificantBits());
        modifier.setLong(MODIFIER_UUID_LEAST, attribute.getUUID().getLeastSignificantBits());
        return modifier;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setString(UUID_TAG, getId().toString());
        name.saveToNBT(compound);
        compound.setString(MATERIAL_TAG, getMaterial().name());
        NBTTagCompound enchants = NBTTagCompound.create();
        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            enchants.setInt(enchantment.getKey().getName(), enchantment.getValue());
        }
        compound.setBase(ENCHANTMENTS_TAG, enchants);
        compound.setBoolean(UNBREAKABLE_TAG, isUnbreakable());
        compound.setBoolean(STACKABLE_TAG, isStackable());
        if (!isStackable()) {
            compound.setString("prevent-" + STACKABLE_TAG, UUID.randomUUID().toString());
        }
        compound.setString(TYPE_TAG, getType().getName());
        attributes.saveToNBT(compound);
        variables.saveToNBT(compound);
        compound.setObject(INSTANCE_TAG, this);
    }

    public static class DefaultItemFactory implements ItemFactory {
        private static final StoredValue<String> NAME = new StoredValue<>(NAME_TAG, StoredValue.STRING, "Unknown Name");

        private ItemManager itemManager;

        public DefaultItemFactory(ItemManager itemManager) {
            this.itemManager = itemManager;
        }

        @Override
        public Item loadFromConfig(ConfigurationSection config) {
            // Load id, name, material, enchantments, unbreakable, type, and variables
            UUID uuid = config.isString(ID_TAG) ? itemManager.getItemId(config.getString(ID_TAG)) : UUID.randomUUID();
            Material material = Material.getMaterial(config.getString(MATERIAL_TAG));
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            if (config.isConfigurationSection(ENCHANTMENTS_TAG)) {
                ConfigurationSection enchants = config.getConfigurationSection(ENCHANTMENTS_TAG);
                for (String enchant : enchants.getKeys(false)) {
                    enchantments.put(Enchantment.getByName(enchant), enchants.getInt(enchant));
                }
            }
            boolean unbreakable = config.getBoolean(UNBREAKABLE_TAG, false);
            boolean stackable = config.getBoolean(STACKABLE_TAG, false);
            ItemType type = itemManager.getItemType(config.getString(TYPE_TAG, "Unknown"));
            VariableContainer variables = BaseVariableContainer.loadFromConfig(config);
            Group attributes = new GroupAttribute(null, ATTRIBUTES_TAG, new HashMap<>(), true);
            Value<String> name = NAME.loadFromConfig(variables, config);

            // Create item
            Item item = new CustomItem(uuid, name, material, enchantments, unbreakable, stackable, type, variables, attributes);

            // Load attributes
            Group loadedAttributes = DefaultAttributes.GROUP.getFactory().loadFromConfig(item, ATTRIBUTES_TAG, config);
            attributes.addAttribute(loadedAttributes.getAttributes().toArray(new Attribute[0]));

            return item;
        }

        @Override
        public Item loadFromNBT(NBTTagCompound compound) {
            // Check if compound already has a loaded item instance
            if (compound.hasKey(INSTANCE_TAG)) {
                Object itemInstance = compound.getObject(INSTANCE_TAG);
                if (itemInstance != null) {
                    return (Item) itemInstance;
                }
            }

            // Load id, name, material, enchantments, unbreakable, type, and attributes
            UUID uuid = UUID.fromString(compound.getString(UUID_TAG));
            Material material = Material.getMaterial(compound.getString(MATERIAL_TAG));
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            NBTTagCompound enchants = compound.getCompound(ENCHANTMENTS_TAG);
            for (String enchant : enchants.getKeySet()) {
                enchantments.put(Enchantment.getByName(enchant), enchants.getInt(enchant));
            }
            boolean unbreakable = compound.getBoolean(UNBREAKABLE_TAG);
            boolean stackable = compound.getBoolean(STACKABLE_TAG);
            ItemType type = itemManager.getItemType(compound.getString(TYPE_TAG));
            VariableContainer variables = BaseVariableContainer.loadFromNBT(compound);
            Group attributes = new GroupAttribute(null, ATTRIBUTES_TAG, new HashMap<>(), true);
            Value<String> name = NAME.loadFromNBT(variables, compound);

            // Create item
            Item item = new CustomItem(uuid, name, material, enchantments, unbreakable, stackable, type, variables, attributes);

            // Load attributes
            Group loadedAttributes = DefaultAttributes.GROUP.getFactory().loadFromNBT(item, ATTRIBUTES_TAG, compound);
            attributes.addAttribute(loadedAttributes.getAttributes().toArray(new Attribute[0]));

            // Store a loaded item instance on the compound for performance
            compound.setObject(INSTANCE_TAG, item);

            return item;
        }

        @Override
        public Item loadFromItemStack(ItemStack itemStack) {
            // ItemStack is definitely not a custom item
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return null;
            }

            // Get ItemStack NBT
            NBTTagCompound compound = NMSHandler.instance().getTag(itemStack);

            // Ensure ItemStack is a custom item
            if (!compound.hasKey(ITEM_TAG)) {
                return null;
            }

            // Get custom item compound
            NBTTagCompound itemTag = compound.getCompound(ITEM_TAG);

            // Load from NBT
            Item item = loadFromNBT(itemTag);

            // Set ItemStack NBT
            NMSHandler.instance().setTag(itemStack, compound);

            return item;
        }

        @Override
        public boolean isItem(ItemStack itemStack) {
            // ItemStack is definitely not a custom item
            if (itemStack == null || itemStack.getType() == Material.AIR) {
                return false;
            }

            NBTTagCompound compound = NMSHandler.instance().getTag(itemStack);

            // Check if compound has relics item tag
            return compound.hasKey(ITEM_TAG);
        }
    }

}
