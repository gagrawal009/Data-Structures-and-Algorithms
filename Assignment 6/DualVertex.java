public class DualVertex {
    int[] i;
    int[] j;
    int key;
    int flag;
    int level;
    double shortest_value;
    DualVertex pred;

    public DualVertex(int[] i, int[] j, int key){
        this.i = new int[2];
        this.j = new int[2];

        for (int k=0; k<i.length; k++) {
            this.i[k] = i[k];
            this.j[k] = j[k];

        }
        this.key = key;

        this.flag=0;
        this.level=0;
        this.shortest_value = 100000;
    }
}
