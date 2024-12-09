package com.pbo.minefield;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static TextureRegion explodeTexture;
    private static TextureRegion mineTexture;
    private static TextureRegion notChoosedCellTexture;
    private static TextureRegion[] choosedCellTextures;
    private static TextureRegion[] fontTextures;
    private static TextureRegion defuserTexture;
    private static TextureRegion mineDefusedTexture;
    private static TextureRegion choosingTexture;
    private static TextureRegion whiteTexture;
    private static TextureRegion flagTexture;
    private static Texture winTexture;
    private static Texture loseTexture;
    private static TextureRegion[] retryButtonTexture;

    public static void load()
    {
        explodeTexture = createSingleTextureRegion("explode.png", 16, 0);
        mineTexture = createSingleTextureRegion("mine.png", 16, 0);
        notChoosedCellTexture = createSingleTextureRegion("notChoosed.png", 16, 0);
        choosedCellTextures = createTextureRegions("choosed.png", 16);
        fontTextures = createTextureRegions("font.png", 16);
        defuserTexture = createSingleTextureRegion("defuser.png", 16, 0);
        choosingTexture = createSingleTextureRegion("choosing.png", 16, 0);
        whiteTexture = createSingleTextureRegion("white.png", 16, 0);
        mineDefusedTexture = createSingleTextureRegion("mineDefused.png", 16, 0);
        flagTexture = createSingleTextureRegion("flag.png", 16, 0);
        winTexture = new Texture(Gdx.files.internal("win.png"));
        loseTexture = new Texture(Gdx.files.internal("lose.png"));
        retryButtonTexture = createTextureRegions("reload.png", 32);
    }
    private static TextureRegion createSingleTextureRegion(String path, int cellSize, int index)
    {
        Texture texture = new Texture(Gdx.files.internal(path));
        int width = texture.getWidth() / cellSize;
        int height = texture.getHeight() / cellSize;
        int x = (index % width) * cellSize;
        int y = (int)(index / width) * cellSize;

        TextureRegion region = new TextureRegion(texture, x, y, cellSize, cellSize);

        return region;
    }
    private static TextureRegion[] createTextureRegions(String path, int cellSize)
    {
        Texture texture = new Texture(Gdx.files.internal(path));
        int width = texture.getWidth() / cellSize;
        int height = texture.getHeight() / cellSize;

        TextureRegion[] arr = new TextureRegion[width * height];
        int index = 0;
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                TextureRegion region = new TextureRegion(texture, x * cellSize, y * cellSize, cellSize, cellSize);
                arr[index] = region;

                index++;
            }
        }


        return arr;
    }
    public static TextureRegion getExplodeTexture() {
        return explodeTexture;
    }
    public static TextureRegion getMineTexture() {
        return mineTexture;
    }
    public static TextureRegion getNotChoosedCellTexture() {
        return notChoosedCellTexture;
    }
    public static TextureRegion getChoosedCellTextures(int index) {
        return choosedCellTextures[index];
    }
    public static TextureRegion getFontTextures(int index) {
        return fontTextures[index];
    }
    public static TextureRegion getDefuserTexture() {
        return defuserTexture;
    }
    public static TextureRegion getMineDefusedTexture()
    {
        return mineDefusedTexture;
    }
    public static TextureRegion getChoosingTexture()
    {
        return choosingTexture;
    }
    public static TextureRegion getWhiteTexture()
    {
        return whiteTexture;
    }
    public static TextureRegion getFlagTexture()
    {
        return flagTexture;
    }
    public static Texture getWinTexture()
    {
        return winTexture;
    }
    public static Texture getLoseTexture()
    {
        return loseTexture;
    }
    public static TextureRegion getRetryTexture(int index)
    {
        return retryButtonTexture[index];
    }
}
