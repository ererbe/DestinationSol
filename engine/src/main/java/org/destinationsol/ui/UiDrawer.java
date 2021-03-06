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
package org.destinationsol.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.destinationsol.CommonDrawer;
import org.destinationsol.assets.Assets;

public class UiDrawer {
    public enum TextAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public static final float FONT_SIZE = .02f;

    private final Matrix4 straightMtx;
    private final float uiLineWidth;

    public final float r;
    public final TextureRegion whiteTexture;
    public final Rectangle filler;
    private final CommonDrawer drawer;
    //TODO WTF is `isTextMode` for? discuss and potentially (=probably) remove
    private Boolean isTextMode;

    public UiDrawer(CommonDrawer commonDrawer) {
        drawer = commonDrawer;
        r = drawer.dimensionsRatio;
        whiteTexture = Assets.getAtlasRegion("engine:uiWhiteTex");
        uiLineWidth = 1 / drawer.height;
        straightMtx = new Matrix4().setToOrtho2D(0, 1, drawer.dimensionsRatio, -1);
        drawer.setMatrix(straightMtx);
        filler = new Rectangle(0, 0, r, 1);
    }

    public void updateMtx() {
        drawer.setMatrix(straightMtx);
    }

    public void drawString(String s, float x, float y, float scale, boolean centered, Color tint) {
        drawString(s, x, y, scale, TextAlignment.CENTER, centered, tint);
    }

    public void drawString(String s, float x, float y, float scale, TextAlignment align, boolean centered, Color tint) {
        if (isTextMode != null && !isTextMode) {
            throw new AssertionError("drawing text in atlas mode");
        }
        drawer.drawString(s, x, y, scale * FONT_SIZE, align, centered, tint);
    }

    private void check() {
        if (isTextMode != null && isTextMode) {
            throw new AssertionError("drawing atlas in text mode");
        }
    }

    public void draw(TextureRegion tr, float width, float height, float origX, float origY, float x, float y,
                     float rot, Color tint) {
        check();
        drawer.draw(tr, width, height, origX, origY, x, y, rot, tint);
    }

    public void draw(Rectangle rect, Color tint) {
        check();
        drawer.draw(whiteTexture, rect, tint);
    }

    public void drawCircle(Vector2 center, float radius, Color col) {
        check();
        drawer.drawCircle(whiteTexture, center, radius, col, uiLineWidth, 1);
    }

    public void drawLine(float x, float y, float angle, float len, Color col) {
        check();
        drawer.drawLine(whiteTexture, x, y, angle, len, col, uiLineWidth);
    }

    public void drawLine(Vector2 p1, Vector2 p2, Color col) {
        check();
        drawer.drawLine(whiteTexture, p1, p2, col, uiLineWidth, false);
    }

    public void setTextMode(Boolean textMode) {
        isTextMode = textMode;
    }
}
