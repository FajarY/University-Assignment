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
    const int THREEBYTHREE = 1;

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
        return false;
    }
    virtual bool contains(int x, int y, int value) = 0;
    int get(int x, int y)
    {
        return grid[y][x];
    }
    virtual void print() = 0;
    virtual int calculateInputCount(vector<int>& start) = 0;
    virtual void read(vector<int>& start, vector<int>& input) = 0;
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
        return fitness - penalty;
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
        type = sudoku::THREEBYTHREE;
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
    int calculateInputCount(vector<int>& start)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 4;
            int y = i / 4;

            if(start[i] == 0)
            {
                inputIndex++;
            }
            else
            {
                set(x, y, start[i]);
            }
        }

        clear();

        return inputIndex;
    }
    void read(vector<int>& start, vector<int>& input)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 4;
            int y = i / 4;

            if(start[i] == 0)
            {
                set(x, y, input[inputIndex]);
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
        type = sudoku::THREEBYTHREE;
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
    int calculateInputCount(vector<int>& start)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 9;
            int y = i / 9;

            if(start[i] == 0)
            {
                inputIndex++;
            }
            else
            {
                set(x, y, start[i]);
            }
        }

        clear();

        return inputIndex;
    }
    void read(vector<int>& start, vector<int>& input)
    {
        clear();

        int inputIndex = 0;
        for(int i = 0; i < start.size(); i++)
        {
            int x = i % 9;
            int y = i / 9;

            if(start[i] == 0)
            {
                set(x, y, input[inputIndex]);
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

//Hill Climbing
void hillClimb(sudoku& grid, vector<int>& start, int sudokuMaxValue, uint64_t maximumIterations, int randomChangeAmount)
{
    cout << "Hill climbing is running" << endl;
    vector<int> input = vector<int>(grid.calculateInputCount(start), 0);

    for(int i = 0; i < input.size(); i++)
    {
        //Initial State
        input[i] = random(1, sudokuMaxValue);
    }

    uint64_t maximum = maximumIterations;
    uint64_t count = 0;
    while(maximum > 0)
    {
        maximum--;
        count++;

        grid.read(start, input);
        int currentFitness = grid.getFitness();
        if(currentFitness == grid.getFullFitness())
        {
            break;
        }

        vector<int> neighbour = input;
        int randomAmount = random(1, randomChangeAmount);
        for(int j = 0; j < randomAmount; j++)
        {
            int index = random(0, input.size() - 1);
            neighbour[index] = random(1, sudokuMaxValue);
        }

        grid.read(start, neighbour);
        int neighbourFitness = grid.getFitness();

        if(neighbourFitness > currentFitness)
        {
            cout << "Fitness : " << neighbourFitness << endl;
            currentFitness = neighbourFitness;
            input = neighbour;
        }
    }

    cout << "Finished in : " << count << " iterations" << endl << endl;
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

    auto startHill4x4 = getCurrentTime();
    hillClimb(grid4x4, start4x4, 4, ULONG_MAX, 5);
    cout << "Frame time : " << calculateMS(startHill4x4, getCurrentTime()) << endl << endl;

    auto startHill9x9 = getCurrentTime();
    hillClimb(grid9x9, start9x9, 9, ULONG_MAX, 64);
    cout << "Frame time : " << calculateMS(startHill9x9, getCurrentTime()) << endl << endl;
}