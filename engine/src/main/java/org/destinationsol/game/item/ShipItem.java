/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.destinationsol.game.item;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.destinationsol.game.SolGame;
import org.destinationsol.game.ship.hulls.HullConfig;

public class ShipItem implements SolItem {

    public static final SolItemType EMPTY = new SolItemType(new Color(), null, 1);
    private final HullConfig config;
    private final String description;

    ShipItem(HullConfig config) {
        this.config = config;
        description = makeDesc(config);
    }

    public static String makeDesc(HullConfig hull) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Takes ").append(hull.getMaxLife()).append(" dmg\n");
        boolean secondGunSlot = hull.getNrOfGunSlots() > 1;
        if (!secondGunSlot || hull.getGunSlot(0).allowsRotation() != hull.getGunSlot(1).allowsRotation()) {
            if (!secondGunSlot) {
                stringBuilder.append(!hull.getGunSlot(0).allowsRotation() ? "1 heavy gun slot\n" : "1 light gun slot\n");
            } else {
                stringBuilder.append("1 heavy + 1 light gun slots\n");
            }
        } else {
            stringBuilder.append(!hull.getGunSlot(0).allowsRotation() ? "2 heavy gun slots\n" : "2 light gun slots\n");
        }
        if (hull.getAbility() != null) {
            stringBuilder.append("Ability:\n");
            hull.getAbility().appendDesc(stringBuilder);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getDisplayName() {
        return config.getDisplayName();
    }

    @Override
    public float getPrice() {
        return config.getPrice();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SolItem copy() {
        return new ShipItem(config);
    }

    @Override
    public boolean isSame(SolItem item) {
        return item instanceof ShipItem && ((ShipItem) item).config == config;
    }

    @Override
    public TextureAtlas.AtlasRegion getIcon(SolGame game) {
        return config.getIcon();
    }

    @Override
    public SolItemType getItemType() {
        return EMPTY;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public int isEquipped() {
        return 0;
    }

    @Override
    public void setEquipped(int equipped) {

    }

    public HullConfig getConfig() {
        return config;
    }
}
