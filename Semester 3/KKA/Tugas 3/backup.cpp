struct greedyNode
{
    public:
    pair<int, int> from;
    pair<int, int> pos;
    double cost;
    double stepCost;

    greedyNode()
    {

    }
    greedyNode(pair<int, int> from, pair<int, int> pos, double cost, double stepCost)
    {
        this->from = from;
        this->pos = pos;
        this->cost = cost;
        this->stepCost = stepCost;
    }
    bool operator()(const greedyNode &left, const greedyNode &right)
    {
        if (left.cost > right.cost)
        {
            return true;
        }

        return false;
    }
};
void greedyBestFirstSearch(grid &data, pair<int, int> start, pair<int, int> target, double &outputCost, vector<pair<int, int>> &path)
{
    unordered_map<pair<int, int>, greedyNode, pairHasher<int, int>> assigned;
    priority_queue<greedyNode, vector<greedyNode>, greedyNode> openSet;

    greedyNode startNode = greedyNode(start, start, getHeuristic(start, target), 0.0);
    openSet.push(startNode);
    assigned[start] = startNode;

    bool foundPath = false;
    while(!openSet.empty())
    {
        greedyNode current = openSet.top();
        openSet.pop();

        if(current.pos == target)
        {
            foundPath = true;
            break;
        }

        for(int x = -1; x <= 1; x++)
        {
            for(int y = -1; y <= 1; y++)
            {
                if(x == 0 && y == 0) continue;
                pair<int, int> pos = make_pair(current.pos.first + x, current.pos.second + y);
                if(assigned.find(pos) != assigned.end()) continue;
                int tile = data.getTile(pos.first, pos.second);
                if(tile == -1 || tile == 1) continue;

                double stepCost = (x != 0 && y != 0) ? DIAGONAL_COST : NORMAL_COST;
                double newStepCost = stepCost + current.stepCost;

                greedyNode newNode = greedyNode(current.pos, pos, getHeuristic(pos, target), newStepCost);

                assigned[pos] = newNode;
                openSet.push(newNode);
            }
        }
    }

    if (foundPath)
    {
        outputCost = assigned[target].stepCost;

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