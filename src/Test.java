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

    public static void main(String[] args){

        System.out.println("We are ready to go! Please choose GUI or CLI");
        Scanner keyboard = new Scanner(System.in);
        String gameMode = keyboard.nextLine();
        Game game = Game.getInstance();
        game.run(gameMode);
    }
}
