import java.util.*;

public class HashtableVsJavaHasMap {
    static Integer[] hashtableArray = new Integer[11500001];
    static ArrayList<Integer> randomNumberHashMap = new ArrayList<>();
    static HashMap<Integer,Integer> hashMap = new HashMap<>();
    static int lengthTable = hashtableArray.length;
    static int numbOfNumbs = 10000000;
    static int nrOfCollisionsHashTable = 0;


    public int getPositionOneHashTable(int numberValue){
        return numberValue % lengthTable;
    }

    public int probe(int h1, int h2, int i, int m){
        if(h2 == 0) h2 = 1;
        return (h1 + (i * h2)) % m;
    }

    public void placeHashTable(int numberValue, int placement){
        if(hashtableArray[placement] == null){
            hashtableArray[placement] = numberValue;

        }else{
            int h2 = (numberValue % (lengthTable - 1 ) + 1);

            for (int i = 1; i <= lengthTable; i++) {
                int placementTwo = probe(placement,h2,i,lengthTable);

                if(hashtableArray[placementTwo] == null){
                    hashtableArray[placementTwo] = numberValue;
                    break;
                }
                nrOfCollisionsHashTable++;

            }

        }

    }
    public void placeHashMap(int numberValue){
        hashMap.put(numberValue,numberValue);
    }


    public static void main(String[] args) {
        HashtableVsJavaHasMap hashtableVsTable = new HashtableVsJavaHasMap();
        ArrayList<Integer> numbersList = new ArrayList<>();

        int number = 0;
        for(int i = 0; i < numbOfNumbs; i++){
            Random random = new Random();
            int randomNumber = random.nextInt(1,10);
            number += randomNumber;

            numbersList.add(number);
            randomNumberHashMap.add(number);
        }

        long timeHashTableStart = System.nanoTime();
        for (Integer integer : numbersList) {
            int placement = hashtableVsTable.getPositionOneHashTable(integer);
            hashtableVsTable.placeHashTable(integer,placement);
        }
        long timeHashTableEnd = System.nanoTime();

        System.out.println(" ");
        //System.out.println(Arrays.toString(hashtableArray));
        System.out.println("numbers of numbers " + numbersList.size() + " / " + numbOfNumbs);


        long startTimeHashMap = System.nanoTime();
        for (Integer integer : randomNumberHashMap) {
            hashtableVsTable.placeHashMap(integer);
        }
        long endTimeHashmap = System.nanoTime();


        System.out.println("");
        //System.out.println(hashMap);
        System.out.println("numbers of elements: "  + hashMap.size() + " / " + numbOfNumbs);


        System.out.println("");
        long timeHashTable= (timeHashTableEnd - timeHashTableStart)/1000000;
        long timeHashMap = (endTimeHashmap - startTimeHashMap)/1000000;

        System.out.println("Time usage hash table: " + timeHashTable + " milli sec");
        System.out.println("Time usage built in java hashMap: " + timeHashMap + " milli sec");


        //Numbers of collisions and load factor hashtable
        System.out.println("\nNumber of collisions: " + nrOfCollisionsHashTable);
        float loadFactor = (float)numbersList.size() / (float)lengthTable;
        System.out.printf("Load factor " + ("%.2f"),loadFactor);

    }
}