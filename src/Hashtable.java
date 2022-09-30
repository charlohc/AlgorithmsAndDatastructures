import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Hashtable {
    static LinkedList[] hashtableArray = new LinkedList[117];
    static int placementInt;
    static int nameValueInt;
    static int nrOfCollisions = 0;

    public int getValueName(String name) {
        int nameValue = 0;
        int letterValue = 0;
        String nameWithoutSpaces = name.replaceAll(" ","");

        //System.out.println("name value " + nameWithoutSpaces + ": ");

            for (int j = 0; j < nameWithoutSpaces.length(); j++) {
                char[] lettersInName = nameWithoutSpaces.toCharArray();

               // if(lettersInName[j] == 'æ')
                //if(lettersInName[j] == 'ø') letterValue = 38;
               // if(lettersInName[j] == 'å')

                letterValue = Character.getNumericValue(lettersInName[j]);
                nameValue += (letterValue);

            }
        return nameValue;
    }

    public int getPositionHashTable(int numberValue, int lengthTable){

        return numberValue % lengthTable;
    }

    public void settInn(int numberValue, int placement){

        //System.out.print("  number Value: " + numberValue + " index: " + placement);
        if(hashtableArray[placement] == null){
            LinkedList<Object> linkedList = new LinkedList<>();
            linkedList.add(numberValue);
            hashtableArray[placement] = linkedList;
        }else{
            //System.out.println("Collision at index: " + placement + " numbers " + numberValue);
            hashtableArray[placement].push(numberValue);
            nrOfCollisions++;

        }

    }

    public boolean containsKey(String name) {
        int nameIntSearch = getValueName(name);

        for (LinkedList linkedList : hashtableArray) {
                if(linkedList.contains(nameIntSearch)){
                    return true;
                }
            }
        return false;
        }


    public static void main (String[]args){
        Hashtable hashtable = new Hashtable();

        try {
            File myObj = new File("C:\\Users\\charl\\Documents\\Algoritmer og datastrukturer\\HashtableTextKey\\src\\names.txt");
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> namesList = new ArrayList<>();
           ArrayList<Integer> namesValue = new ArrayList<>();

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                namesList.add(data);
            }
            myReader.close();

            for (String s : namesList) {
                namesValue.add(hashtable.getValueName(s));
            }

            for (Integer integer : namesValue) {
                for (int j = 0; j < integer; j++) {
                    nameValueInt = integer;
                    placementInt = hashtable.getPositionHashTable(integer, hashtableArray.length);
                }
                hashtable.settInn(nameValueInt,placementInt);
            }

            for (int i = 0; i < hashtableArray.length; i++) {
                if(hashtableArray[i] == null) hashtableArray[i] = new LinkedList<>();
            }

            System.out.println();

            System.out.println(Arrays.toString(hashtableArray));

            float nrOfCollisionsPerPerson = (float)nrOfCollisions/ (float) namesList.size();
            System.out.printf("\n Total number of collisions: " + nrOfCollisions + " \n number of collisions per person: " + ("%.2f"),nrOfCollisionsPerPerson);

            float loadFactor = (float)namesList.size() / (float)hashtableArray.length;
            System.out.printf(" \n Load factor " + ("%.2f"),loadFactor);


            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("\n Search for a name: ");
            String name = sc.nextLine();

            hashtable.containsKey(name);
            if(hashtable.containsKey(name)){
                System.out.println(name + " går i klassen!");
            }else{
                System.out.println(name + " tar ikke dette faget, eller så skrev du ikke inn det fulle navnet...");
            }




        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}

