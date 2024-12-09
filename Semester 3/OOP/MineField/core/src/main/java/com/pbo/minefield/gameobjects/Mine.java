package com.pbo.minefield.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbo.minefield.Assets;
import com.pbo.minefield.Game;

public class Mine extends Cell {
    public boolean defused = false;
    public Mine(Game game) {
        super(game);
    }

    @Override
    public void update() {
        super.update();
    }
    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion region = getDefaultCellTexture();

        if(choosed)
        {
            region = Assets.getMineTexture();
            if(defused)
            {
                region = Assets.getMineDefusedTexture();
            }
        }

        game.drawCell(batch, region, x, y);
    }
    @Override
    protected void onChoosedCallback() {
        if(defused)
        {
            return;
        }
        game.setLose();
    }
}
