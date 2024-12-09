package com.pbo.minefield.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbo.minefield.Assets;
import com.pbo.minefield.Game;

public class Defuser extends Cell {
    public Defuser(Game game) {
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
            region = Assets.getDefuserTexture();
        }

        game.drawCell(batch, region, x, y);
    }
    @Override
    protected void onChoosedCallback() {
        for(int x = -2; x <= 2; x++)
        {
            for(int y = -2; y <= 2; y++)
            {
                Cell cell = game.getCellAt(this.x + x, this.y + y);

                if(cell == null || cell.choosed)
                {
                    continue;
                }
                if(cell instanceof Defuser)
                {
                    continue;
                }
                if(cell instanceof Mine)
                {
                    Mine mine = (Mine) cell;
                    mine.defused = true;
                }
                cell.setChoosed();
            }
        }
    }
}
