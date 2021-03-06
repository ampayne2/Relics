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
package com.herocraftonline.items.crafting.recipe.ingredient;

import com.herocraftonline.items.api.crafting.Ingredient;
import com.herocraftonline.items.api.crafting.ReagentType;
import org.bukkit.inventory.ItemStack;

public class BasicIngredient implements Ingredient {

    private final ReagentType reagent;
    private final int amount;

    public BasicIngredient(ReagentType reagent, int amount) {
        this.reagent = reagent;
        this.amount = amount;
    }

    @Override
    public ReagentType getType() {
        return reagent;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return itemStack != null && getType().test(itemStack) && itemStack.getAmount() == getAmount();
    }

    @Override
    public String toString() {
        return getType() + " x" + getAmount();
    }

}
