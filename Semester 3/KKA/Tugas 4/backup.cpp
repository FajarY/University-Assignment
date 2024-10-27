#include <bits/stdc++.h>
using namespace std;

class sudoku
{
    protected:
    vector<vector<int>> grid;
    vector<uint32_t> gridSet;
    vector<uint32_t> subgridSet;
    int type;

    virtual int get_subgrid_index(int x, int y) = 0;
    virtual void set_contains(int x, int y, int value) = 0;

    public:
    const int FOURBYFOUR = 0;
    const int THREEBYTHREE = 1;

    virtual bool set(int x, int y, int value) = 0;
    virtual bool contains(int x, int y, int value) = 0;
    virtual int get(int x, int y) = 0;
    virtual void print() = 0;
    int get_type()
    {
        return type;
    }
    virtual void read_file(string path) = 0;
};

class sudoku3x3 : public sudoku
{
    protected:
    int get_subgrid_index(int x, int y)
    {
        return (x / 3) + 3 * (y / 3);
    }
    void set_contains(int x, int y, int value)
    {
        int current = get(x, y);

        if(current != 0)
        {
            gridSet[x] &= ~(1 << (current - 1));
            gridSet[y] &= ~(1 << (current + 8));
            subgridSet[get_subgrid_index(x, y)] &= ~(1 << (current - 1));
        }
        if(value != 0)
        {
            gridSet[x] |= (1 << (value - 1));
            gridSet[y] |= (1 << (value + 8));
            subgridSet[get_subgrid_index(x, y)] |= (1 << (value - 1));
        }
    }

    public:
    sudoku3x3()
    {
        grid = vector<vector<int>>(9, vector<int>(9, 0));
        gridSet = vector<uint32_t>(9, 0);
        subgridSet = vector<uint32_t>(9, 0);
        type = sudoku::THREEBYTHREE;
    }
    bool set(int x, int y, int value)
    {
        if(!contains(x, y, value) || value == 0)
        {
            set_contains(x, y, value);
            grid[y][x] = value;
            return true;
        }

        return false;
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
    int get(int x, int y)
    {
        return grid[y][x];
    }
    void read_file(string path)
    {
        fstream file(path);
        if (!file.is_open())
        {
            cerr << "Error openning file" << endl;
            return;
        }

        for(int y = 8; y >= 0; y--)
        {
            for(int x = 0; x < 9; x++)
            {
                int current;
                file >> current;

                set(x, y, current);
            }
        }
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
        int current = get(x, y);

        if(current != 0)
        {
            gridSet[x] &= ~(1 << (current - 1));
            gridSet[y] &= ~(1 << (current + 3));
            subgridSet[get_subgrid_index(x, y)] &= ~(1 << (current - 1));
        }
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
    bool set(int x, int y, int value)
    {
        if(!contains(x, y, value) || value == 0)
        {
            set_contains(x, y, value);
            grid[y][x] = value;
            return true;
        }

        return false;
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
    int get(int x, int y)
    {
        return grid[y][x];
    }
    void read_file(string path)
    {
        fstream file(path);
        if (!file.is_open())
        {
            cerr << "Error openning file" << endl;
            return;
        }

        for(int y = 3; y >= 0; y--)
        {
            for(int x = 0; x < 4; x++)
            {
                int current;
                file >> current;

                set(x, y, current);
            }
        }
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
    }
    void clear()
    {
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

//Hill Climbing
void hillClimb4x4(sudoku4x4& grid)
{

}

int main()
{
    sudoku4x4 grid = sudoku4x4();

    grid.read_file("4x4.txt");

    grid.print();
}