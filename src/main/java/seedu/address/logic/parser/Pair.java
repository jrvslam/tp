package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;

/**
 * Pair of Index and Type that is used when parsing the Edit command. Command original String is "E2" so Pair stores
 * the index and whether it is a Person or an Edit.
 */
public class Pair {
    private final Index index;
    private final String type;

    /**
     * Pair constructor for edit command
     */
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
