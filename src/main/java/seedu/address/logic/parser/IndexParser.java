package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class IndexParser {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static Pair parseEditIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        String type = trimmedIndex.substring(0, 1);
        String unparsedIndex = trimmedIndex.substring(1);
        if (!StringUtil.isNonZeroUnsignedInteger(unparsedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        //TODO check type is valid
        return new Pair(Index.fromOneBased(Integer.parseInt(unparsedIndex)), type);
    }
}
