package com.pbo.minefield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pbo.minefield.gameobjects.Cell;
import com.pbo.minefield.gameobjects.Defuser;
import com.pbo.minefield.gameobjects.Mine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Game {
    public static float drawScale = 16f;
    public static final float randomScale = 1000f;
    private Cell[][] cells;
    private int width;
    private int height;
    private float size;
    private int cellCount = 0;

    private HashSet<Cell> startCells = new HashSet<>();
    private Vector3 mousePosition = new Vector3();
    private Vector2Int mouseCellPosition = new Vector2Int();
    private boolean[][] flagged;

    public static final int IDLE_STATE = 0;
    public static final int WIN_STATE = 1;
    public static final int LOSE_STATE = 2;
    private boolean active = false;
    private int state;
    private float timer;
    private float freezeTimer;

    private Random random;

    public Game()
    {
        resetDefault();
    }
    public void resetDefault()
    {
        random = new Random();
        reset(30, 180f, random.nextInt(20, 40), random.nextInt(10, 25));
    }
    public void reset(int size, float timer, float mineCount, float defuserCount)
    {
        random = new Random();
        cells = new Cell[size][size];
        flagged = new boolean[size][size];
        this.size = (float)size;
        this.width = size;
        this.height = size;

        this.freezeTimer = 0f;
        this.active = true;
        this.state = IDLE_STATE;
        this.timer = timer;

        List<Cell> freeCells = new ArrayList<>();
        for(int x = 0; x < size; x++)
        {
            for(int y = 0; y < size; y++)
            {
                Cell cell;
                cell = new Cell(this);
                cellCount++;

                cells[x][y] = createCell(cell, x, y);
                freeCells.add(cell);
            }
        }

        while (freeCells.size() > 0 && mineCount > 0)
        {
            Cell cell = freeCells.get(random.nextInt(freeCells.size()));
            freeCells.remove(cell);

            Cell mine = createCell(new Mine(this), cell.x, cell.y);

            cellCount--;
            mineCount--;
        }
        while (freeCells.size() > 0 && defuserCount > 0)
        {
            Cell cell = freeCells.get(random.nextInt(freeCells.size()));
            freeCells.remove(cell);

            Cell defuser = createCell(new Defuser(this), cell.x, cell.y);

            defuserCount--;
        }
    }
    private void chooseAllCells()
    {
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < width; y++)
            {
                Cell cell = getCellAt(x, y);
                if(cell == null) continue;

                if(cell instanceof Mine)
                {
                    continue;
                }
                cell.setChoosed();
            }
        }
    }
    public void update()
    {
        mousePosition.x = Gdx.input.getX();
        mousePosition.y = Gdx.input.getY();
        mousePosition.z = 0;
        Main.getFitViewport().unproject(mousePosition);

        mouseCellPosition.x = (int)(mousePosition.x / drawScale);
        mouseCellPosition.y = (int)(mousePosition.y / drawScale);

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            //For testing purpose
            chooseAllCells();
        }

        if(state != IDLE_STATE && !active)
        {
            if(isHoveringReload() && Main.isTouchDown)
            {
                resetDefault();
            }

            return;
        }

        for(Cell cell : startCells)
        {
            cell.start();
        }
        startCells.clear();

        if(freezeTimer > 0f)
        {
            freezeTimer -= Gdx.graphics.getDeltaTime();
            if(freezeTimer <= 0f) active = false;
            return;
        }
        timer -= Gdx.graphics.getDeltaTime();
        if(timer <= 0f)
        {
            state = LOSE_STATE;
            freezeTimer = 3f;
            timer = 0f;
            return;
        }

        if(Main.isRightTouchDown && withinBounds(mouseCellPosition.x, mouseCellPosition.y))
        {
            flagged[mouseCellPosition.x][mouseCellPosition.y] = true;
        }

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Cell cell = getCellAt(x, y);
                if(cell == null) continue;

                cell.update();
            }
        }

        boolean win = true;
        for(int x = 0; x < width; x++)
        {
            if(!win) break;
            for(int y = 0; y < height; y++)
            {
                Cell cell = getCellAt(x, y);

                if(cell instanceof Mine)
                {
                    continue;
                }
                if(!cell.isChoosed())
                {
                    win = false;
                    break;
                }
            }
        }
        if(win)
        {
            state = Game.WIN_STATE;
            freezeTimer = 2f;
        }
    }
    public void preDraw(Camera camera)
    {
        drawScale = camera.viewportWidth / size;
    }
    public void draw(SpriteBatch batch, Camera camera)
    {
        if(freezeTimer > 0f)
        {
            batch.setColor(1f, 1f, 1f, 1f);
            float residu = freezeTimer % 1f;
            if(residu >= 0.5f)
            {
                if(state == WIN_STATE)
                {
                    batch.setColor(0.5f, 1f, 0.5f, 1f);
                }
                else
                {
                    batch.setColor(1f, 0.5f, 0.5f, 1f);
                }
            }

            batch.draw(Assets.getWhiteTexture(), 0f, 0f, camera.viewportWidth, camera.viewportHeight);
        }
        else
        {
            batch.draw(Assets.getWhiteTexture(), 0f, 0f, camera.viewportWidth, camera.viewportHeight);
        }

        batch.setColor(1f, 1f, 1f, 1f);
        //Headers
        if(active)
        {
            int timerLength = 1;
            float timerClone = timer;
            float div = 1f;
            while (timerClone >= 10f)
            {
                timerLength++;
                timerClone /= 10f;
                div *= 10f;
            }
            timerClone = timer;
            float fontSize = 75f;
            float startFontHorizontal = 500f - ((fontSize * timerLength) / 2f);
            for(int i = 0; i < timerLength; i++)
            {
                int index = (int) (timerClone / div) % 10;
                batch.draw(Assets.getFontTextures(index), startFontHorizontal + i * fontSize, 1012.5f, fontSize, fontSize);
                div /= 10f;
            }
        }
        else
        {
            if(state == WIN_STATE)
            {
                batch.draw(Assets.getWinTexture(), 0f, 1000f, 1000f, 100f);
            }
            else
            {
                batch.draw(Assets.getLoseTexture(), 0f, 1000f, 1000f, 100f);
            }
            TextureRegion retryRegion = Assets.getRetryTexture(0);
            if(isHoveringReload())
            {
                retryRegion = Assets.getRetryTexture(1);
            }

            batch.draw(retryRegion, 900f, 1000f, 100f, 100f);
        }

        //World
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Cell cell = getCellAt(x, y);
                if(cell == null) continue;

                cell.draw(batch);

                if(flagged[x][y] && !cell.isChoosed())
                {
                    drawCell(batch, Assets.getFlagTexture(), x, y);
                }
            }
        }
    }
    public boolean withinBounds(int x, int y)
    {
        return x >= 0 && y >= 0 && x < width && y < height;
    }
    public Cell getCellAt(int x, int y)
    {
        if(!withinBounds(x, y))
        {
            return null;
        }
        return cells[x][y];
    }
    public void destroyCell(int x, int y)
    {
        Cell cell = getCellAt(x, y);
        if(cell == null || cell.destroyed)
        {
            return;
        }
        cell.destroy();

        cells[x][y] = null;
    }
    public Cell createCell(Cell cell, int x, int y)
    {
        if(cell == null)
        {
            return null;
        }
        if(!withinBounds(x, y))
        {
            return null;
        }
        if(getCellAt(x, y) != null)
        {
            Cell oldCell = getCellAt(x, y);
            oldCell.destroy();
        }
        if(cell.destroyed)
        {
            return null;
        }

        cell.x = x;
        cell.y = y;
        cells[x][y] = cell;
        cell.awake();

        startCells.add(cell);

        return cell;
    }

    public void drawCell(SpriteBatch batch, TextureRegion region, float x, float y)
    {
        batch.draw(region, calculateDrawPosition(x), calculateDrawPosition(y), calculateDrawSize(1f), calculateDrawSize(1f));
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public float calculateDrawPosition(float position)
    {
        return position * drawScale;
    }
    public float calculateDrawSize(float size)
    {
        return size * drawScale;
    }
    public Vector2Int getPointerCellPosition()
    {
        return mouseCellPosition;
    }
    public int getState()
    {
        return state;
    }
    public Random getRandom()
    {
        return random;
    }
    public void setLose()
    {
        if(state != IDLE_STATE)
        {
            return;
        }

        state = LOSE_STATE;
        freezeTimer = 2f;
    }
    public boolean isHoveringReload()
    {
        return mousePosition.x >= 900f && mousePosition.x <= 1000f && mousePosition.y >= 1000f && mousePosition.y <= 1100f;
    }
}
