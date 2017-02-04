/*
 * This file is part of AmpItems.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.commands;

import ninja.amp.items.api.ItemPlugin;
import ninja.amp.items.api.command.Command;
import ninja.amp.items.api.message.Messenger;
import ninja.amp.items.api.message.PageList;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * A command that lists all plugin commands.
 *
 * @author Austin Payne
 */
public class HelpCommand extends Command {

    public HelpCommand(ItemPlugin plugin) {
        super(plugin, "help");
        setDescription("Lists all amp items commands.");
        setCommandUsage("/aitem help [page]");
        setPermission(new Permission("ampitems.help", PermissionDefault.TRUE));
        setArgumentRange(0, 1);
        setPlayerOnly(false);
    }

    @Override
    public void execute(String command, CommandSender sender, List<String> args) {
        int pageNumber = 1;
        if (args.size() == 1) {
            pageNumber = PageList.getPageNumber(args.get(0));
        }

        Messenger messenger = plugin.getMessenger();
        for (String line : plugin.getCommandController().getPageList().getPage(pageNumber)) {
            messenger.sendRawMessage(sender, line);
        }
    }

    @Override
    public List<String> tabComplete(List<String> args) {
        switch (args.size()) {
            case 1:
                return tabCompletions(args.get(0), plugin.getCommandController().getPageList().getPageNumbersList());
            default:
                return EMPTY_LIST;
        }
    }

}
