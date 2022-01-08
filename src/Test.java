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

        System.out.println("We are ready to go! Please choose GUI or CLI");
        Scanner keyboard = new Scanner(System.in);
        String gameMode = keyboard.nextLine();
        Game game = new Game();
        game.run(gameMode);
//        JSONParser parser = new JSONParser();
//        CharacterFactory characterFactory = new CharacterFactory();
//        try {
//            JSONObject obj = (JSONObject) parser.parse(new FileReader("src/accounts.json"));
//            JSONArray jsonArray = (JSONArray) obj.get("accounts");
//            JSONObject person = (JSONObject)jsonArray.get(0);
//            JSONArray characters = (JSONArray) person.get("characters");
//            JSONObject character = (JSONObject) characters.get(0);
//            Character chosenCharacter = characterFactory.getCharacter((String) character.get("profession"),
//                            (String) character.get("name"),
//                            Integer.parseInt((String) character.get("experience")),
//                            Integer.parseInt((String) character.get("level")));
////            for( Object o : jsonObject) {
////                JSONObject person = (JSONObject) o;
////                System.out.println();
////                System.out.println(person.get("name"));
////                System.out.println("Characters");
////                JSONArray characters = (JSONArray) person.get("characters");
////                for (Object o1 : characters) {
////                    JSONObject character = (JSONObject) o1;
////                    Character chosenCharacter = characterFactory.getCharacter((String) character.get("profession"),
////                            (String) character.get("name"),
////                            Integer.parseInt((String) character.get("experience")),
////                            Integer.parseInt((String) character.get("level")));
//////                    System.out.println("Name:" + chosenCharacter.characterName + " Experience:" + chosenCharacter.exp +
//////                            " Level:" + chosenCharacter.level);
////                    chosenCharacter.inventory.addPotion(new HealthPotion());
////                    chosenCharacter.inventory.addPotion(new ManaPotion());
////                    System.out.println(chosenCharacter.inventory.potions);
////                    //System.out.println(chosenCharacter.inventory.weightRemained());
//////                    chosenCharacter.receiveDamage(50);
//////                    System.out.println(chosenCharacter.currentHealth);
//////                    chosenCharacter.inventory.useHealthPotion(chosenCharacter);
//////                    System.out.println(chosenCharacter.currentHealth);
//////                    System.out.println(chosenCharacter.inventory.potions);
////                    chosenCharacter.addSpell(new Fire());
////                    chosenCharacter.addSpell(new Ice());
////                    chosenCharacter.addSpell(new Earth());
////                    System.out.println(chosenCharacter.spells);
////                    System.out.println(chosenCharacter.currentHealth);
////                    chosenCharacter.useSpell(chosenCharacter.spells.get(1), chosenCharacter);
////                    System.out.println(chosenCharacter.currentHealth);
////                    chosenCharacter.useSpell(chosenCharacter.spells.get(2), chosenCharacter);
////                    System.out.println(chosenCharacter.currentHealth);
////                    chosenCharacter.inventory.useManaPotion(chosenCharacter);
////                    chosenCharacter.useSpell(chosenCharacter.spells.get(2), chosenCharacter);
////                    System.out.println(chosenCharacter.currentHealth);
//            Grid game = Grid.generateMap(5, 5, chosenCharacter);
//            game.printMap();
//            game.goEast();
//            game.goEast();
//            game.goEast();
//            if(game.currentCell.cellType == CellType.SHOP) {
//                ((Shop)game.currentCell.cellElement).printPotionList();
//            }
//            chosenCharacter.buy(((Shop)game.currentCell.cellElement).getPotion(0));
//            chosenCharacter.buy(((Shop)game.currentCell.cellElement).getPotion(0));
//            System.out.println(chosenCharacter.inventory.potions);
//            ((Shop)game.currentCell.cellElement).printPotionList();
//            game.goEast();
//            game.goSouth();
//            game.goSouth();
//            game.goSouth();
////                }
////            }
//
//
//        } catch(IOException | ParseException e) {
//            e.printStackTrace();
//        }



    }
}
