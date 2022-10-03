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
    int characterNumber = 1;

    public int getValueName(String name) {
        int nameValue = 0;
        int letterValue = 0;
        String nameWithoutSpaces = name.replaceAll(" ","");

        characterNumber = 1;

        for (int j = 0; j < nameWithoutSpaces.length(); j++) {
            char[] lettersInName = nameWithoutSpaces.toCharArray();

                if(lettersInName[j] == 'æ'){
                    letterValue = 37;
                }else if((lettersInName[j] == 'ø') ){ letterValue = 38;
                }
                else if(lettersInName[j] == 'å'){
                    letterValue = 39;
                }else{
                letterValue = Character.getNumericValue(lettersInName[j]);
                }
            nameValue += (letterValue * characterNumber);
            characterNumber++;
        }
        return nameValue;
    }

    public int getPositionHashTable(int numberValue, int lengthTable){

        return numberValue % lengthTable;
    }

    public void settInn(int numberValue, int placement){

        if(hashtableArray[placement] == null){
            LinkedList<Object> linkedList = new LinkedList<>();
            linkedList.add(numberValue);
            hashtableArray[placement] = linkedList;
        }else{
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
//TODO: find out how to reference to file correctly
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
                System.out.println(name + " takes this course!");
            }else{
                System.out.println(name + " does not take this course, or you did not type in their full name...");
            }


        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}