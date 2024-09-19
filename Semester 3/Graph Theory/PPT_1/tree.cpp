#include <bits/stdc++.h>

using namespace std;

class kruskal_sorter
{
    public:
    bool operator() (const pair<int, pair<int, int>>& left, const pair<int, pair<int, int>>& right) const
    {
        //Priority Queue Make Top Smallest
        return left.second.second <= right.second.second;
    }
};

int get_parent(int vertex, vector<int>& parentList)
{
    if(parentList[vertex] == vertex)
    {
        return vertex;
    }

    return get_parent(parentList[vertex], parentList);
}
void union_set(int left, int right, vector<int>& parentList)
{
    int leftParent = get_parent(left, parentList);
    int rightParent = get_parent(right, parentList);

    parentList[rightParent] = leftParent;
}

//All algorithm cannot work with negative cycle
class graph
{
    private:
    vector<vector<pair<int, int>>> edges;
    vector<pair<int, pair<int, int>>> edgesAll;
    int listSize;
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
        this->listSize = vertexCount + 1;
        edges = vector<vector<pair<int, int>>>(this->listSize);

        edgeCount = 0;
    }

    void addEdge(int left, int right, int cost)
    {
        //Undirected graph
        edges[left].push_back(make_pair(right, cost));
        edges[right].push_back(make_pair(left, cost));

        edgesAll.push_back(make_pair(left, make_pair(right, cost)));

        edgeCount++;
    }

    int kruskal()
    {
        vector<pair<int, pair<int, int>>> sortedEdge = edgesAll;
        kruskal_sorter sorter;
        sort(sortedEdge.begin(), sortedEdge.end(), sorter);

        vector<int> parentList = vector<int>(listSize);
        for(int i = 1; i <= vertexCount; i++)
        {
            parentList[i] = i;
        }

        vector<pair<int, pair<int, int>>> keepEdge;
        for(int i = 0; i < sortedEdge.size(); i++)
        {
            pair<int, pair<int, int>> current = sortedEdge[i];

            int leftParent = get_parent(current.first, parentList);
            int rightParent = get_parent(current.second.first, parentList);

            if(leftParent != rightParent)
            {
                parentList[rightParent] = leftParent;
                keepEdge.push_back(current);
            }
        }

        int weight = 0;
        for(int i = 0; i < keepEdge.size(); i++)
        {
            weight += keepEdge[i].second.second;
        }

        return weight;
    }
};

int main()
{
    const int VERTEX_COUNT = 7;

    graph graphObj = graph(VERTEX_COUNT);

    graphObj.addEdge(1, 2, 6);
    graphObj.addEdge(1, 3, 6);
    graphObj.addEdge(1, 4, 6);

    graphObj.addEdge(2, 4, 3);
    graphObj.addEdge(2, 5, 2);

    graphObj.addEdge(3, 4, 7);
    graphObj.addEdge(3, 6, 8);

    graphObj.addEdge(4, 5, 2);
    graphObj.addEdge(4, 6, 3);
    graphObj.addEdge(4, 7, 4);

    graphObj.addEdge(5, 7, 3);
    
    graphObj.addEdge(6, 7, 1);

    cout << graphObj.kruskal();
}