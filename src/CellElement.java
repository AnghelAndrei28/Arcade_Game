public interface CellElement {
    String toCharacter();
}

class Start implements CellElement {

    @Override
    public String toCharacter() {
        return "P ";
    }
}

class Finish implements CellElement {

    @Override
    public String toCharacter() {
        return "F ";
    }
}

class Empty implements CellElement {

    @Override
    public String toCharacter() {
        return "N ";
    }
}
