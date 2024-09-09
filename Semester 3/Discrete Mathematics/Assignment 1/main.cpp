#include <bits/stdc++.h>
using namespace std;

bool firstTautology(bool p, bool q)
{
    bool r = p | q;

    return !p | r;
}

bool secondTautology(bool p, bool q)
{
    bool r = !p | q;
    bool s = !(!p)  | r;

    return s;
}

bool firstEquivalentLeft(bool p, bool q, bool r)
{
    bool s = !p | q;
    bool t = !p | r;

    return s & t;
}
bool firstEquivalentRight(bool p, bool q, bool r)
{
    bool s = q & r;
    return !p | s;
}

bool secondEquivalentLeft(bool p, bool q, bool r)
{
    bool s = !p | r;
    bool t = !q | r;

    return s | t;
}
bool secondEquivalentRight(bool p, bool q, bool r)
{
    bool s = p & q;

    return !s | r;
}

typedef bool (*twoInputs)(bool, bool);
typedef bool (*thirdInputs)(bool, bool, bool);
void testTwoInputs(twoInputs method)
{
    bool val[] = {true, false};

    for(bool p : val)
    {
        for(bool q : val)
        {
            bool result = method(p, q);

            cout << p << " " << q << " : " << result << endl;
        }
    }

    cout << endl;
}
void testThirdInputs(thirdInputs method)
{
    bool val[] = {true, false};

    for(bool p : val)
    {
        for(bool q : val)
        {
            for(bool r : val)
            {
                bool result = method(p, q, r);

                cout << p << " " << q << " " << r << " : " << result << endl;
            }
        }
    }

    cout << endl;
}

int main()
{
    testTwoInputs(firstTautology);
    testTwoInputs(secondTautology);

    testThirdInputs(firstEquivalentLeft);
    testThirdInputs(firstEquivalentRight);

    testThirdInputs(secondEquivalentLeft);
    testThirdInputs(secondEquivalentRight);
    
    return 0;
}