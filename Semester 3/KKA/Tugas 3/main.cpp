#include <bits/stdc++.h>
#include <chrono>

using namespace std;
/*
    [2]   w   w   w
    [1]   w   w   w
    [0]   w   w   w
         [0] [1] [2]

    0 : Air (.)
    1 : Obstacle (w)

    path (x)
    start (s)
    target (t)
*/

// Other
template <typename T>
bool containsOnVector(vector<T, allocator<T>> *list, T data)
{
    for (int i = 0; i < list->size(); i++)
    {
        if ((*list)[i] == data)
        {
            return true;
        }
    }
    return false;
}
template <typename T, typename K>
class pairHasher
{
public:
    size_t operator()(const pair<T, K> &p) const
    {
        size_t hash1 = hash<T>{}(p.first);
        size_t hash2 = hash<K>{}(p.second);
        return hash1 ^ (hash2 << 1);
    }
};

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
        return x >= 0 && y >= 0 && x < width && y < height;
    }
    int getTile(int x, int y)
    {
        if (withinBounds(x, y))
        {
            return tiles[y][x];
        }
        return -1;
    }
    int setTile(int x, int y, int tile)
    {
        if (withinBounds(x, y))
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

        for (int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for (int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for (int x = 0; x < width; x++)
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

        for (int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for (int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for (int x = 0; x < width; x++)
            {
                if (tiles[y][x] == 1)
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

        for (int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for (int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for (int x = 0; x < width; x++)
            {
                if (start == make_pair(x, y))
                {
                    cout << "s" << " ";
                }
                else if (target == make_pair(x, y))
                {
                    cout << "t" << " ";
                }
                else if (tiles[y][x] == 1)
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
    void printTilesAliasStartEndPath(pair<int, int> start, pair<int, int> target, vector<pair<int, int>> &path)
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for (int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for (int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for (int x = 0; x < width; x++)
            {
                if (start == make_pair(x, y))
                {
                    cout << "s" << " ";
                }
                else if (target == make_pair(x, y))
                {
                    cout << "t" << " ";
                }
                else if (containsOnVector<pair<int, int>>(&path, make_pair(x, y)))
                {
                    cout << "x" << " ";
                }
                else if (tiles[y][x] == 1)
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
    void printTilesAliasStartEndPathExplored(pair<int, int> start, pair<int, int> target, vector<pair<int, int>> &path)
    {
        int startY = height - 1;
        int addSpace = height / 10;

        for (int y = startY; y >= 0; y--)
        {
            cout << "[" << y << "] ";

            int currentAddSpace = addSpace - (y / 10);
            for (int i = 0; i < currentAddSpace; i++)
            {
                cout << " ";
            }
            cout << "| ";
            for (int x = 0; x < width; x++)
            {
                if (start == make_pair(x, y))
                {
                    cout << "s" << " ";
                }
                else if (target == make_pair(x, y))
                {
                    cout << "t" << " ";
                }
                else if (containsOnVector<pair<int, int>>(&path, make_pair(x, y)))
                {
                    cout << "x" << " ";
                }
                else if (tiles[y][x] == 1)
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

grid *generateDefaultGrid()
{
    grid *data = new grid(20, 20);
    // Reset
    for (int x = 0; x < data->getWidth(); x++)
    {
        for (int y = 0; y < data->getHeight(); y++)
        {
            data->fastSetTile(x, y, 0);
        }
    }

    return data;
}

grid *readFromTxt(string path)
{
    fstream file(path);
    if (!file.is_open())
    {
        cerr << "Error openning file" << endl;
        return nullptr;
    }

    int width = 0;
    int height = 0;
    file >> width >> height;

    grid *data = new grid(width, height);

    for (int y = height - 1; y >= 0; y--)
    {
        for (int x = 0; x < width; x++)
        {
            int tile;
            file >> tile;

            data->setTile(x, y, tile);
        }
    }

    return data;
}

double getHeuristic(pair<int, int> current, pair<int, int> target)
{
    int x = target.first - current.first;
    int y = target.second - current.second;
    return sqrt((double)(x * x + y * y));
}
double NORMAL_COST = 1.0;
double DIAGONAL_COST = getHeuristic(make_pair(0, 0), make_pair(1, 1));

void greedyBestFirstSearch(grid &data, pair<int, int> start, pair<int, int> target, double &outputCost, vector<pair<int, int>> &path)
{
    priority_queue<pair<double, pair<int, int>>, vector<pair<double, pair<int, int>>>, greater<pair<double, pair<int, int>>>> queue;
    map<pair<int, int>, int> visited;
    pair<int, int> neighbor;
    map<pair<int, int>, pair<int, int>> pred;

    double weight = getHeuristic(start, target);
    queue.push(make_pair(weight, start));
    visited[start] = 1;

    while (queue.top().second != target && !queue.empty())
    {
        int x = queue.top().second.first;
        int y = queue.top().second.second;
        queue.pop();

        vector<pair<int, int>> directions = {
            {0, 1},   // atas
            {1, 1},   // diagonal atas kanan
            {1, 0},   // kanan
            {1, -1},  // diagonal kanan bawah
            {0, -1},  // bawah
            {-1, -1}, // diagonal kiri bawah
            {-1, 0},  // kiri
            {-1, 1}   // diagonal kiri atas
        };

        for (auto direction : directions)
        {
            int newX = x + direction.first;
            int newY = y + direction.second;

            if (data.getTile(newX, newY) == 0)
            {
                neighbor = make_pair(newX, newY);
                if (visited.find(neighbor) == visited.end())
                {
                    weight = getHeuristic(neighbor, target);
                    queue.push(make_pair(weight, neighbor));

                    visited[neighbor] = 1;
                    pred[neighbor] = {x, y};
                }
            }
        }
    }

    pair<int, int> key = target;
    while (key != start)
    {
        path.insert(path.begin(), key);
        if (pred[key].first == key.first || pred[key].second == key.second)
        {
            outputCost += NORMAL_COST;
        }
        else
        {
            outputCost += DIAGONAL_COST;
        }
        key = pred[key];
    }
}

// AStar
struct aStarNode
{
public:
    pair<int, int> from;
    pair<int, int> pos;
    double gCost;
    double fCost;

    aStarNode()
    {
    }
    aStarNode(pair<int, int> from, pair<int, int> pos, double gCost, double fCost)
    {
        this->from = from;
        this->pos = pos;
        this->gCost = gCost;
        this->fCost = fCost;
    }
    bool operator()(const aStarNode &left, const aStarNode &right)
    {
        if (left.fCost > right.fCost)
        {
            return true;
        }
        else if (left.fCost == right.fCost)
        {
            return left.gCost >= right.gCost;
        }

        return false;
    }
    bool compareWithoutEquals(const aStarNode &left, const aStarNode &right)
    {
        if (left.fCost > right.fCost)
        {
            return true;
        }
        else if (left.fCost == right.fCost)
        {
            return left.gCost > right.gCost;
        }

        return false;
    }
};
void addAStarNode(grid &data, aStarNode current, int xAdder, int yAdder, pair<int, int> target, unordered_map<pair<int, int>, aStarNode, pairHasher<int, int>> &assigned, priority_queue<aStarNode, vector<aStarNode>, aStarNode> &openSet, unordered_set<pair<int, int>, pairHasher<int, int>> &visited)
{
    pair<int, int> toPos = make_pair(current.pos.first + xAdder, current.pos.second + yAdder);

    int tile = data.getTile(toPos.first, toPos.second);
    if (tile == -1 || tile == 1)
    {
        return;
    }
    if (visited.find(toPos) != visited.end())
    {
        return;
    }

    double addCost = NORMAL_COST;
    if (xAdder != 0 && yAdder != 0)
    {
        addCost = DIAGONAL_COST;
    }

    double newGCost = current.gCost + addCost;
    double newFCost = newGCost + getHeuristic(toPos, target);
    aStarNode newNode = aStarNode(current.pos, toPos, newGCost, newFCost);

    if (assigned.find(toPos) != assigned.end())
    {
        if (!current.compareWithoutEquals(assigned[toPos], newNode))
        {
            return;
        }
    }

    assigned[toPos] = newNode;
    openSet.push(newNode);
}
void aStar(grid &data, pair<int, int> start, pair<int, int> target, double &outputCost, vector<pair<int, int>> &path)
{
    unordered_map<pair<int, int>, aStarNode, pairHasher<int, int>> assigned;
    priority_queue<aStarNode, vector<aStarNode>, aStarNode> openSet;
    unordered_set<pair<int, int>, pairHasher<int, int>> visited;

    aStarNode startNode = aStarNode(start, start, 0.0, getHeuristic(start, target));
    assigned[start] = startNode;
    openSet.push(startNode);

    bool found = false;
    while (!openSet.empty())
    {
        aStarNode current = openSet.top();
        openSet.pop();

        if (!current(assigned[current.pos], current))
        {
            continue;
        }

        visited.insert(current.pos);

        if (current.pos == target)
        {
            found = true;
            break;
        }

        addAStarNode(data, current, -1, 0, target, assigned, openSet, visited);
        addAStarNode(data, current, -1, 1, target, assigned, openSet, visited);
        addAStarNode(data, current, 0, 1, target, assigned, openSet, visited);
        addAStarNode(data, current, 1, 1, target, assigned, openSet, visited);
        addAStarNode(data, current, 1, 0, target, assigned, openSet, visited);
        addAStarNode(data, current, 1, -1, target, assigned, openSet, visited);
        addAStarNode(data, current, 0, -1, target, assigned, openSet, visited);
        addAStarNode(data, current, -1, -1, target, assigned, openSet, visited);
    }

    if (found)
    {
        outputCost = assigned[target].gCost;

        pair<int, int> current = target;
        while (current != start)
        {
            path.push_back(current);
            current = assigned[current].from;
        }
        path.push_back(start);
    }
    else
    {
        outputCost = -1.0;
    }
}

// IDA*
struct idAStarNode
{
    public:
    pair<int, int> from;
    pair<int, int> pos;
    double gCost;
    double fCost;

    idAStarNode()
    {

    }
    idAStarNode(pair<int, int> from, pair<int, int> pos, double gCost, double fCost)
    {
        this->from = from;
        this->pos = pos;
        this->gCost = gCost;
        this->fCost = fCost;
    }
};
void addIdAStarNode(grid &data, idAStarNode current, int xAdder, int yAdder, pair<int, int> target, unordered_map<pair<int, int>, idAStarNode, pairHasher<int, int>> &assigned, stack<idAStarNode> &openSet)
{
    pair<int, int> toPos = make_pair(current.pos.first + xAdder, current.pos.second + yAdder);

    int tile = data.getTile(toPos.first, toPos.second);
    if (tile == -1 || tile == 1)
    {
        return;
    }
    if (assigned.find(toPos) != assigned.end())
    {
        return;
    }

    double addCost = NORMAL_COST;
    if (xAdder != 0 && yAdder != 0)
    {
        addCost = DIAGONAL_COST;
    }

    double newGCost = current.gCost + addCost;
    double newFCost = newGCost + getHeuristic(toPos, target);
    idAStarNode newNode = idAStarNode(current.pos, toPos, newGCost, newFCost);

    assigned[toPos] = newNode;
    openSet.push(newNode);
}
void idAStar(grid &data, pair<int, int> start, pair<int, int> target, double &outputCost, vector<pair<int, int>> &path)
{
    double threshold = getHeuristic(start, target);
    double newThreshold = threshold;
    bool foundPath = false;
    outputCost = -1.0;

    while (!foundPath && threshold != -1.0)
    {
        unordered_map<pair<int, int>, idAStarNode, pairHasher<int, int>> assigned;
        stack<idAStarNode> openSet;

        idAStarNode startNode = idAStarNode(start, start, 0.0, getHeuristic(start, target));
        assigned[start] = startNode;
        openSet.push(startNode);
        newThreshold = -1.0;

        while (!openSet.empty())
        {
            idAStarNode current = openSet.top();
            openSet.pop();

            if (current.pos == target)
            {
                foundPath = true;
                break;
            }
            if (current.fCost > threshold)
            {
                if (newThreshold == -1.0)
                {
                    newThreshold = current.fCost;
                }
                else if (current.fCost < newThreshold)
                {
                    newThreshold = current.fCost;
                }
                continue;
            }

            addIdAStarNode(data, current, -1, 0, target, assigned, openSet);
            addIdAStarNode(data, current, -1, 1, target, assigned, openSet);
            addIdAStarNode(data, current, 0, 1, target, assigned, openSet);
            addIdAStarNode(data, current, 1, 1, target, assigned, openSet);
            addIdAStarNode(data, current, 1, 0, target, assigned, openSet);
            addIdAStarNode(data, current, 1, -1, target, assigned, openSet);
            addIdAStarNode(data, current, 0, -1, target, assigned, openSet);
            addIdAStarNode(data, current, -1, -1, target, assigned, openSet);
        }

        threshold = newThreshold;

        if (foundPath)
        {
            outputCost = assigned[target].gCost;

            pair<int, int> current = target;
            while (current != start)
            {
                path.push_back(current);
                current = assigned[current].from;
            }
            path.push_back(start);
            break;
        }
    }
}

//Monitoring
chrono::_V2::system_clock::time_point getCurrentTime()
{
    return chrono::high_resolution_clock::now();
}
double calculateMS(chrono::_V2::system_clock::time_point start, chrono::_V2::system_clock::time_point end)
{
    chrono::duration<double> duration = end - start;
    return chrono::duration_cast<chrono::nanoseconds>(duration).count() / 1000000.0;
}

int main()
{
    grid *data = readFromTxt("./tile.txt");

    pair<int, int> start = make_pair(11, 0);
    pair<int, int> target = make_pair(18, 14);

    cout << "GBFS" << endl;
    double gfsCost = 0;
    vector<pair<int, int>> gfsPath = vector<pair<int, int>>();

    auto startGBFS = getCurrentTime();
    greedyBestFirstSearch(*data, start, target, gfsCost, gfsPath);
    auto endGBFS = getCurrentTime();

    data->printTilesAliasStartEndPath(start, target, gfsPath);
    cout << gfsCost << ", Runtime " << calculateMS(startGBFS, endGBFS) << "ms" << endl
         << endl;

    cout << "AStar" << endl;
    double aStarCost = 0;
    vector<pair<int, int>> aStarPath = vector<pair<int, int>>();

    auto startAStar = getCurrentTime();
    aStar(*data, start, target, aStarCost, aStarPath);
    auto endAStar = getCurrentTime();

    data->printTilesAliasStartEndPath(start, target, aStarPath);
    cout << aStarCost << ", Runtime " << calculateMS(startAStar, endAStar) << "ms" << endl
         << endl;

    cout << "IdAStar" << endl;
    double idAStarCost = 0;
    vector<pair<int, int>> idAStarPath = vector<pair<int, int>>();

    auto startIDAStar = getCurrentTime();
    idAStar(*data, start, target, idAStarCost, idAStarPath);
    auto endIDAStar = getCurrentTime();

    data->printTilesAliasStartEndPath(start, target, idAStarPath);
    cout << idAStarCost << ", Runtime " << calculateMS(startIDAStar, endIDAStar) << endl
         << endl;

    return 0;
}