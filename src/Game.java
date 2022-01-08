import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JsonObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    List<Account> accountList = new ArrayList<>();
    Map<CellType, List<String>> dictionary = new HashMap<>();

    public void run(String gameMode) {
        JSONParser parser = new JSONParser();
        try {
            //TODO: Parsare Account Json
            JSONObject obj = (JSONObject) parser.parse(new FileReader("src/accounts.json"));
            JSONArray jsonArray = (JSONArray) obj.get("accounts");
            for (Object o : jsonArray) {
                JSONObject person = (JSONObject) o;
                JSONObject credentialsJson = (JSONObject) person.get("credentials");
                String email = (String) credentialsJson.get("email");
                String password = (String) credentialsJson.get("password");
                Credentials credentials = new Credentials(email, password);
                String name = (String) person.get("name");
                String country = (String) person.get("country");
                TreeSet<String> favGames = new TreeSet<String>(new MyComparator());
                JSONArray favGamesJson = (JSONArray) person.get("favorite_games");
                for (Object o1: favGamesJson) {
                    favGames.add((String) o1);
                }
                Account.Information information = new Account.Information.InformationBuilder(credentials, name)
                        .country(country).games(favGames).build();
                List<Character> characterList = new ArrayList<>();
                JSONArray characters = (JSONArray) person.get("characters");
                CharacterFactory characterFactory = new CharacterFactory();
                for(Object o2 : characters) {
                    JSONObject characterJson = (JSONObject) o2;
                    Character character = characterFactory.getCharacter((String) characterJson.get("profession"),
                            (String) characterJson.get("name"),
                           Integer.parseInt((String) characterJson.get("experience")),
                            Integer.parseInt((String) characterJson.get("level")));
                    characterList.add(character);
                }
                int gamesCompleted =  Integer.parseInt((String) person.get("maps_completed"));
                accountList.add(new Account(information, characterList, gamesCompleted));
            }
            //TODO: Parsare Stories JSON
            obj = (JSONObject) parser.parse(new FileReader("src/stories.json"));
            jsonArray = (JSONArray) obj.get("stories");
            dictionary.put(CellType.EMPTY, new ArrayList<>());
            dictionary.put(CellType.ENEMY, new ArrayList<>());
            dictionary.put(CellType.SHOP, new ArrayList<>());
            dictionary.put(CellType.FINISH, new ArrayList<>());
            for (Object o : jsonArray) {
                JSONObject story = (JSONObject) o;
                String type = (String) story.get("type");
                String text = (String) story.get("value");
                switch (type) {
                    case "EMPTY":
                        dictionary.get(CellType.EMPTY).add(text);
                        break;
                    case "ENEMY":
                        dictionary.get(CellType.ENEMY).add(text);
                        break;
                    case "SHOP":
                        dictionary.get(CellType.SHOP).add(text);
                        break;
                    case "FINISH":
                        dictionary.get(CellType.FINISH).add(text);
                }
            }
        }catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Data uploaded. Please login to an account");
        Scanner keyboard = new Scanner(System.in);
        Account currentAccount = null;
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Email:");
            String email = keyboard.nextLine();
            System.out.println("Password:");
            String password = keyboard.nextLine();
            for (Account account : accountList) {
                if (account.information.getCredentials().getEmail().equals(email) && account.information.getCredentials()
                        .getPassword().equals(password)) {
                    currentAccount = account;
                    loggedIn = true;
                }
            }
            if(!loggedIn) {
                System.out.println("This account doesn't exist. Try again.\n");
            }
        }
        System.out.println("You logged in as " + currentAccount.information.getName() + "\n");
        System.out.println("Please select a character. Insert the number of the character\n");
        int index = 1;
        for (Character character: currentAccount.characters) {
            System.out.println(index + ".  " + character.toString2());
            System.out.println();
            index++;
        }
        int chosenCharacterNumber = keyboard.nextInt();
        Character currentCharacter = currentAccount.characters.get(chosenCharacterNumber - 1);
        System.out.println("You chose " + currentCharacter.characterName + "\n");
        System.out.println("Generating map\n");
        Grid board = Grid.generateMap(5, 5 , currentCharacter);
        System.out.println("Map generated\n");
        board.printMap();
        System.out.println("\n Starting the game. Press \"P\" to make the next move");
        while (board.currentCell.cellType != CellType.FINISH) {
            if(keyboard.next().equals("P")) {
                //TODO: Analizat pasii
            }
        }
    }

    void displayOptions(Cell currentCell) {

    }

    void displayStory(Cell currentCell) {

    }
}
