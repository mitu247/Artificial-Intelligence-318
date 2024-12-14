#include<bits/stdc++.h>
using namespace std;
int n;
set<string> un;

int merge(int arr[], int l, int q, int r) {

    int n1 = q - l;
    int n2 = r - q + 1;
    int inv_count = 0;

    int L[n1], M[n2];

    for (int i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (int j = 0; j < n2; j++)
        M[j] = arr[q + j];

    int i, j, k;
    i = j = 0;k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= M[j]) {
            arr[k++] = L[i];
            i++;
        } else {
            arr[k++] = M[j];
            inv_count = inv_count + (q - (l+i));
            j++;
        }
    }
    while (i < n1) {
        arr[k++] = L[i++];
    }
    while (j < n2) {
        arr[k++] = M[j++];
    }
    return inv_count;
}
int mergeSort(int arr[],int l, int r)
{
    int mid, inv_count = 0;
    if (l<r) {
        mid = (l+r-1) / 2;
        inv_count += mergeSort(arr,l, mid);
        inv_count += mergeSort(arr,mid + 1, r);
        inv_count += merge(arr,l, mid+1, r);
    }
    return inv_count;
}
int inversionCount(int arr[5][5]){
    int a[10000],k;
    k = 0;
    for(int i = 0; i<n; i++){
        for(int j = 0; j<n; j++){
            if(arr[i][j]!=0){
                a[k] = arr[i][j];
                k++;
            }
        }
    }
    return mergeSort(a,0,k-1);
}
bool isSolvable(int arr[5][5]){
    int k = 0,val;
    val = inversionCount(arr);
     for(int i = 0; i<n; i++){
        for(int j = 0; j<n; j++){
            if(arr[i][j]==0){
                k=i;
                break;
            }
        }
    }

    if (n%2 == 1) {
        if (val%2 == 0) return true;
        return false;
    }
    else {
        int x = val + ((n-k)%2 ? 0 : 1);
        if (x%2 == 0) return true;
        return false;
    }
}
class tile{
private:
    int arr[5][5];
    int size;
    int distance;
    tile *parent;
    int moves;
    int getHammingDistance(int arr[5][5]){
        int k = 1;
        int dist = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(arr[i][j]!=0 && arr[i][j]!=k){
                dist++;
                }
                k++;
            }
        }
        return dist;
    }
    int getManhattanDistance(int arr[5][5]){
        int dist = 0;
        int k = 1;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
            if(arr[i][j]!=0 && arr[i][j]!=k){
                int i_m,j_m;
                if(arr[i][j]%n){
                    i_m = arr[i][j]/n;
                    j_m = arr[i][j]%n -1;
                }
                else{
                i_m = arr[i][j]/n -1;
                j_m = n-1;
                }
                dist += abs(i_m-i) + abs(j_m-j);
                }
                k++;
            }
        }
        return dist;
    }

    public:
    int blankX, blankY;

    tile(int arr[5][5], int size, tile* t = NULL){
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                this->arr[i][j] = arr[i][j];
            }
        }
        this->size = size;
        this->parent = t;
        this->moves = 0;
        this->distance = getManhattanDistance(this->arr);
    }
    void setMove(int move){
        this->moves = move;
    }
    int getMove(){
        return this->moves;
    }
    tile* getParent(){
        return this->parent;
    }
    int getDistance(){
        return this->distance;
    }
    bool isGoalTile(){
        return this->distance==0;
    }
    tile* getChild(int bx, int by, int x, int y) {
        int newa[5][5];

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                newa[i][j] = arr[i][j];
            }
        }

        newa[bx][by] = newa[x][y];
        newa[x][y] = 0;

        std::string str;

        for (int i = 0 ; i < n; ++i)
        {
            for (int j = 0; j < n; ++j)
            {
                str.append(std::to_string(newa[i][j]));
            }
        }
        if (un.find(str) != un.end()) return NULL;
        un.insert(str);

        tile* child = new tile(newa, size, this);
        child->blankX = x;
        child->blankY = y;
        child->setMove(this->moves + 1);
        // cout << "hello" << endl;

        return child;
    }
    void print() {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                cout << arr[i][j] << ' ';
            }
            cout << endl;
        }
        cout << endl;
    }
};
class Compare {
public:
 bool operator()(tile* a, tile* b)
    {
    if ((a->getMove() + a->getDistance()) < (b->getMove() + b->getDistance())) {
            return false;
        }
    return true;
    }
};

int main() {
    fstream cin("in.txt");

    int arr[5][5];

    cin >> n;
    std::string str;
    int x=0, y=0;

    for(int i = 0; i<n; i++){
        for(int j = 0; j<n; j++){
            cin>>arr[i][j];
            str.append(std::to_string(arr[i][j]));

            if (arr[i][j] == 0) {
                x = i;
                y = j;
            }
        }
    }
    un.insert(str);
    // cout<<getHammingDistance(arr)<<endl;
    // cout<<getManhattanDistance(arr)<<endl;
    int checkMove = 1;
    if (isSolvable(arr)) {
        priority_queue<tile*, std::vector<tile*>, Compare> pq;
        tile* newTile = new tile(arr,n,NULL);
        newTile->blankX = x;
        newTile->blankY = y;
        tile* child = NULL;

        pq.push(newTile);

        while(!pq.empty()){
            newTile = pq.top();
            pq.pop();
            // cout << "hello" << endl;

            int bx = newTile->blankX;
            int by = newTile->blankY;


            if (bx+1 < n) {
                // cout << "hello" << endl;
                child = newTile->getChild(bx, by, bx+1, by);
                if (child){
                    if (child->isGoalTile()) {
                        break;
                    }
                    pq.push(child);
                }
                // child->print();
                // cout << "hello" << endl;
                checkMove++;
            }
            if (bx-1 >= 0) {
                child = newTile->getChild(bx, by, bx-1, by);
                if (child){
                    if (child->isGoalTile()) {
                        break;
                    }
                    pq.push(child);
                }
                checkMove++;
                // child->print();
            }
            if (by+1 < n) {
                child = newTile->getChild(bx, by, bx, by+1);
                if (child){
                    if (child->isGoalTile()) {
                        break;
                    }
                    pq.push(child);
                }
                checkMove++;
                // child->print();
            }
            if (by-1 >= 0) {
                child = newTile->getChild(bx, by, bx, by-1);
                if (child){
                    if (child->isGoalTile()) {
                        break;
                    }
                    pq.push(child);
                }
                checkMove++;
                // child->print();
            }
            // child->print();

            // delete newTile;
        }
        newTile = child;
        vector<tile*> listTile;
        cout << "Minimum number of Moves: " << newTile->getMove() << "\n" << endl;
        while (newTile!=NULL) {
            listTile.push_back(newTile);
            // destTile->print();
            newTile = newTile->getParent();
        }

        for (int i=listTile.size()-1; i>=0; i--) {
            listTile[i]->print();
        }
    }
    else {
        cout << "Unsolvable puzzle" << endl;
    }
    //cout<<checkMove<<endl;
    return 0;
}
