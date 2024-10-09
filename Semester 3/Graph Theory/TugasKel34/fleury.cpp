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
    int listSize;
    int vertexCount;
    int edgeCount;

    private:
    bool isEulerian(vector<int>& outStartPosition)
    {
        vector<int> oddEdges;

        for(int i = 1; i <= vertexCount; i++)
        {
            int count = edges[i].size();

            if(count == 0)
            {
                return false;
            }
            if((count % 2) != 0)
            {
                oddEdges.push_back(i);
            }
        }

        if(oddEdges.size() != 0)
        {
            if(oddEdges.size() != 2)
            {
                return false;
            }
            else
            {
                for(int i = 0; i < oddEdges.size(); i++)
                {
                    outStartPosition.push_back(oddEdges[i]);
                }
            }
        }
        else
        {
            for(int i = 1; i <= vertexCount; i++)
            {
                outStartPosition.push_back(i);
            }
        }

        return true;
    }
    bool isBridge(vector<vector<pair<int, int>>>& edgesModified, int from, int to)
    {
        stack<int> openSet;
        unordered_set<pair<int, int>, pair_hasher<int, int>> closedSet;

        closedSet.insert(make_lower(from, to));
        openSet.push(to);

        while (!openSet.empty())
        {
            int current = openSet.top();
            openSet.pop();

            for(int i = 0; i < edgesModified[current].size(); i++)
            {
                int target = edgesModified[current][i].first;
                pair<int, int> hash = make_lower(current, target);

                if(closedSet.find(hash) != closedSet.end())
                {
                    continue;
                }
                if(target == from)
                {
                    return false;
                }

                closedSet.insert(hash);
                openSet.push(target);                
            }   
        }

        return true;
    }
    void removeEdge(vector<vector<pair<int, int>>>& edges, int start, int end)
    {
        for(int i = 0; i < edges[start].size(); i++)
        {
            if(edges[start][i].first == end)
            {
                edges[start].erase(edges[start].begin() + i);
                break;
            }
        }
        for(int i = 0; i < edges[end].size(); i++)
        {
            if(edges[end][i].first == start)
            {
                edges[end].erase(edges[end].begin() + i);
                break;
            }
        }
    }

    public:
    int getEdgeCount()
    {
        return edgeCount;
    }
    graph(int vertexCount)
    {
        this->vertexCount = vertexCount;
        this->listSize = vertexCount + 1;
        edges = vector<vector<pair<int, int>>>(this->listSize);

        edgeCount = 0;
    }
    void addEdge(int left, int right, int cost)
    {
        //Undirected graph
        edges[left].push_back(make_pair(right, cost));
        edges[right].push_back(make_pair(left, cost));

        edgeCount++;
    }
    int fleury(vector<int>& outPath)
    {
        vector<int> avalaibleVertex;
        if(!isEulerian(avalaibleVertex))
        {
            return -1;
        }

        int currentVertex = avalaibleVertex[0];

        vector<vector<pair<int, int>>> fleuryEdges = edges;

        int cost = 0;

        while (true)
        {
            outPath.push_back(currentVertex);
            int degree = fleuryEdges[currentVertex].size();

            if(degree == 0)
            {
                break;
            }
            if(degree == 1)
            {
                pair<int, int> targetEdge = fleuryEdges[currentVertex][0];

                removeEdge(fleuryEdges, currentVertex, targetEdge.first);
                
                currentVertex = targetEdge.first;
                cost += targetEdge.second;
            }
            else
            {
                bool found = false;
                for(int i = 0; i < fleuryEdges[currentVertex].size(); i++)
                {
                    pair<int, int> target = fleuryEdges[currentVertex][i];
                    if(!isBridge(fleuryEdges, currentVertex, target.first))
                    {
                        removeEdge(fleuryEdges, currentVertex, target.first);
                        currentVertex = target.first;

                        cost += target.second;
                        found = true;
                        break;
                    }
                }
                if(!found)
                {
                    cout << "Something is not right" << endl;
                    return -1;
                }
            }
        }
        
        return cost;
    }
};

int main()
{
    const int VERTEX_COUNT = 6;

    graph graphObj = graph(VERTEX_COUNT);

    graphObj.addEdge(1, 2, 1);
    graphObj.addEdge(1, 3, 4);
    graphObj.addEdge(1, 4, 5);

    graphObj.addEdge(2, 3, 2);
    graphObj.addEdge(2, 5, 3);
    graphObj.addEdge(2, 6, 6);

    graphObj.addEdge(3, 4, 1);
    graphObj.addEdge(3, 5, 2);

    graphObj.addEdge(4, 5, 2);
    graphObj.addEdge(4, 6, 2);

    graphObj.addEdge(5, 6, 3);

    vector<int> fleuryPath;
    int fleuryCost = graphObj.fleury(fleuryPath);
    cout << fleuryCost << endl;
    for(int i = 0; i < fleuryPath.size(); i++)
    {
        cout << fleuryPath[i] << " ";
    }
    cout << endl;

    return 0;
}