#include <bits/stdc++.h>
using namespace std;

class sudoku
{
    protected:
    vector<vector<int>> grid;
    vector<uint32_t> gridSet;
    vector<uint32_t> subgridSet;
    int type;
    int fitness = 0;
    int penalty = 0;

    virtual int get_subgrid_index(int x, int y) = 0;
    virtual void set_contains(int x, int y, int value) = 0;

    public:
    const int FOURBYFOUR = 0;
    const int NINEBYNINE = 1;

    bool set(int x, int y, int value)
    {
        if(value == 0 || grid[y][x] != 0)
        {
            return false;
        }
        if(!contains(x, y, value))
        {
            fitness++;
            set_contains(x, y, value);
            grid[y][x] = value;
            return true;
        }

        penalty++;
        grid[y][x] = value;
        set_contains(x, y, value);
        return false;
    }
    virtual bool contains(int x, int y, int value) = 0;
    int get(int x, int y)
    {
        return grid[y][x];
    }
    virtual void print() = 0;
    virtual vector<vector<int>> calculateInputCount(vector<int>& start) = 0;
    virtual void read(vector<int>& start, vector<vector<int>>& input, vector<int>& indexer) = 0;
    virtual void clear() = 0;
    virtual int getFullFitness() = 0;
    int get_type()
    {
        return type;
    }
    int getPenalty()
    {
        return penalty;
    }
    int getFitness()
    {
        return fitness;
    }
};

class sudoku4x4 : public sudoku
{
    protected:
    int get_subgrid_index(int x, int y)
    {
        return (x / 2) + 2 * (y / 2);
    }
    void set_contains(int x, int y, int value)
    {
        if(value != 0)
        {
            gridSet[x] |= (1 << (value - 1));
            gridSet[y] |= (1 << (value + 3));
            subgridSet[get_subgrid_index(x, y)] |= (1 << (value - 1));
        }
    }

    public:
    sudoku4x4()
    {
        grid = vector<vector<int>>(4, vector<int>(4, 0));
        gridSet = vector<uint32_t>(4, 0);
        subgridSet = vector<uint32_t>(4, 0);
        type = sudoku::FOURBYFOUR;
    }
    int getFullFitness()
    {
        return 16;
    }
    bool contains(int x, int y, int value)
    {
        if(value == 0)
        {
            return false;
        }

        uint32_t horizontal = gridSet[x];
        uint32_t vertical = gridSet[y];
        uint32_t subgrid = subgridSet[get_subgrid_index(x, y)];

        if((horizontal & (1 << (value - 1))) || (vertical & (1 << (value + 3))) || (subgrid & (1 << (value - 1))))
        {
            return true;
        }

        return false;
    }
    void print()
    {
        int verticalCount = 0;

        for(int y = grid.size() - 1; y >= 0; y--)
        {
            cout << "[" << y << "] ";
            for(int x = 0; x < grid[y].size(); x++)
            {
                cout << grid[y][x] << " ";

                if((x + 1) % 2 == 0)
                {
                    cout << " ";
                }
            }

            bitset<4> horizontal(gridSet[y]);
            bitset<4> vertical(gridSet[y] >> 4);
            bitset<4> subgrid(subgridSet[y]);

            cout << " " << horizontal << " " << vertical << " " << subgrid;

            cout << endl;

            verticalCount++;
            if((verticalCount % 2) == 0 && verticalCount != 0)
            {
                cout << endl;
            }
        }

        cout << "Fitness : " << fitness << endl;
        cout << "Penalty : " << penalty << endl;
    }
    vector<vector<int>> calculateInputCount(vector<int>& start)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 4;
            int y = i / 4;

            if(start[i] != 0)
            {
                set(x, y, start[i]);
            }
        }

        vector<vector<int>> buckets = vector<vector<int>>(4, vector<int>());
        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                if(get(x, y) == 0)
                {
                    int subgridIndex = get_subgrid_index(x, y);
                    uint32_t subgrid = subgridSet[subgridIndex];

                    for(int i = 1; i <= 4; i++)
                    {
                        uint32_t val = (subgrid & (1 << (i - 1)));
                        if(!val)
                        {
                            set(x, y, i);
                            buckets[subgridIndex].push_back(i);
                            break;
                        }
                    }
                }
            }
        }

        clear();

        return buckets;
    }
    void read(vector<int>& start, vector<vector<int>>& input, vector<int>& indexer)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 4;
            int y = i / 4;

            if(start[i] == 0)
            {
                int subgridIndex = get_subgrid_index(x, y);
                set(x, y, input[subgridIndex][indexer[subgridIndex]]);
                indexer[subgridIndex] += 1;
                
                inputIndex++;
            }
            else
            {
                set(x, y, start[i]);
            }
        }
    }
    void clear()
    {
        fitness = 0;
        penalty = 0;

        for(int y = 0; y < 4; y++)
        {
            for(int x = 0; x < 4; x++)
            {
                grid[y][x] = 0;
            }

            gridSet[y] = 0;
            subgridSet[y] = 0;
        }
    }
};

class sudoku9x9 : public sudoku
{
    protected:
    int get_subgrid_index(int x, int y)
    {
        return (x / 3) + 3 * (y / 3);
    }
    void set_contains(int x, int y, int value)
    {
        if(value != 0)
        {
            gridSet[x] |= (1 << (value - 1));
            gridSet[y] |= (1 << (value + 8));
            subgridSet[get_subgrid_index(x, y)] |= (1 << (value - 1));
        }
    }

    public:
    sudoku9x9()
    {
        grid = vector<vector<int>>(9, vector<int>(9, 0));
        gridSet = vector<uint32_t>(9, 0);
        subgridSet = vector<uint32_t>(9, 0);
        type = sudoku::NINEBYNINE;
    }
    int getFullFitness()
    {
        return 81;
    }
    bool contains(int x, int y, int value)
    {
        if(value == 0)
        {
            return false;
        }

        uint32_t horizontal = gridSet[x];
        uint32_t vertical = gridSet[y];
        uint32_t subgrid = subgridSet[get_subgrid_index(x, y)];

        if((horizontal & (1 << (value - 1))) || (vertical & (1 << (value + 8))) || (subgrid & (1 << (value - 1))))
        {
            return true;
        }

        return false;
    }
    void print()
    {
        int verticalCount = 0;

        for(int y = grid.size() - 1; y >= 0; y--)
        {
            cout << "[" << y << "] ";
            for(int x = 0; x < grid[y].size(); x++)
            {
                cout << grid[y][x] << " ";

                if((x + 1) % 3 == 0)
                {
                    cout << " ";
                }
            }

            bitset<9> horizontal(gridSet[y]);
            bitset<9> vertical(gridSet[y] >> 9);
            bitset<9> subgrid(subgridSet[y]);

            cout << " " << horizontal << " " << vertical << " " << subgrid;

            cout << endl;

            verticalCount++;
            if((verticalCount % 3) == 0 && verticalCount != 0)
            {
                cout << endl;
            }
        }

        cout << "Fitness : " << fitness << endl;
        cout << "Penalty : " << penalty << endl;
    }
    vector<vector<int>> calculateInputCount(vector<int>& start)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 9;
            int y = i / 9;

            if(start[i] != 0)
            {
                set(x, y, start[i]);
            }
        }

        vector<vector<int>> buckets = vector<vector<int>>(9, vector<int>());
        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                if(get(x, y) == 0)
                {
                    int subgridIndex = get_subgrid_index(x, y);
                    uint32_t subgrid = subgridSet[subgridIndex];

                    for(int i = 1; i <= 9; i++)
                    {
                        if(!(subgrid & (1 << (i - 1))))
                        {
                            set(x, y, i);
                            buckets[subgridIndex].push_back(i);
                            break;
                        }
                    }
                }
            }
        }

        clear();

        return buckets;
    }
    void read(vector<int>& start, vector<vector<int>>& input, vector<int>& indexer)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 9;
            int y = i / 9;

            if(start[i] == 0)
            {
                int subgridIndex = get_subgrid_index(x, y);
                set(x, y, input[subgridIndex][indexer[subgridIndex]]);
                indexer[subgridIndex] += 1;

                inputIndex++;
            }
            else
            {
                set(x, y, start[i]);
            }
        }
    }
    void clear()
    {
        fitness = 0;
        penalty = 0;

        for(int y = 0; y < 9; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                grid[y][x] = 0;
            }

            gridSet[y] = 0;
            subgridSet[y] = 0;
        }
    }
};

//Helpers
void clear(vector<int>& arr)
{
    for(int i = 0; i < arr.size(); i++)
    {
        arr[i] = 0;
    }
}
void newRandom()
{
    srand(static_cast<unsigned int>(time(0)));
}
int random(int min, int max)
{
    return min + rand() % (max - min + 1);
}
double randomDouble(double min, double max)
{
    return min + (double)(rand()) / (double)RAND_MAX * (max - min);
}

//To allow debugging, uncomment the line bellow
//#define DEBUG

void randomize(vector<vector<int>>& input, int count)
{
    for(int j = 0; j < count; j++)
    {
        int index = random(0, input.size() - 1);

        if(input[index].size() == 0)
        {
            continue;
        }

        int left = random(0, input[index].size() - 1);
        int right = random(0, input[index].size() - 1);

        int temp = input[index][left];
        input[index][left] = input[index][right];
        input[index][right] = temp;
    }
}

//Hill Climbing
void hillClimb(sudoku& grid, vector<int>& start, int sudokuMaxValue, uint64_t maximumIterations, int randomChangeAmount)
{
    cout << "Hill climbing is running" << endl;
    vector<vector<int>> input = grid.calculateInputCount(start);
    vector<int> indexer = vector<int>(input.size(), 0);

    uint64_t maximum = maximumIterations;
    uint64_t count = 0;

    while(maximum > 0)
    {
        maximum--;
        count++;

        grid.read(start, input, indexer);
        clear(indexer);
        int currentFitness = grid.getFitness();
        if(currentFitness == grid.getFullFitness())
        {
            break;
        }

        vector<vector<int>> neighbour = input;
        int randomAmount = random(1, randomChangeAmount);
        randomize(neighbour, randomAmount);

        grid.read(start, neighbour, indexer);
        clear(indexer);
        int neighbourFitness = grid.getFitness();

        if(neighbourFitness > currentFitness)
        {
            cout << "Fitness : " << neighbourFitness << endl;
            
            input = neighbour;
        }
    }

    cout << "Finished in : " << count << " iterations" << endl << endl;
    grid.read(start, input, indexer);
    clear(indexer);
    grid.print();
}

//Simulated Annealing
void simulatedAnnealing(sudoku& grid, vector<int>& start, int sudokuMaxValue, uint64_t maximumIterations, int randomChangeAmount, double coolingMultiplier)
{
    cout << "Simulated annealing is running" << endl;
    vector<vector<int>> input = grid.calculateInputCount(start);
    vector<int> indexer = vector<int>(input.size(), 0);

    uint64_t maximum = maximumIterations;
    uint64_t count = 0;

    //Temperature
    double temperature = 0.0;
    double sumTemperature = 0.0;
    vector<double> temperatureCheck = vector<double>(200);
    vector<vector<int>> temperatureInput = input;
    for(int i = 0; i < temperatureCheck.size(); i++)
    {
        int randomAmount = random(1, randomChangeAmount);
        randomize(temperatureInput, randomAmount);

        grid.read(start, temperatureInput, indexer);
        clear(indexer);

        temperatureCheck[i] = (double)grid.getFitness();
        sumTemperature += (double)grid.getFitness();
    }
    double mean = sumTemperature / (double)temperatureCheck.size();
    double squareTemperatureSum = 0.0;
    for(int i = 0; i < temperatureCheck.size(); i++)
    {
        squareTemperatureSum += ((temperatureCheck[i] - mean) * (temperatureCheck[i] - mean));
    }
    temperature = sqrt(squareTemperatureSum / (double)temperatureCheck.size());

    cout << "Start Temperature : " << temperature << endl;

    //Algorithm
    while(maximum > 0)
    {
        maximum--;
        count++;

        grid.read(start, input, indexer);
        clear(indexer);
        int currentFitness = grid.getFitness();
        if(currentFitness == grid.getFullFitness())
        {
            break;
        }

        vector<vector<int>> neighbour = input;
        int randomAmount = random(1, randomChangeAmount);
        randomize(neighbour, randomAmount);

        grid.read(start, neighbour, indexer);
        clear(indexer);
        int neighbourFitness = grid.getFitness();

        if(neighbourFitness > currentFitness)
        {
            cout << "Fitness : " << neighbourFitness << " , Temperature : " << temperature << endl;
            input = neighbour;
        }
        else
        {
            
            double acceptanceProbability = exp((double)(neighbourFitness - currentFitness) / temperature);
            double diced = randomDouble(0.0, 1.0);
            if(diced < acceptanceProbability)
            {
                #ifdef DEBUG
                cout << "(Accepted) Fitness : " << neighbourFitness << " , Temperature : " << temperature << " , Prob : " << acceptanceProbability << " , Diced : " << diced << endl;
                #endif
                input = neighbour;
            }
        }

        temperature *= coolingMultiplier;
    }

    cout << "Finished in : " << count << " iterations" << endl << endl;
    grid.read(start, input, indexer);
    clear(indexer);
    grid.print();
}

//Genetic
class geneticComparer
{
    public:
    bool operator() (const pair<int, vector<vector<int>>>& left, const pair<int, vector<vector<int>>>& right) const
    {
        return left.first > right.first;
    }
};
void copyTo(vector<int>& from, vector<int>& to)
{
    if(from.size() != to.size())
    {
        cout << "Brh";
        return;
    }

    for(int i = 0; i < from.size(); i++)
    {
        to[i] = from[i];
    }
}
void genetic(sudoku& grid, vector<int>& start, int sudokuMaxValue, uint64_t maximumIterations, int halfCount, int randomChangeAmountStart, double mutationProbability, int mutationRandomChange, double tournamentWinChance, int plateuRestart)
{
    cout << "Genetic is running" << endl;
    if(halfCount % 2 != 0 || halfCount < 2)
    {
        cout << "Genetic algorithm cannot run invalid arguments" << endl;

        return;
    }
    geneticComparer comparer;
    vector<vector<int>> input = grid.calculateInputCount(start);
    vector<int> indexer = vector<int>(input.size(), 0);

    vector<pair<int, vector<vector<int>>>> population = vector<pair<int, vector<vector<int>>>>(halfCount * 2);

    for(int i = 0; i < population.size(); i++)
    {
        int randomAmount = random(1, randomChangeAmountStart);
        vector<vector<int>> sampleInput = input;

        randomize(sampleInput, randomAmount);

        grid.read(start, sampleInput, indexer);
        clear(indexer);
        
        int fitness = grid.getFitness();

        population[i] = make_pair(fitness, sampleInput);
    }

    uint64_t maximum = maximumIterations;
    uint64_t count = 0;
    int greatestFitness = INT_MIN;
    vector<vector<int>> greatestInput;
    int currentTournamentIndex = 0;
    int plateuCount = 0;

    while(maximum > 0)
    {
        maximum--;
        count++;
        currentTournamentIndex = 0;
        plateuCount++;

        if(plateuCount > plateuRestart)
        {
            plateuCount = 0;
            int bestFitness = INT_MIN;
            for(int i = 0; i < population.size(); i++)
            {
                vector<vector<int>>& current = population[i].second;
                randomize(current, random(1, randomChangeAmountStart));

                grid.read(start, current, indexer);
                clear(indexer);

                population[i].first = grid.getFitness();

                if(grid.getFitness() > bestFitness)
                {
                    bestFitness = grid.getFitness();
                    
                    if(bestFitness > greatestFitness)
                    {
                        greatestFitness = bestFitness;
                        greatestInput = current;

                        if(greatestFitness == grid.getFullFitness())
                        {
                            goto done;
                        }
                    }

                    greatestFitness = bestFitness;
                    greatestInput = current;
                }
            }
            cout << "Plateud : " << bestFitness << endl;
        }

        //Tornament
        while(currentTournamentIndex < halfCount)
        {
            int left = random(currentTournamentIndex, population.size() - 1);
            int right = random(currentTournamentIndex, population.size() - 1);
            int middle = random(currentTournamentIndex, population.size() - 1);
            
            if(left == right || left == middle || right == middle)
            {
                continue;
            }

            int choose = left;
            if(population[right].first > population[choose].first && random(0.0, 1.0) < tournamentWinChance)
            {
                choose = right;
            }
            if(population[middle].first > population[choose].first && random(0.0, 1.0) < tournamentWinChance)
            {
                choose = middle;
            }

            pair<int, vector<vector<int>>> temp = population[currentTournamentIndex];
            population[currentTournamentIndex] = population[choose];
            population[choose] = temp;
            currentTournamentIndex++;
        }

        for(int i = 0; i < halfCount / 2; i++)
        {
            vector<vector<int>>& leftParent = population[i * 2].second;
            vector<vector<int>>& rightParent = population[i * 2 + 1].second;

            //0 -- 2^(subgrid) - 1
            uint32_t bitmask = random(0, (1 << (input.size())) - 1);

            pair<int, vector<vector<int>>>& leftChild = population[halfCount + (i * 2)];
            pair<int, vector<vector<int>>>& rightChild = population[halfCount + (i * 2 + 1)];

            for(int j = 0; j < input.size(); j++)
            {
                uint32_t current = bitmask & (1 << j);

                if(current)
                {
                    leftChild.second[j] = rightParent[j];
                    rightChild.second[j] = leftParent[j];
                }
                else
                {
                    leftChild.second[j] = leftParent[j];
                    rightChild.second[j] = rightParent[j];
                }
            }

            for(int i = 0; i < 2; i++)
            {
                double mutation = randomDouble(0.0, 1.0);
                vector<vector<int>>& current = leftChild.second;
                if(i == 1)
                {
                    current = rightChild.second;
                }
                if(mutation > mutationProbability)
                {
                    continue;
                }

                randomize(current, random(1, mutationRandomChange));
            }

            grid.read(start, leftChild.second, indexer);
            clear(indexer);
            leftChild.first = grid.getFitness();

            grid.read(start, rightChild.second, indexer);
            clear(indexer);
            rightChild.first = grid.getFitness();

            if(leftChild.first > greatestFitness)
            {
                greatestFitness = leftChild.first;
                greatestInput = leftChild.second;
                cout << "Fitness : " << greatestFitness << " , Gen : " << count << endl;
            }
            if(rightChild.first > greatestFitness)
            {
                greatestFitness = rightChild.first;
                greatestInput = rightChild.second;
                cout << "Fitness : " << greatestFitness << " , Gen : " << count << endl;
            }
        }

        if(greatestFitness == grid.getFullFitness())
        {
            break;
        }
    }

    done:
    cout << "Finished in : " << count << " iterations" << endl << endl;
    grid.read(start, greatestInput, indexer);
    clear(indexer);
    grid.print();
}

vector<int> parseAs4x4Start(string path)
{
    fstream file(path);
    if (!file.is_open())
    {
        cerr << "Error openning file" << endl;
        return vector<int>(0);
    }

    vector<int> start = vector<int>(16);

    for(int y = 3; y >= 0; y--)
    {
        for(int x = 0; x < 4; x++)
        {
            int i = y * 4 + x;
            file >> start[i];
        }
    }

    return start;
}
vector<int> parseAs9x9Start(string path)
{
    fstream file(path);
    if (!file.is_open())
    {
        cerr << "Error openning file" << endl;
        return vector<int>(0);
    }

    vector<int> start = vector<int>(81);

    for(int y = 8; y >= 0; y--)
    {
        for(int x = 0; x < 9; x++)
        {
            int i = y * 9 + x;
            file >> start[i];
        }
    }

    return start;
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
    newRandom();
    sudoku4x4 grid4x4 = sudoku4x4();
    vector<int> start4x4 = parseAs4x4Start("4x4.txt");

    sudoku9x9 grid9x9 = sudoku9x9();
    vector<int> start9x9 = parseAs9x9Start("9x9.txt");

    cout << "4x4 Grid" << endl;
    auto startHill4x4 = getCurrentTime();
    hillClimb(grid4x4, start4x4, 4, UINT_MAX, 5);
    cout << "Frame time : " << calculateMS(startHill4x4, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;

    cout << "9x9 Grid (Capped 1000000)" << endl;
    auto startHill9x9 = getCurrentTime();
    hillClimb(grid9x9, start9x9, 9, 1000000, 20);
    cout << "Frame time : " << calculateMS(startHill9x9, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;

    cout << "4x4 Grid" << endl;
    auto startSim4x4 = getCurrentTime();
    simulatedAnnealing(grid4x4, start4x4, 4, UINT_MAX, 5, 0.99);
    cout << "Frame time : " << calculateMS(startSim4x4, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;

    cout << "9x9 Grid" << endl;
    auto startSim9x9 = getCurrentTime();
    simulatedAnnealing(grid9x9, start9x9, 9, UINT_MAX, 20, 0.99);
    cout << "Frame time : " << calculateMS(startSim9x9, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;

    cout << "4x4 Grid" << endl;
    auto startGen4x4 = getCurrentTime();
    genetic(grid4x4, start4x4, 4, UINT_MAX, 20, 32, 0.1, 5, 0.25, 100000);
    cout << "Frame time : " << calculateMS(startGen4x4, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;

    cout << "9x9 Grid" << endl;
    auto startGen9x9 = getCurrentTime();
    genetic(grid9x9, start9x9, 9, UINT_MAX, 100, 2000, 0.1, 20, 0.25, 100000);
    cout << "Frame time : " << calculateMS(startGen9x9, getCurrentTime()) << endl << endl;
    cout << "-------------------------------------------------" << endl << endl;
}