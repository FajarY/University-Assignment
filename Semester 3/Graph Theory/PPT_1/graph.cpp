#include <bits/stdc++.h>

using namespace std;

class djikstraComparer
{
    public:
    bool operator() (const pair<int, int>& left, const pair<int, int>& right) const
    {
        //Priority Queue Make Top Smallest
        return left.second >= right.second;
    }
};
struct aStarNode
{
    public:
    int vertex;
    int gCost;
    int fCost;

    aStarNode(int vertex, int gCost, int fCost)
    {
        this->vertex = vertex;
        this->gCost = gCost;
        this->fCost = fCost;
    }
};
class aStarComparer
{
    public:
    bool operator() (const aStarNode& left, const aStarNode& right) const
    {
        if(left.fCost > right.fCost)
        {
            return true;
        }
        else if(left.fCost == right.fCost)
        {
            return left.gCost >= right.gCost;
        }
        
        return false;
    }
};

//All algorithm cannot work with negative cycle
class graph
{
    private:
    vector<vector<pair<int, int>>> edges;
    vector<vector<pair<int, int>>> johnsonEdges;
    int listSize;
    int vertexCount;
    int edgeCount;

    public:
    int getEdgeCount()
    {
        return edgeCount;
    }
    void initializeForJohnson()
    {
        johnsonEdges = edges;
        
        //Create new vertex that connects to all vertex with weight 0
        for(int i = 1; i <= vertexCount; i++)
        {
            //Directed
            johnsonEdges[0].push_back(make_pair(i, 0));
        }
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

    //Shortest path algorithm from start to end, cannot work with negative weights
    template <typename comparer>
    int djikstra(int start, int end, vector<int>& returnPath)
    {
        comparer comparator;
        //Left is from vertex to itself, Right is total cost from the vertex to the vertex itself
        vector<pair<int, int>> visited = vector<pair<int, int>>(listSize, make_pair(-1, INT_MAX));

        //Left is current vertex, Right is current cost
        priority_queue<pair<int, int>, vector<pair<int, int>>, djikstraComparer> openSet;

        visited[start] = make_pair(-1, 0);
        openSet.push(make_pair(start, 0));

        bool foundPath = false;
        while(!openSet.empty())
        {
            pair<int, int> current = openSet.top();
            openSet.pop();

            //Check if target cost is higher than equals current cost, if not continue
            if(!comparator(make_pair(current.first, visited[current.first].second), current))
            {
                continue;
            }
            if(current.first == end)
            {
                foundPath = true;

                //If we want to make this greedy add a break
                //break;
            }
            
            for(int i = 0; i < edges[current.first].size(); i++)
            {
                pair<int, int> edge = edges[current.first][i];
                pair<int, int> newSet = make_pair(edge.first, current.second + edge.second);

                //Check if its not visited or the target cost is higher than current cost
                //Does not support negative weight cycle because it will loop, workaround is to not compare and just check if it is already visited
                if(visited[edge.first].first == -1 || comparator(make_pair(newSet.first, visited[edge.first].second), newSet))
                {
                    //Make the traversal to this vertex be the current vertex
                    visited[edge.first] = make_pair(current.first, newSet.second);
                    openSet.push(newSet);
                }
            }
        }

        if(foundPath)
        {
            int current = end;

            while(current != start)
            {
                returnPath.push_back(current);
                current = visited[current].first;
            }
            returnPath.push_back(start);

            return visited[end].second;
        }

        return -1;
    }

    //Algorithm to check the cost from start to every vertex. Also modified to add traversal from start to end or any vertex
    int bellmanFord(int start, int end, vector<int>& returnPath)
    {
        //Left is from vertex to itself, Right is total cost from the vertex to the vertex itself
        vector<pair<int, int>> cost = vector<pair<int, int>>(listSize, make_pair(-1, INT_MAX));
        cost[start] = make_pair(start, 0);

        //I is the iteration count |v| - 1
        for(int i = 1; i < vertexCount; i++)
        {
            //Loop for all the edges
            for(int j = 1; j <= vertexCount; j++)
            {
                for(int k = 0; k < edges[j].size(); k++)
                {
                    pair<int, int> edge = edges[j][k];

                    //If it is not setted continue
                    if(cost[j].second == INT_MAX)
                    {
                        continue;
                    }

                    //New cost is cost at current vertex plus the edge weight
                    int newCost = cost[j].second + edge.second;

                    if(newCost < cost[edge.first].second)
                    {
                        //Make the traversal to this vertex be the current
                        cost[edge.first] = make_pair(j, newCost);
                    }
                }
            }
        }

        //Check For Negative Cycles
        for(int i = 1; i <= vertexCount; i++)
        {
            for(int j = 0; j < edges[i].size(); j++)
            {
                pair<int, int> edge = edges[i][j];

                //It is a negative cycle if the cost of current vertex plus the weight of the vertex is less than the cost to that vertex
                if(cost[i].second != INT_MAX && cost[i].second + edge.second < cost[edge.first].second)
                {
                    //Negative cycle
                    return -1;
                }
            }
        }

        int loopSize = 0;
        int current = end;
        while(current != start)
        {
            returnPath.push_back(current);

            current = cost[current].first;

            loopSize++;

            //Check if no path or is looping
            if(current == -1 || loopSize > (vertexCount + 1))
            {
                returnPath.clear();
                return -1;
            }
        }

        returnPath.push_back(start);
        return cost[end].second;
    }

    int aStar(int start, int end, vector<int>& returnPath)
    {
        //Get heuristic from simple bfs
        vector<int> heuristic = vector<int>(listSize, INT_MAX);
        queue<pair<int, int>> heuristicOpenSet;
        heuristic[end] = 0;
        
        heuristicOpenSet.push(make_pair(end, 0));
        while(!heuristicOpenSet.empty())
        {
            pair<int, int> current = heuristicOpenSet.back();
            heuristicOpenSet.pop();

            for(int i = 0; i < edges[current.first].size(); i++)
            {
                pair<int, int> edge = edges[current.first][i];

                int newCost = edge.second + current.second;
                if(heuristic[edge.first] == INT_MAX)
                {
                    heuristic[edge.first] = newCost;
                    heuristicOpenSet.push(make_pair(edge.first, newCost));
                }
            }
        }

        //A Star
        vector<pair<int, aStarNode>> visited = vector<pair<int, aStarNode>>(listSize, make_pair(-1, aStarNode(-1, INT_MAX, INT_MAX)));
        priority_queue<aStarNode, vector<aStarNode>, aStarComparer> openSet; 
        aStarComparer comparator;

        aStarNode startNode = aStarNode(start, 0, heuristic[start]);
        visited[start] = make_pair(start, startNode);
        openSet.push(startNode);

        bool foundPath = false;
        while(!openSet.empty())
        {
            aStarNode current = openSet.top();
            openSet.pop();

            //If the visited is not bigger skip
            if(!comparator(visited[current.vertex].second, current))
            {
                continue;
            }
            if(current.vertex == end)
            {
                foundPath = true;
            }

            for(int i = 0; i < edges[current.vertex].size(); i++)
            {
                pair<int, int> edge = edges[current.vertex][i];

                int newGCost = current.gCost + edge.second;
                aStarNode newNode = aStarNode(edge.first, newGCost, newGCost + heuristic[edge.first]);

                if(visited[edge.first].first == -1 || comparator(visited[edge.first].second, newNode))
                {
                    visited[edge.first] = make_pair(current.vertex, newNode);
                    openSet.push(newNode);
                }
            }
        }

        if(foundPath)
        {
            int current = end;

            while(current != start)
            {
                returnPath.push_back(current);
                current = visited[current].first;
            }
            returnPath.push_back(start);

            return visited[end].second.gCost;
        }

        return -1;
    }

    //Algorithm to check the cost of every vertex to every vertex. Also modified to add traversal from every vertex to any vertex
    int floydWarshall(int start, int end, vector<int>& returnPath)
    {
        //Prepare matrix and set to max
        vector<vector<int>> cost = vector<vector<int>>(listSize, vector<int>(listSize, INT_MAX));
        vector<vector<int>> traversal = vector<vector<int>>(listSize, vector<int>(listSize, -1));

        for(int i = 1; i <= vertexCount; i++)
        {
            //Set cost to itself to 0, and traversal from to itself
            cost[i][i] = 0;
            traversal[i][i] = i;

            //Set cost to others
            for(int j = 0; j < edges[i].size(); j++)
            {
                pair<int, int> edge = edges[i][j];

                //Set traversal on target to this
                traversal[i][edge.first] = i;
                cost[i][edge.first] = edge.second;
            }
        }

        for(int k = 1; k <= vertexCount; k++)
        {
            for(int i = 1; i <= vertexCount; i++)
            {
                for(int j = 1; j <= vertexCount; j++)
                {
                    //Check adjacency from i to k, and k to j
                    if(cost[i][k] != INT_MAX && cost[k][j] != INT_MAX && cost[i][j] > cost[i][k] + cost[k][j])
                    {
                        cost[i][j] = cost[i][k] + cost[k][j];

                        //Change traversal between vertex i to j to the traversal from k to j
                        //traversal from i to k is unchanged and traversal from k to j is unchanged
                        traversal[i][j] = traversal[k][j];
                    }
                }
            }
        }

        if(cost[start][end] == INT_MAX)
        {
            return -1;
        }
        
        int current = end;
        while(current != start)
        {
            returnPath.push_back(current);

            //Get the traversal always from start since start is unchanged 
            //ex: i to j, from i to k then k to j
            //let i to j is from k, and i to k is from i, and k to j is from k. Then if we traverse i to j we get from k, after that we traverse i to k we get i, hence we get the path j - k - i
            current = traversal[start][current];
        }

        returnPath.push_back(start);

        return cost[start][end];
    }

    //Same like Floyd  Warshall, instead combine bellman with djikstra. This generates a more efficient time complexity
    int johnson(int start, int end, vector<int>& returnPath)
    {
        //Belman with extra source vertex, with source vertex is directed to every vertex
        vector<int> bellmanVertex = vector<int>(listSize, INT_MAX);
        bellmanVertex[0] = 0;
        
        for(int i = 0; i < vertexCount; i++)
        {
            for(int j = 0; j <= vertexCount; j++)
            {
                for(int k = 0; k < johnsonEdges[j].size(); k++)
                {
                    if(bellmanVertex[j] == INT_MAX)
                    {
                        continue;
                    }

                    pair<int, int> edge = johnsonEdges[j][k];

                    int newCost = bellmanVertex[j] + edge.second;
                    if(bellmanVertex[edge.first] > newCost)
                    {
                        bellmanVertex[edge.first] = newCost;
                    }
                }
            }
        }
        
        //Skip if negative cycle
        for(int i = 0; i <= vertexCount; i++)
        {
            for(int j = 0; j < johnsonEdges[i].size(); j++)
            {
                pair<int, int> edge = johnsonEdges[i][j];

                if(bellmanVertex[i] != INT_MAX && bellmanVertex[i] + edge.second < bellmanVertex[edge.first])
                {
                    return -1;
                }
            }
        }

        //Create modified edges for djikstra without the source vertex
        //Modified edges weight is the original cost plus the (cost from source to vertex start) minus (cost from source to vertex destination)
        vector<vector<pair<int, int>>> modifiedEdges = vector<vector<pair<int, int>>>(listSize);
        for(int i = 1; i <= vertexCount; i++)
        {
            for(int j = 0; j < johnsonEdges[i].size(); j++)
            {
                pair<int, int> edge = johnsonEdges[i][j];
                modifiedEdges[i].push_back(make_pair(edge.first, edge.second + bellmanVertex[i] - bellmanVertex[j]));
            }
        }

        //Create cost matrix for traversal and cost purposes
        vector<vector<pair<int, int>>> cost = vector<vector<pair<int, int>>>(listSize, vector<pair<int, int>>(listSize, make_pair(-1, INT_MAX)));

        //Djikstra for every vertex, to get the cost and path of every vertex like Floyd Warshall
        djikstraComparer comparator;
        for(int i = 1; i <= vertexCount; i++)
        {
            vector<pair<int, int>> visited = vector<pair<int, int>>(listSize, make_pair(-1, INT_MAX));
            priority_queue<pair<int, int>, vector<pair<int, int>>, djikstraComparer> openSet;

            visited[i] = make_pair(i, 0);
            openSet.push(make_pair(i, 0));

            while(!openSet.empty())
            {
                pair<int, int> current = openSet.top();
                openSet.pop();

                if(!comparator(make_pair(current.first, visited[current.first].second), current))
                {
                    continue;
                }
                for(int j = 0; j < modifiedEdges[current.first].size(); j++)
                {
                    pair<int, int> edge = modifiedEdges[current.first][j];

                    int newCost = edge.second + current.second;

                    if(visited[edge.first].first == -1 || newCost < visited[edge.first].second)
                    {
                        visited[edge.first] = make_pair(current.first, newCost);
                        openSet.push(make_pair(edge.first, newCost));
                    }
                }
            }
            for(int j = 1; j <= vertexCount; j++)
            {
                cost[i][j] = visited[j];
            }
        }
        
        //Skip if not found the path
        if(cost[start][end].second == INT_MAX)
        {
            return -1;
        }

        int current = end;
        while(current != start)
        {
            returnPath.push_back(current);
            current = cost[start][current].first;
        }
        returnPath.push_back(start);

        return cost[start][end].second;
    }
};

int main()
{
    const int VERTEX_COUNT = 11;
    const int START = 1;
    const int END = 11;

    graph graphObj = graph(VERTEX_COUNT);

    graphObj.addEdge(1, 2, 2);
    graphObj.addEdge(1, 3, 8);
    graphObj.addEdge(1, 4, 1);

    graphObj.addEdge(2, 3, 6);
    graphObj.addEdge(2, 5, 1);

    graphObj.addEdge(3, 5, 5);
    graphObj.addEdge(3, 6, 1);
    graphObj.addEdge(3, 7, 2);
    graphObj.addEdge(3, 4, 7);

    graphObj.addEdge(4, 7, 9);

    graphObj.addEdge(5, 6, 3);
    graphObj.addEdge(5, 8, 2);
    graphObj.addEdge(5, 9, 9);

    graphObj.addEdge(6, 9, 6);
    graphObj.addEdge(6, 7, 4);

    graphObj.addEdge(7, 9, 3);
    graphObj.addEdge(7, 10, 1);

    graphObj.addEdge(8, 9, 7);
    graphObj.addEdge(8, 11, 9);

    graphObj.addEdge(9, 11, 2);
    graphObj.addEdge(9, 10, 1);

    graphObj.addEdge(10, 11, 4);  

    cout << "Pathfinding from " << START << " to " << END << endl << endl;

    vector<int> djikstraPath;
    int djikstraCost = graphObj.djikstra<djikstraComparer>(START, END, djikstraPath);
    cout << "Djikstra : " << djikstraCost << endl;
    for(int i = djikstraPath.size() - 1; i >= 0; i--)
    {
        cout << djikstraPath[i] << " ";
    }
    cout << endl << endl;

    vector<int> bellmanPath;
    int bellmanCost = graphObj.bellmanFord(START, END, bellmanPath);
    cout << "Bellman Ford : " << bellmanCost << endl;
    for(int i = bellmanPath.size() - 1; i >= 0; i--)
    {
        cout << bellmanPath[i] << " ";
    }
    cout << endl << endl;
    
    vector<int> aStarPath;
    int aStarCost = graphObj.aStar(START, END, aStarPath);
    cout << "A Star : " << aStarCost << endl;
    for(int i = aStarPath.size() - 1; i >= 0; i--)
    {
        cout << aStarPath[i] << " ";
    }
    cout << endl << endl;

    vector<int> floydPath;
    int floydCost = graphObj.floydWarshall(START, END, floydPath);
    cout << "Floyd Warshall : " << floydCost << endl;
    for(int i = floydPath.size() - 1; i >= 0; i--)
    {
        cout << floydPath[i] << " ";
    }
    cout << endl << endl;

    graphObj.initializeForJohnson();
    vector<int> johnsonPath;
    int johnsonCost = graphObj.johnson(START, END, johnsonPath);
    cout << "Johnson : " << johnsonCost << endl;
    for(int i = johnsonPath.size() - 1; i >= 0; i--)
    {
        cout << johnsonPath[i] << " ";
    }

    return 0;
}