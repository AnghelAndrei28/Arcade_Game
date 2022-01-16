import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game {
    List<Account> accountList = new ArrayList<>();
    Map<CellType, List<String>> dictionary = new HashMap<>();

    JSONObject initialJson;

    private static Game instance;
    private Game() {}

    public static Game getInstance() {
        if(instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void run(String gameMode) {
        initialJson = parsingJson();
        if(gameMode.equals("CLI")) {
            runCLI();
        } else if(gameMode.equals("GUI")) {
            runGUI();
        }
    }

    JSONObject parsingJson() {
        JSONObject initialJson = null;
        JSONParser parser = new JSONParser();
        try {
            //TODO: Parsare Account Json
            JSONObject obj = (JSONObject) parser.parse(new FileReader("src/accounts.json"));
            initialJson = obj;
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
                long gamesCompleted =  (long) person.get("maps_completed");
                accountList.add(new Account(information, characterList, gamesCompleted));
            }
            //TODO: Parsare Stories JSON
            JSONObject obj1 = (JSONObject) parser.parse(new FileReader("src/stories.json"));
            jsonArray = (JSONArray) obj1.get("stories");
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
            initialJson = obj;
        }catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return initialJson;
    }

    void runCLI () {
        int indexAccount = 0;
        int indexCharacter;
        System.out.println("Uploading data");
        for (Account account: accountList) {
            System.out.println(account);
        }
        System.out.println("\n" + dictionary + "\n");
        System.out.println("Data uploaded. Please login to an account");
        Scanner keyboard = new Scanner(System.in);
        Account currentAccount = null;
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Email:");
            String email = keyboard.nextLine();
            System.out.println("Password:");
            String password = keyboard.nextLine();
            int index = 0;
            for (Account account : accountList) {
                if (account.information.getCredentials().getEmail().equals(email) && account.information.getCredentials()
                        .getPassword().equals(password)) {
                    currentAccount = account;
                    loggedIn = true;
                    indexAccount = index;
                }
                index++;
            }
            if(!loggedIn) {
                System.out.println("This account doesn't exist. Try again.\n");
            }
        }
        System.out.println("You logged in as " + currentAccount.information.getName() + "\n");
        System.out.println("Please select a character. Insert the number of the character\n");
        int index = 1;
        for (Character character: currentAccount.characters) {
            System.out.println(index + ".  " + character);
            System.out.println();
            index++;
        }
        int chosenCharacterNumber = keyboard.nextInt();
        indexCharacter = chosenCharacterNumber - 1;
        Character currentCharacter = currentAccount.characters.get(indexCharacter);
        System.out.println("You chose " + currentCharacter.characterName + "\n");
        System.out.println("Generating map\n");
        Grid board = Grid.generateMap(5, 5 , currentCharacter, false);
        System.out.println("Map generated\n");
        board.printMap();
        System.out.println("\n Starting the game. Press \"P\" to make the next move");
        int step = 1;
        while (board.currentCell.cellType != CellType.FINISH) {
            try {
                if (keyboard.next().equals("P")) {
                    //TODO: Analizat pasii
                    switch (step) {
                        case 1:
                        case 2:
                        case 4:
                            board.goEast();
                            displayStory(board.currentCell, false);
                            displayOptions(board.currentCell, currentCharacter);
                            board.printMap();
                            break;
                        case 3:
                            board.goEast();
                            displayStory(board.currentCell, false);
                            board.currentCell.visited = true;
                            board.printMap();
                            System.out.println("\nList of potions you can buy");
                            boolean healthExist = false;
                            boolean manaExist = false;
                            for (Potion potion : ((Shop) board.currentCell.cellElement).potionList) {
                                if (potion.getClass() == ManaPotion.class) {
                                    manaExist = true;
                                } else {
                                    healthExist = true;
                                }
                            }
                            if (!healthExist) {
                                ((Shop) board.currentCell.cellElement).potionList.remove(0);
                                ((Shop) board.currentCell.cellElement).potionList.add(0, new HealthPotion());
                            } else if (!manaExist) {
                                ((Shop) board.currentCell.cellElement).potionList.remove(0);
                                ((Shop) board.currentCell.cellElement).potionList.add(0, new ManaPotion());
                            }
                            System.out.println("\nPlease select a HealthPotion and a ManaPotion");
                            displayOptions(board.currentCell, currentCharacter);
                            displayOptions(board.currentCell, currentCharacter);
                            ((Shop) board.currentCell.cellElement).printPotionList();
                            System.out.println("\n Your inventory \n");
                            System.out.println(currentCharacter.inventory.potions + "\n");
                            break;
                        case 5:
                        case 6:
                        case 9:
                            board.goSouth();
                            displayStory(board.currentCell, false);
                            displayOptions(board.currentCell, currentCharacter);
                            board.printMap();
                            break;
                        case 7:
                            board.goSouth();
                            displayStory(board.currentCell, false);
                            board.currentCell.visited = true;
                            board.printMap();
                            board.currentCell.visited = false;
                            break;
                        case 8:
                            displayOptions(board.currentCell, currentCharacter);
                            break;
                    }

                    step++;
                } else {
                    System.out.println("Am zis sa apesi P");
                    throw new InvalidCommandException();
                }
            } catch (InvalidCommandException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Congratulations you finished the game");
        currentAccount.playedGames++;
        board.printEndMap();
        ((JSONObject)((JSONArray)initialJson.get("accounts")).get(indexAccount)).put("maps_completed", currentAccount.playedGames);
        ((JSONObject)((JSONArray)((JSONObject)((JSONArray)initialJson.get("accounts")).get(indexAccount)).get("characters")).get(indexCharacter)).put("level", String.valueOf(currentCharacter.level));
        ((JSONObject)((JSONArray)((JSONObject)((JSONArray)initialJson.get("accounts")).get(indexAccount)).get("characters")).get(indexCharacter)).put("experience", String.valueOf(currentCharacter.exp));
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("src/accounts.json");
            fileWriter.write(initialJson.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    void runGUI () {
        new AuthScreen(accountList);

    }

    void displayOptions(Cell currentCell, Character currentCharacter) {
        Scanner keyboard = new Scanner(System.in);
        Random random = new Random();
        switch (currentCell.cellType) {
            case EMPTY:
                if ((random.nextInt(5 - 1) + 1) == 1 && !currentCell.visited) {
                    currentCharacter.inventory.addCash(20);
                    System.out.println("You found money");
                }
                break;
            case SHOP:
                ((Shop) currentCell.cellElement).printPotionList();
                int indexPotion = keyboard.nextInt();
                currentCharacter.buy(((Shop) currentCell.cellElement).getPotion(indexPotion - 1));
                break;
            case ENEMY:
                if (!currentCell.visited) {

                    boolean turn = true;
                    System.out.println("\nEnemy life: " + ((Enemy) currentCell.cellElement).currentHealth);
                    System.out.println("\nEnemy Protections:\nFire: " + ((Enemy) currentCell.cellElement).fire + "\nIce: " + ((Enemy) currentCell.cellElement).ice + "\nEarth: " + ((Enemy) currentCell.cellElement).earth + "\n");
                    while (currentCharacter.currentHealth > 0 && ((Enemy) currentCell.cellElement).currentHealth > 0) {
                        if (turn) {
                            System.out.println("Actions:\n1. Attack enemy\n2. Use spell\n3. Use potion");
                            int index;
                            int option = keyboard.nextInt();
                            switch (option) {
                                case 1:
                                    System.out.println("\nMy atack\n");
                                    currentCharacter.getDamage(null, (Enemy) currentCell.cellElement);
                                    break;
                                case 2:
                                    for (int i = 1; i <= currentCharacter.spells.size(); i++) {
                                        System.out.println(i + ". " + currentCharacter.spells.get(i - 1));
                                    }
                                    index = keyboard.nextInt();
                                    System.out.println("\nMy atack\n");
                                    currentCharacter.useSpell(currentCharacter.spells.get(index - 1), (Enemy) currentCell.cellElement);
                                    break;
                                case 3:
                                    for (int i = 1; i <= currentCharacter.inventory.potions.size(); i++) {
                                        System.out.println(i + ". " + currentCharacter.inventory.potions.get(i - 1));
                                    }
                                    index = keyboard.nextInt();
                                    currentCharacter.inventory.usePotion(currentCharacter, currentCharacter.inventory.potions.get(index - 1));
                            }
                            System.out.println("Enemy Life: " + (Math.max(((Enemy) currentCell.cellElement).currentHealth, 0)));
                        } else {
                            System.out.println("\nEnemy attacks\n");
                            random = new Random();
                            if (random.nextBoolean()) {
                                int indexSpell = random.nextInt(((Enemy) currentCell.cellElement).spells.size());
                                ((Enemy) currentCell.cellElement).useSpell(((Enemy) currentCell.cellElement).spells.get(indexSpell), currentCharacter);
                            } else {
                                ((Enemy) currentCell.cellElement).getDamage(null, currentCharacter);
                            }
                            System.out.println("My Life: " + currentCharacter.currentHealth + "\n");
                        }
                        turn = !turn;
                    }
                    if (currentCharacter.currentHealth > 0) {
                        int experienceGained = 80;
                        if ((random.nextInt(5 - 1) + 1) != 1) {
                            System.out.println("You got money by defeating your enemy");
                            currentCharacter.inventory.addCash(80);
                        }
                        currentCharacter.exp += experienceGained;
                        if (currentCharacter.exp >= currentCharacter.upgradeExp) {
                            currentCharacter.levelUp(currentCharacter.exp - currentCharacter.upgradeExp);
                        }
                    } else {
                        System.out.println("Game over");
                    }
                }
        }
        currentCell.visited = true;
    }


    String displayStory (Cell currentCell, boolean gameMode) {
        if(!gameMode) {
            displayStoryCLI(currentCell);
            return null;
        } else {
            return displayStoryGUI(currentCell);
        }
    }

    private void displayStoryCLI(Cell currentCell) {
        Random random = new Random();
        if (!currentCell.visited && currentCell.cellType != CellType.START) {
            int index = random.nextInt(dictionary.get(currentCell.cellType).size() - 1);
            System.out.println("Story:" + dictionary.get(currentCell.cellType).get(index));
        }
    }

    private String displayStoryGUI(Cell currentCell) {
        Random random = new Random();
        if (!currentCell.visited && currentCell.cellType != CellType.START) {
            int index = random.nextInt(dictionary.get(currentCell.cellType).size() - 1);
            return dictionary.get(currentCell.cellType).get(index);
        }
        return null;
    }
}

class InvalidCommandException extends Exception{
    public InvalidCommandException() {
        super("Invalid Command");
    }
}