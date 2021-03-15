package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;

public class Pair {
    private Index index;
    private String type;

    public Pair(Index index, String type) {
        this.index = index;
        this.type = type;
    }

    public Index getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    public boolean isEditPerson() {
        return type.equals("P");
    }

}
