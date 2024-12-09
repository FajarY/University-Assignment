package com.pbo.minefield.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pbo.minefield.*;

import java.util.HashSet;

public class Cell extends GameObject {
    public int x;
    public int y;
    protected boolean isMouseChoosed = false;
    protected boolean choosed = false;
    protected boolean zombie = false;

    public Cell(Game game) {
        super(game);
    }
    @Override
    public void awake() {
        super.awake();
    }
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update()
    {
        listenChoose();
    }
    protected void listenChoose()
    {
        if(game.getState() == Game.IDLE_STATE && !choosed)
        {
            if(Main.isTouchDown && game.getPointerCellPosition().x == x && game.getPointerCellPosition().y == y)
            {
                isMouseChoosed = true;

                setChoosed();
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion region = getDefaultCellTexture();

        if(choosed)
        {
            int mineCount = 0;
            for(int x = -1; x <= 1; x++)
            {
                for(int y = -1; y <= 1; y++)
                {
                    Cell cell = game.getCellAt(this.x + x, this.y + y);
                    if(cell == null)
                    {
                        continue;
                    }
                    if(cell instanceof Mine)
                    {
                        Mine mine = (Mine) cell;
                        if(mine.defused) continue;

                        mineCount++;
                    }
                }
            }
            region = Assets.getChoosedCellTextures(mineCount);
        }

        game.drawCell(batch, region, x, y);
    }
    protected TextureRegion getDefaultCellTexture()
    {
        TextureRegion region = Assets.getNotChoosedCellTexture();

        if(game.getState() == Game.IDLE_STATE)
        {
            if(!choosed && game.getPointerCellPosition().x == x && game.getPointerCellPosition().y == y)
            {
                region = Assets.getChoosingTexture();
            }
        }

        return region;
    }
    public void setZombie(boolean condition)
    {
        this.zombie = condition;
        onZombieCallback(condition);
    }
    protected void onZombieCallback(boolean condition)
    {

    }
    public void setChoosed()
    {
        if(this.choosed)
        {
            return;
        }

        this.choosed = true;
        onChoosedCallback();
    }
    protected void onChoosedCallback()
    {
        if(isMouseChoosed)
        {
            HashSet<Cell> chooseAlso = new HashSet<>();
            int randomAmount = game.getRandom().nextInt(0, 10);
            Vector2Int currentPos = new Vector2Int(x, y);

            while (randomAmount > 0)
            {
                randomAmount--;
                boolean skip = false;

                for(int x = -1; x <= 1; x++)
                {
                    if(skip) break;

                    for(int y = -1; y <= 1; y++)
                    {
                        if(skip) break;

                        Cell cell = game.getCellAt(currentPos.x + x, currentPos.y + y);

                        if(cell == null) continue;
                        if(cell instanceof Mine)
                        {
                            currentPos.x = this.x;
                            currentPos.y = this.y;
                            skip = true;
                        }
                    }
                }
                if(skip) continue;

                for(int x = -1; x <= 1; x++)
                {
                    for(int y = -1; y <= 1; y++)
                    {
                        Cell cell = game.getCellAt(currentPos.x + x, currentPos.y + y);

                        if(cell == null || cell instanceof Defuser || cell instanceof Mine || cell.choosed) continue;
                        chooseAlso.add(cell);
                    }
                }
                currentPos.x += game.getRandom().nextInt(-1, 2);
                currentPos.y += game.getRandom().nextInt(-1, 2);

                if(!game.withinBounds(currentPos.x, currentPos.y))
                {
                    currentPos.x = this.x;
                    currentPos.y = this.y;
                }
            }
            for(Cell cell : chooseAlso)
            {
                cell.setChoosed();
            }
        }
    }
    public boolean isChoosed()
    {
        return choosed;
    }
}
