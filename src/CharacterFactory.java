public class CharacterFactory {

    public Character getCharacter(String characterType, String characterName, int exp, int level) {
        if(characterType == null) {
            return null;
        }
        if(characterType.equalsIgnoreCase("Warrior")){
            return new Warrior(characterName, exp, level);

        } else if(characterType.equalsIgnoreCase("Mage")){
            return new Mage(characterName, exp, level);

        } else if(characterType.equalsIgnoreCase("Rogue")){
            return new Rogue(characterName, exp, level);
        }
        return null;
    }
}
