import java.util.*;

public class HashtableVsTable {
    static LinkedList[] hashtableArray = new LinkedList[121];
    static ArrayList<Integer> randomNumberHashMap = new ArrayList<>();
    static HashMap<Integer,Integer> hashMap = new HashMap<>();
    static int lengthTable = hashtableArray.length;
    static int numbOfNumbs = 113;
    static int nrOfCollisionsHashTable = 0;
    static int nrOfCollisionsHashMap = 0;


    public int getPositionOneHashTable(int numberValue){
        return numberValue % lengthTable;
    }

    public int probe(int h1, int h2, int i, int m){
        if(h2 == 0) h2 = 1;
        return (h1 + (i * h2)) % m;
    }

    public void placeHashTable(int numberValue, int placement){
        if(hashtableArray[placement] == null){
            LinkedList<Object> linkedList = new LinkedList<>();
            linkedList.add(numberValue);
            hashtableArray[placement] = linkedList;

        }else{
            int h1 = getPositionOneHashTable(numberValue);
            int h2 = (numberValue % (lengthTable - 1 ) + 1);

            for (int i = 1; i <= lengthTable; i++) {
                int placementTwo = probe(h1,h2,i,lengthTable);

                if(hashtableArray[placementTwo] == null){
                    LinkedList<Object> linkedList = new LinkedList<>();
                    linkedList.add(numberValue);
                    hashtableArray[placementTwo] = linkedList;
                    break;
                }
                nrOfCollisionsHashTable++;

            }

        }

    }

    public void placeHashMap(int numberValue, int placement) {

        if (hashMap.get(placement) == null) {
            hashMap.put(placement,numberValue);
        }else{

            int h1 = getPositionOneHashTable(numberValue);
            int h2 = (numberValue % (lengthTable - 1 ) + 1);

            for (int i = 1; i <= lengthTable; i++) {
                int placementTwo = probe(h1,h2,i,lengthTable);

                if(hashMap.get(placementTwo) == null){
                    hashMap.put(placementTwo,numberValue);
                    break;
                }
                nrOfCollisionsHashMap++;

            }
        }

    }

    public static void main(String[] args) {
        HashtableVsTable hashtableVsTable = new HashtableVsTable();
        ArrayList<Integer> numbersList = new ArrayList<>();

        for(int i = 0; i < numbOfNumbs; i++){
            Random random = new Random();
            int randomNumber = random.nextInt(1,1000);
            numbersList.add(randomNumber);
            randomNumberHashMap.add(randomNumber);
        }

        long timeHashTableStart = System.nanoTime();
        for (Integer integer : numbersList) {
            int placement = hashtableVsTable.getPositionOneHashTable(integer);
            hashtableVsTable.placeHashTable(integer,placement);
        }
        long timeHashTableEnd = System.nanoTime();

        for (int i = 0; i < hashtableArray.length; i++) {
            if(hashtableArray[i] == null) hashtableArray[i] = new LinkedList<>();
        }

        System.out.println(" ");
        System.out.println(Arrays.toString(hashtableArray));

        int numberOfElements = 0;
        for (LinkedList linkedList : hashtableArray) {
            if (!linkedList.isEmpty()) {
                numberOfElements++;
            }
        }
        //System.out.println("numbers of numbers " + numberOfElements + " / " + numbOfNumbs);


        long startTimeHashMap = System.nanoTime();
        for (Integer integer : randomNumberHashMap) {
            int placementHashMap = hashtableVsTable.getPositionOneHashTable(integer);
            hashtableVsTable.placeHashMap(integer, placementHashMap );
        }
        long endTimeHashmap = System.nanoTime();

        System.out.println("");
        System.out.println(hashMap);


        //System.out.println("numbers of elements: "  + hashMap.size() + " / " + numbOfNumbs);
        System.out.println("");
        long timeHashTable= (timeHashTableEnd - timeHashTableStart)/1000;

        long timeHashMap = (endTimeHashmap - startTimeHashMap)/1000;

        System.out.println("Time usage hash table: " + timeHashTable + " micro sec");
        System.out.println("Time usage built in java hashMap: " + timeHashMap + " micro sec");

        //Numbers of collisions and load factor are the same for each method
        System.out.println("\nNumber of collisions: " + nrOfCollisionsHashTable);

        float loadFactor = (float)numbersList.size() / (float)lengthTable;
        System.out.printf("Load factor " + ("%.2f"),loadFactor);

    }
}