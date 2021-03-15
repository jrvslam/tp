package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2030;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2107;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2107;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2107;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_CS2107;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_END_CS2107;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_START_CS2107;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditEventDescriptor descriptorWithSameValues = new EditEventDescriptor(DESC_CS2030);
        assertTrue(DESC_CS2030.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_CS2030.equals(DESC_CS2030));

        // null -> returns false
        assertFalse(DESC_CS2030.equals(null));

        // different types -> returns false
        assertFalse(DESC_CS2030.equals(5));

        // different values -> returns false
        assertFalse(DESC_CS2030.equals(DESC_CS2107));

        // different EventName -> returns false
        EditEventDescriptor editedCS2030 = new EditEventDescriptorBuilder(DESC_CS2030)
                .withEventName(VALID_NAME_CS2107).build();
        assertFalse(DESC_CS2030.equals(editedCS2030));

        // different Description -> returns false
        editedCS2030 = new EditEventDescriptorBuilder(DESC_CS2030).withDescription(VALID_DESCRIPTION_CS2107).build();
        assertFalse(DESC_CS2030.equals(editedCS2030));

        // different start time -> returns false
        editedCS2030 = new EditEventDescriptorBuilder(DESC_CS2030).withTimeStart(VALID_TIME_START_CS2107).build();
        assertFalse(DESC_CS2030.equals(editedCS2030));

        // different end time -> returns false
        editedCS2030 = new EditEventDescriptorBuilder(DESC_CS2030).withTimeEnd(VALID_TIME_END_CS2107).build();
        assertFalse(DESC_CS2030.equals(editedCS2030));

        // different Status -> returns false
        editedCS2030 = new EditEventDescriptorBuilder(DESC_CS2030).withStatus(VALID_STATUS_CS2107).build();
        assertFalse(DESC_CS2030.equals(editedCS2030));
    }
}
