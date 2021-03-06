/*
 * This file is part of Relics API.
 *
 * Copyright (c) 2017, Austin Payne <amperialdev@gmail.com - http://github.com/Amperial>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of Relics API,
 * via any medium is strictly prohibited.
 */
package com.herocraftonline.items.api.storage.config.transform;

import com.herocraftonline.items.api.ItemPlugin;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

/**
 * A config transform that formats strings in the configuration section with the given args.
 *
 * @author Austin Payne
 */
public class ArgTransform extends ConfigTransform {

    public ArgTransform(ItemPlugin plugin) {
        super(plugin);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConfigurationSection transform(ConfigurationSection section, Object[] args) {
        // Check all paths in config
        for (String key : section.getKeys(true)) {
            if (section.isString(key)) {
                // Format string with transform args
                String string = String.format(section.getString(key), args);

                // Replace string at config path
                setValue(section, key, string);
            } else if (section.isList(key)) {
                List<Object> list = (List<Object>) section.getList(key);
                for (int i = 0; i < list.size(); i++) {
                    Object element = list.get(i);
                    if (element instanceof String) {
                        // Format string with transform args
                        String string = String.format((String) element, args);

                        // Replace value at list index
                        setValue(list, i, string);
                    }
                }

                // Replace list at config path
                setValue(section, key, list);
            }
        }
        return section;
    }

}
