package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    /* Added prefixes that relate to Events */
    public static final Prefix PREFIX_TIMESTART = new Prefix("ts/");
    public static final Prefix PREFIX_TIMEEND = new Prefix("te/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_DESC = new Prefix("d/");
    public static final Prefix PREFIX_PERSONS = new Prefix("ps/");

}
