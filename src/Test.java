import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import netscape.javascript.JSObject;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {

    public static void main(String[] args) {

        JSONParser parser = new JSONParser();
        CharacterFactory characterFactory = new CharacterFactory();
        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader("src/accounts.json"));
            JSONArray jsonObject = (JSONArray) obj.get("accounts");

            for( Object o : jsonObject) {
                JSONObject person = (JSONObject) o;
                System.out.println();
                System.out.println(person.get("name"));
                System.out.println("Characters");
                JSONArray characters = (JSONArray) person.get("characters");
                for (Object o1 : characters) {
                    JSONObject character = (JSONObject) o1;
                    Character chosenCharacter = characterFactory.getCharacter((String) character.get("profession"),
                            (String) character.get("name"),
                            Integer.parseInt((String) character.get("experience")),
                            Integer.parseInt((String) character.get("level")));
//                    System.out.println("Name:" + chosenCharacter.characterName + " Experience:" + chosenCharacter.exp +
//                            " Level:" + chosenCharacter.level);
                    chosenCharacter.inventory.addPotion(new HealthPotion());
                    chosenCharacter.inventory.addPotion(new ManaPotion());
                    System.out.println(chosenCharacter.inventory.potions);
                    //System.out.println(chosenCharacter.inventory.weightRemained());
//                    chosenCharacter.receiveDamage(50);
//                    System.out.println(chosenCharacter.currentHealth);
//                    chosenCharacter.inventory.useHealthPotion(chosenCharacter);
//                    System.out.println(chosenCharacter.currentHealth);
//                    System.out.println(chosenCharacter.inventory.potions);
                    chosenCharacter.addSpell(new Fire());
                    chosenCharacter.addSpell(new Ice());
                    chosenCharacter.addSpell(new Earth());
                    System.out.println(chosenCharacter.spells);
                    System.out.println(chosenCharacter.currentHealth);
                    chosenCharacter.useSpell(chosenCharacter.spells.get(1), chosenCharacter);
                    System.out.println(chosenCharacter.currentHealth);
                    chosenCharacter.useSpell(chosenCharacter.spells.get(2), chosenCharacter);
                    System.out.println(chosenCharacter.currentHealth);
                    chosenCharacter.inventory.useManaPotion(chosenCharacter);
                    chosenCharacter.useSpell(chosenCharacter.spells.get(2), chosenCharacter);
                    System.out.println(chosenCharacter.currentHealth);
                }
            }


        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
