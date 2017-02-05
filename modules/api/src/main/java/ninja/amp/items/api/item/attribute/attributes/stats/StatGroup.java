/*
 * This file is part of AmpItems API.
 *
 * Copyright (c) 2017, Austin Payne <payneaustin5@gmail.com - http://github.com/ampayne2>
 *
 * All Rights Reserved.
 *
 * Unauthorized copying and/or distribution of AmpItems API,
 * via any medium is strictly prohibited.
 */
package ninja.amp.items.api.item.attribute.attributes.stats;

import ninja.amp.items.api.item.attribute.ItemLore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatGroup<T extends StatType<T>> implements ItemLore {

    private final Map<StatType<T>, Map<StatSpecifier<T>, StatTotal<T>>> stats;

    public StatGroup() {
        stats = new HashMap<>();
    }

    public void addStat(StatAttribute<T> stat) {
        T type = stat.getStatType();
        StatSpecifier<T> specifier = stat.getStatSpecifier();

        if (!stats.containsKey(type)) {
            stats.put(type, new HashMap<>());
        }
        Map<StatSpecifier<T>, StatTotal<T>> totals = stats.get(type);
        if (!totals.containsKey(specifier)) {
            totals.put(specifier, type.newTotal(specifier));
        }
        totals.get(specifier).addStat(stat);
    }

    @Override
    public void addTo(List<String> lore, String prefix) {
        stats.entrySet().stream()
                .forEach(stat -> {
                    Map<StatSpecifier<T>, StatTotal<T>> totals = stat.getValue().entrySet().stream()
                            .filter(value -> value.getValue().shouldAddLore())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    stat.getKey().addTo(lore, totals);
                });
    }

}
