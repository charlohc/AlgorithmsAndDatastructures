import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WeightedGraph {
    int N,K;
    Node[]node;
    int[] distance;
    int[] forrige;
    int startNode = 1;

    class Kant{
        Kant neste;
        Node til;

        public Kant(Node n, Kant nst) {
            til = n;
            neste = nst;
        }
    }

    class Node {
        Kant kant1;
        Object d;
    }

    class VKant extends Kant{
        int vekt;
        public VKant(Node n, VKant nst, int vkt){
            super(n,nst);
            vekt = vkt;
        }
    }

    class Forgj{
        int dist;
        Node forgj;
        static int uendelig = 100000;

        public int finn_dist(){
            return dist;
        }

        public Node finn_forgj(){
            return forgj;
        }

        public Forgj(){
            dist = uendelig;
        }
    }

    public void ny_vgraf(BufferedReader br) throws IOException{
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];

        for (int i = 0; i < N; ++i) {
            node[i] = new Node();
        }
        K = Integer.parseInt(st.nextToken());

        for(int i = 0; i < K; ++i){
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            int vekt = Integer.parseInt(st.nextToken());
            VKant k = new VKant(node[til], (VKant) node[fra].kant1, vekt);
            node[fra].kant1 = k;
        }
    }

    public int returnIndexOfNode(Node nodeInput){
        for (int i = 0; i < node.length; i++) {
            if(nodeInput == node[i]){
                return i;
            }
        }
        return -1;
    }


    private Node hent_min(int i) {
        Node[] pri = new Node[node.length];
        int shortestDistance = Forgj.uendelig;
        VKant tempKant = (VKant) node[i].kant1;
        pri[0] = null;

            while (tempKant != null) {
                if (tempKant.vekt < shortestDistance) {
                    shortestDistance = tempKant.vekt;
                    pri[0] = tempKant.til;
                }
                tempKant = (VKant) tempKant.neste;
            }

        return pri[0];


    }


    public void dijkstra() {
        distance = new int[node.length];
        forrige = new int[node.length];
        boolean[] besokt = new boolean[node.length];
        Arrays.fill(distance, Forgj.uendelig);
        Arrays.fill(besokt, false);

        VKant startTempKant = (VKant) node[startNode].kant1;
        distance[startNode] = 0;
        forrige[startNode] = -1;


        while (hent_min(returnIndexOfNode(node[startNode])) != null && !besokt[returnIndexOfNode(node[startNode])]) {
           // System.out.println("-".repeat(30));
            // System.out.println("start node " + returnIndexOfNode(node[startNode]));

            while (startTempKant != null) {
                //System.out.println(" nabo " + returnIndexOfNode(startTempKant.til));

                if ((startTempKant.vekt + distance[startNode]) < distance[returnIndexOfNode(startTempKant.til)]) {
                    distance[returnIndexOfNode(startTempKant.til)] = startTempKant.vekt + distance[startNode];

                    forrige[returnIndexOfNode(startTempKant.til)] = startNode;
                }

                startTempKant = (VKant) startTempKant.neste;
            }
            //System.out.println(Arrays.toString(distance));
            besokt[startNode] = true;

            startNode = returnIndexOfNode(hent_min(returnIndexOfNode(node[startNode])));

            startTempKant = (VKant) node[startNode].kant1;

        }
    }

    public void printGraph(){
        for (int i = 0; i < node.length; i++) {
            System.out.print(i + ":");

            VKant tempKant = (VKant) node[i].kant1;

            while (tempKant != null) {
                System.out.print(" " + returnIndexOfNode(tempKant.til));
                System.out.print("(vekt " + tempKant.vekt + "), ");

                tempKant = (VKant) tempKant.neste;
            }

            System.out.println(" ");

        }

    }


    public static void main(String[] args) throws IOException {

        WeightedGraph weightedGraph = new WeightedGraph();

        String filename = "vg5";

        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator")  + filename + ".txt"));

        weightedGraph.ny_vgraf(br);

        System.out.println(" \n" + "Dijkstras algoritme på " + filename );

        System.out.println("-".repeat(30));
        weightedGraph.printGraph();

        weightedGraph.dijkstra();

        System.out.println(" \n" + "-".repeat(30));
        System.out.println("Node     Forgjenger    Distanse");

        for (int i = 0; i < weightedGraph.N; i++) {
            if(weightedGraph.forrige[i] == -1) {
                System.out.println(i + " ".repeat(10)  + "start" + " ".repeat(10)  + weightedGraph.distance[i]);

                }else if(weightedGraph.distance[i] == Forgj.uendelig){
                    System.out.println(i + " ".repeat(10)  + "node nåes ikke");

                } else {
                System.out.println(i + " ".repeat(12) + weightedGraph.forrige[i] + " ".repeat(12)  + weightedGraph.distance[i]);
            }
        }
    }
}
