#include <bits/stdc++.h>

using namespace std;

pair<int, int> make_lower(int left, int right)
{
    if(left < right)
    {
        return make_pair(left, right);
    }

    return make_pair(right, left);
}

template <class T1, class T2>
struct pair_hasher
{
    std::size_t operator()(const std::pair<T1, T2>& p) const
    {
        auto hash1 = std::hash<T1>{}(p.first);
        auto hash2 = std::hash<T2>{}(p.second);
        return hash1 ^ (hash2 << 1);
    }
};

class graph
{
    private:
    vector<vector<pair<int, int>>> edges;
    int vertexCount;
    int edgeCount;

    public:
    int getEdgeCount()
    {
        return edgeCount;
    }
    graph(int vertexCount)
    {
        this->vertexCount = vertexCount;
        edges = vector<vector<pair<int, int>>>(this->vertexCount);

        edgeCount = 0;
    }
    void addEdge(int left, int right, int cost)
    {
        //Undirected graph
        edges[left].push_back(make_pair(right, cost));
        edges[right].push_back(make_pair(left, cost));

        edgeCount++;
    }
    int bellmanHeldKarp(vector<int>& path)
    {
        vector<vector<int>> costMatrix = vector<vector<int>>(vertexCount, vector<int>(vertexCount, INFINITY));
        for(int i = 0; i < edges.size(); i++)
        {
            for(int j = 0; j < edges[i].size(); j++)
            {
                pair<int, int> target = edges[i][j];
                costMatrix[i][target.first] = target.second;
            }
        }

        //The cost of visit from i.
        vector<vector<int>> memo = vector<vector<int>>(vertexCount, vector<int>(1 << vertexCount, INFINITY));
        vector<vector<int>> parent = vector<vector<int>>(vertexCount, vector<int>(1 << vertexCount, -1));

        for(int i = 0; i < vertexCount; i++)
        {
            memo[i][1 << i] = 0;
            parent[i][1 << i] = i;
        }
        for(int mask = 1; mask < (1 << vertexCount); mask++)
        {
            for(int i = 0; i < vertexCount; i++)
            {
                if((mask & (1 << i)) == 0)
                {
                    continue;
                }
                for(int j = 0; j < vertexCount; j++)
                {
                    if(i != j && (mask & (1 << j)))
                    {
                        int newCost = memo[j][mask & ~(1 << i)] + costMatrix[j][i];
                        cout << " " << mask << " " << i << " " << j << " " << newCost << endl;

                        if(newCost < memo[i][mask])
                        {
                            cout << "Set : " << mask << " " << i << " " << j << " " << newCost << endl;
                            memo[i][mask] = newCost;
                            parent[i][mask] = j;
                        }
                    }
                }
            }
        }

        int visitedMask = (1 << vertexCount) - 1;
        int returnCost = INFINITY;
        int lastCity = -1;
        int firstCityAdd = -1;

        for(int i = 0; i < vertexCount; i++)
        {
            int firstCity = i;
            int checkMask = (1 << vertexCount) - 1;
            while(checkMask != 0)
            {
                int tempCity = parent[firstCity][checkMask];
                checkMask ^= (1 << firstCity);
                firstCity = tempCity;
            }

            int cost = memo[i][visitedMask] + costMatrix[i][firstCity];
            if(cost < returnCost)
            {
                returnCost = cost;
                lastCity = i;
                firstCityAdd = firstCity;
            }
        }

        path.push_back(firstCityAdd);
        while(visitedMask != 0)
        {
            path.push_back(lastCity);
            int tempCity = parent[lastCity][visitedMask];
            visitedMask ^= (1 << lastCity);
            lastCity = tempCity;
        }

        return returnCost;
    }
};

int main()
{
    const int VERTEX_COUNT = 6;

    graph graphObj = graph(VERTEX_COUNT);

    graphObj.addEdge(0, 1, 60);
    graphObj.addEdge(0, 2, 56);
    graphObj.addEdge(0, 3, 51);
    graphObj.addEdge(0, 4, 35);
    graphObj.addEdge(0, 5, 2);

    graphObj.addEdge(1, 2, 70);
    graphObj.addEdge(1, 3, 13);
    graphObj.addEdge(1, 4, 68);
    graphObj.addEdge(1, 5, 61);

    graphObj.addEdge(2, 3, 78);
    graphObj.addEdge(2, 4, 21);
    graphObj.addEdge(2, 5, 57);

    graphObj.addEdge(3, 4, 68);
    graphObj.addEdge(3, 5, 51);

    graphObj.addEdge(4, 5, 36);

    vector<int> tspPath;
    cout << graphObj.bellmanHeldKarp(tspPath) << " " << tspPath.size() << endl;
    for(int i = tspPath.size() - 1; i >= 0; i--)
    {
        cout << tspPath[i] << " ";
    }

    return 0;
}