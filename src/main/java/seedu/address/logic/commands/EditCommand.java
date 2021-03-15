package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Pair;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventStatus;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.Description;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event book.";

    private final Pair pairedIndex;
    private final EditPersonDescriptor editPersonDescriptor;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param pairedIndex of the person/event in the filtered person/event list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Pair pairedIndex, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(pairedIndex);
        requireNonNull(editPersonDescriptor);

        this.pairedIndex = pairedIndex;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.editEventDescriptor = null;
    }

    /**
     *
     * @param pairedIndex of the person/event in the filtered person/event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditCommand(Pair pairedIndex, EditEventDescriptor editEventDescriptor) {
        requireNonNull(pairedIndex);
        requireNonNull(editEventDescriptor);

        this.pairedIndex = pairedIndex;
        this.editPersonDescriptor = null;
        this.editEventDescriptor = editEventDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (pairedIndex.isEditPerson()) {
            List<Person> lastShownList = model.getFilteredPersonList();

            if (pairedIndex.getIndex().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToEdit = lastShownList.get(pairedIndex.getIndex().getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

            if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
        } else {
            List<Event> lastShownList = model.getFilteredEventList();

            if (pairedIndex.getIndex().getZeroBased() >= lastShownList.size()) {
                //Done: change error message to Event Error
                throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            }

            Event eventToEdit = lastShownList.get(pairedIndex.getIndex().getZeroBased());
            Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

            if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
                //Done: change error message to duplicate event error
                throw new CommandException(MESSAGE_DUPLICATE_EVENT);
            }

            model.setEvent(eventToEdit, editedEvent);
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            //Done: change success message to event sucess
            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
        }

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        EventName updatedEventName = editEventDescriptor.getEventName().orElse(eventToEdit.getName());
        EventTime updatedTimeStart = editEventDescriptor.getTimeStart().orElse(eventToEdit.getTimeStart());
        EventTime updatedTimeEnd = editEventDescriptor.getTimeEnd().orElse(eventToEdit.getTimeEnd());
        EventStatus updatedStatus = editEventDescriptor.getStatus().orElse(eventToEdit.getStatus());
        Description updatedDesc = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        //Set<Person> updatedPersons = editEventDescriptor.getPersons().orElse(eventToEdit.getPersons());

        return new Event(updatedEventName, updatedTimeStart, updatedTimeEnd, updatedStatus, updatedDesc,
                updatedTags, eventToEdit.getPersons());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return pairedIndex.getIndex().equals(e.pairedIndex.getIndex())
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private EventName eventName;
        private EventTime timeStart;
        private EventTime timeEnd;
        private EventStatus status;

        private Description description;
        private Set<Tag> tags = new HashSet<>();
        private Set<Person> persons = new HashSet<>();

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventName(toCopy.eventName);
            setTimeStart(toCopy.timeStart);
            setTimeEnd(toCopy.timeEnd);
            setStatus(toCopy.status);
            setDescription(toCopy.description);
            setTags(toCopy.tags);
            setPersons(toCopy.persons);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, timeStart, timeEnd, status,
                    description, tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public Optional<EventName> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setEventName(EventName eventName) {
            this.eventName = eventName;
        }

        public Optional<EventTime> getTimeStart() {
            return Optional.ofNullable(timeStart);
        }

        public void setTimeStart(EventTime timeStart) {
            this.timeStart = timeStart;
        }

        public Optional<EventTime> getTimeEnd() {
            return Optional.ofNullable(timeEnd);
        }

        public void setTimeEnd(EventTime timeEnd) {
            this.timeEnd = timeEnd;
        }

        public Optional<EventStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setStatus(EventStatus status) {
            this.status = status;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public Optional<Set<Person>> getPersons() {
            return (persons != null) ? Optional.of(Collections.unmodifiableSet(persons)) : Optional.empty();
        }


        public void setPersons(Set<Person> persons) {
            this.persons = (tags != null) ? new HashSet<>(persons) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventName().equals(e.getEventName())
                    && getTimeStart().equals(e.getTimeStart())
                    && getTimeEnd().equals(e.getTimeEnd())
                    && getStatus().equals(e.getStatus())
                    && getDescription().equals(e.getDescription())
                    && getTags().equals(e.getTags());
        }
    }
}
