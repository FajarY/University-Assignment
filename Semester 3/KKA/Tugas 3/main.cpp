#include <bits/stdc++.h>

using namespace std;
/*
    [2]   w   w   w
    [1]   w   w   w
    [0]   w   w   w   
         [0] [1] [2]

    0 : Air (.)
    1 : Obstacle (w)

    path (x)
*/

//Other
template <typename T>
bool containsOnVector(vector<T, allocator<T>>* list, T data)
{
    for(int i = 0; i < list->size(); i++)
    {
        if((*list)[i] == data)
        {
            return true;
        }
    }
    return false;
}
class grid
{
    private:
    vector<vector<int>> tiles;
    int width;
    int height;

    public:
    grid(int width, int height)
    {
        this->width = width;
        this->height = height;

        tiles = vector<vector<int>>(height, vector<int>(width, 0));
    }
    bool withinBounds(int x, int y)
    {
        return x >= 0 && y >= 0 && x < width && x < height;
    }
    int getTile(int x, int y)
    {
        if(withinBounds(x, y))
        {
            return tiles[y][x];
        }
        return -1;
    }
    int setTile(int x, int y, int tile)
    {
        if(withinBounds(x, y))
        {
            tiles[y][x] = tile;
            return tile;
        }
        return -1;
    }
    void fastSetTile(int x, int y, int tile)
    {
        tiles[y][x] = tile;
    }
    void printTiles()
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for(int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for(int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for(int x = 0; x < width; x++)
            {
                cout << tiles[y][x] << " ";
            }
            cout << endl;
        }
    }
    void printTilesAlias()
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for(int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for(int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for(int x = 0; x < width; x++)
            {
                if(tiles[y][x] == 1)
                {
                    cout << "x" << " ";
                }
                else
                {
                    cout << ". ";
                }
            }
            cout << endl;
        }
    }
    void printTilesAliasStartEnd(pair<int, int> start, pair<int, int> target)
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for(int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for(int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for(int x = 0; x < width; x++)
            {
                if(start == make_pair(x, y))
                {
                    cout << "s" << " ";
                }
                else if(target == make_pair(x, y))
                {
                    cout << "t" << " ";
                }
                else if(tiles[y][x] == 1)
                {
                    cout << "w" << " ";
                }
                else
                {
                    cout << ". ";
                }
            }
            cout << endl;
        }
    }
    void printTilesAliasStartEndPath(pair<int, int> start, pair<int, int> target, vector<pair<int, int>> path)
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for(int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for(int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for(int x = 0; x < width; x++)
            {
                if(start == make_pair(x, y))
                {
                    cout << "s" << " ";
                }
                else if(target == make_pair(x, y))
                {
                    cout << "t" << " ";
                }
                else if(containsOnVector<pair<int, int>>(&path, make_pair(x, y)))
                {
                    cout << "x" << " ";
                }
                else if(tiles[y][x] == 1)
                {
                    cout << "w" << " ";
                }
                else
                {
                    cout << ". ";
                }
            }
            cout << endl;
        }
    }
    int getWidth()
    {
        return width;
    }
    int getHeight()
    {
        return height;
    }
};

grid* generateDefaultGrid()
{
    grid* data = new grid(20, 20);
    //Reset
    for(int x = 0; x < data->getWidth(); x++)
    {
        for(int y = 0; y < data->getHeight(); y++)
        {
            data->fastSetTile(x, y, 0);
        }
    }

    return data;
}

grid* readFromTxt(string path)
{
    fstream file(path);
    if(!file.is_open())
    {
        cerr << "Error openning file" << endl;
        return nullptr;
    }

    int width = 0;
    int height = 0;
    file >> width >> height;

    grid* data = new grid(width, height);

    for(int y = height - 1; y >= 0; y--)
    {
        for(int x = 0; x < width; x++)
        {
            int tile;
            file >> tile;

            data->setTile(x, y, tile);
        }
    }

    return data;
}

void greedyBestFirstSearch(grid& data, pair<int, int> start, pair<int, int> target, int& outputCost, vector<pair<int, int>>& path)
{
    
}
void aStar(grid& data, pair<int, int> start, pair<int, int> target, int& outputCost, vector<pair<int, int>>& path)
{

}
void idAStar(grid& data, pair<int, int> start, pair<int, int> target, int& outputCost, vector<pair<int, int>>& path)
{

}

int main()
{
    grid* data = readFromTxt("./tile.txt");

    pair<int, int> start = make_pair(11, 0);
    pair<int, int> target = make_pair(18, 14);
    
    cout << "GFS" << endl;
    int gfsCost = 0;
    vector<pair<int, int>> gfsPath = vector<pair<int, int>>();
    greedyBestFirstSearch(*data, start, target, gfsCost, gfsPath);
    data->printTilesAliasStartEndPath(start, target, gfsPath);
    cout << endl << endl;

    cout << "AStar" << endl;
    int aStarCost = 0;
    vector<pair<int, int>> aStarPath = vector<pair<int, int>>();
    aStar(*data, start, target, aStarCost, aStarPath);
    data->printTilesAliasStartEndPath(start, target, aStarPath);
    cout << endl << endl;

    cout << "IdAStar" << endl;
    int idAStarCost = 0;
    vector<pair<int, int>> idAStarPath = vector<pair<int, int>>();
    idAStar(*data, start, target, idAStarCost, idAStarPath);
    data->printTilesAliasStartEndPath(start, target, idAStarPath);
    cout << endl << endl;

    return 0;
}