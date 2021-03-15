package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the index used for the edit command.
 */
public class IndexParser {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_TYPE = "Type is not a Person(P) or Event(E).";

    /**
     * Prases the index given to the edit command.
     * @param oneBasedIndex String index
     * @return Pair that is used in the Edit command
     * @throws ParseException if the index is not valid.
     */
    public static Pair parseEditIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        String type;
        String unparsedIndex;
        try {
            type = trimmedIndex.substring(0, 1);
            unparsedIndex = trimmedIndex.substring(1);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(unparsedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX + unparsedIndex);
        }

        if (!type.equalsIgnoreCase("E") && !type.equalsIgnoreCase("P")) {
            throw new ParseException(MESSAGE_INVALID_TYPE + type);
        }
        return new Pair(Index.fromOneBased(Integer.parseInt(unparsedIndex)), type);
    }
}
