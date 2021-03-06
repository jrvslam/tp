package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTART;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_TIMESTART, PREFIX_TIMEEND, PREFIX_STATUS, PREFIX_DESC, PREFIX_PERSONS);

        Index index;
        Pair pairedIndex;

        try {
            pairedIndex = IndexParser.parseEditIndex(argMultimap.getPreamble());
            //old: index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor;
        EditEventDescriptor editEventDescriptor;

        EditCommand editCommand;

        if (pairedIndex.isEditPerson()) {
            editPersonDescriptor = new EditPersonDescriptor();
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
                editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            editCommand = new EditCommand(pairedIndex, editPersonDescriptor);
        } else {
            editEventDescriptor = new EditEventDescriptor();
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_TIMESTART).isPresent()) {
                editEventDescriptor.setTimeStart(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIMESTART).get()));
            }
            if (argMultimap.getValue(PREFIX_TIMEEND).isPresent()) {
                editEventDescriptor.setTimeEnd(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIMEEND).get()));
            }
            if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
                editEventDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
            }
            if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
                editEventDescriptor.setDescription(ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC).get()));
            }
            parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEventDescriptor::setTags);

            if (!editEventDescriptor.isAnyFieldEdited()) {
                throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
            }
            editCommand = new EditCommand(pairedIndex, editEventDescriptor);
        }

        return editCommand;
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
