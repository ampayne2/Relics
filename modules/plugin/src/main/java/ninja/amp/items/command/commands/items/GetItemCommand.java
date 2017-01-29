/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017 <http://github.com/ampayne2/AmpItems//>
 *
 * AmpItems is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AmpItems is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with AmpItems.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninja.amp.items.command.commands.items;

import ninja.amp.items.AmpItems;
import ninja.amp.items.command.Command;
import ninja.amp.items.config.Config;
import ninja.amp.items.config.ConfigManager;
import ninja.amp.items.config.ItemConfig;
import ninja.amp.items.item.Item;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;
import java.util.List;

public class GetItemCommand extends Command {

    public GetItemCommand(AmpItems plugin) {
        super(plugin, "get");
        setDescription("Spawn a custom attribute item into your inventory.");
        setCommandUsage("/aitem item get <item>");
        setPermission(new Permission("ampitems.item.get", PermissionDefault.OP));
        setArgumentRange(1, 1);
        setPlayerOnly(true);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        ConfigManager configManager = plugin.getConfigManager();

        String itemName = args.get(0);
        Config itemConfig = new ItemConfig(itemName);
        File file = new File(plugin.getDataFolder(), itemConfig.getFileName());
        if (!file.exists()) {
            configManager.registerCustomConfig(itemConfig, plugin);
        }
        FileConfiguration config = configManager.getConfig(itemConfig);
        Item item = Item.DEFAULT_FACTORY.loadFromConfig(config);

        player.getInventory().addItem(item.getItem());
    }

}
