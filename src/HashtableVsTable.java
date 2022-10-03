import java.util.*;

public class HashtableVsTable {
    static LinkedList[] hashtableArray = new LinkedList[121];
    static int lengthTable = hashtableArray.length;
    static int numbOfNumbs = 113;
    static int counter = 0;
    int nrOfCollisions = 0;
    static int counterHashMap = 0;

    public int getPositionOneHashTable(int numberValue){
        return numberValue % lengthTable;
    }

    public int probe(int h1, int h2, int i, int m){
        if(h2 == 0) h2 = 1;
        return (h1 + (i * h2)) % m;
    }

    public void settInn(int numberValue, int placement){
        if(hashtableArray[placement] == null){
            LinkedList<Object> linkedList = new LinkedList<>();
            linkedList.add(numberValue);
            hashtableArray[placement] = linkedList;

        }else{
            int h1 = getPositionOneHashTable(numberValue);
            int h2 = (numberValue % (lengthTable - 1 ) + 1);

            for (int i = 1; i <= hashtableArray.length; i++) {
                int placementTwo = probe(h1,h2,i,hashtableArray.length);

                if(hashtableArray[placementTwo] == null){
                    LinkedList<Object> linkedList = new LinkedList<>();
                    linkedList.add(numberValue);
                    hashtableArray[placementTwo] = linkedList;
                    break;
                }
                nrOfCollisions++;

            }
//TODO: finne plassering til de som ikke blir plassert etter probing
/*
            for (LinkedList linkedList : hashtableArray) {
                if (!linkedList.contains(numberValue)) {
                    hashtableArray[placement].push(numberValue);
                    nrOfCollisions++;
                }
            }
 */

        }

    }


    public static void main(String[] args) {
        HashtableVsTable hashtableVsTable = new HashtableVsTable();
        ArrayList<Integer> numbersList = new ArrayList<>();
        ArrayList<Integer> randomNumberHashMap = new ArrayList<>();
        int[] tabel = new int[numbOfNumbs];
        HashMap<Integer,Integer> hashMap = new HashMap<>();

        for(int i = 0; i < numbOfNumbs; i++){
            Random random = new Random();
            int randomNumber = random.nextInt(1,1000);
            numbersList.add(randomNumber);
        }

        long timeHashTableStart = System.nanoTime();
            for (Integer integer : numbersList) {
                int placement = hashtableVsTable.getPositionOneHashTable(integer);
                hashtableVsTable.settInn(integer,placement);
            }
        long timeHashTableEnd = System.nanoTime();

        for (int i = 0; i < hashtableArray.length; i++) {
            if(hashtableArray[i] == null) hashtableArray[i] = new LinkedList<>();
        }

        System.out.println(Arrays.toString(hashtableArray));

        int numberOfElements = 0;
        for (int i = 0; i < hashtableArray.length; i++) {
            if (!hashtableArray[i].isEmpty()){
                numberOfElements++;
            }
        }
        System.out.println("numbers of numbers " + numberOfElements + " / " + numbOfNumbs);

        int lastNumber = 0;
        for(int i = 0; i < numbOfNumbs; i++){
            Random random = new Random();
            int randomNumber = random.nextInt(1,10);
            lastNumber += randomNumber;
            randomNumberHashMap.add(lastNumber);
        }
        System.out.println(randomNumberHashMap.size());

        /*TODO: er det greit at alle får nøkkel i stigende rekkefølge eller burde jeg tildele de etter tallverdi % lengde tabell?
        da får jeg ofte at noen får samme nøkkelverdi selv om de er spredt bra, slik at noen overskriver andre - mister noen.
         */

        long startTimeHashMap = System.nanoTime();
        for (int i = 0; i < randomNumberHashMap.size(); i++) {
            hashMap.put(i, randomNumberHashMap.get(i));
        }
        long endTimeHashmap = System.nanoTime();

        System.out.println("");
        System.out.println(hashMap);
        System.out.println("numbers of elements: "+ hashMap.size() + " / " + numbOfNumbs);
        System.out.println("");
        long timeHashTable= timeHashTableEnd - timeHashTableStart;
        long timeHashMap = endTimeHashmap - startTimeHashMap;
        System.out.println("Time in hashtable placement: " + timeHashTable);
        System.out.println("Time in hashmap placement: " + timeHashMap);

    }
}
