package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code events}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getName());
        descriptor.setTimeStart(event.getTimeStart());
        descriptor.setTimeEnd(event.getTimeEnd());
        descriptor.setStatus(event.getStatus());
        descriptor.setDescription(event.getDescription());
        descriptor.setTags(event.getTags());
        descriptor.setPersons(event.getPersons());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String name) {
        descriptor.setEventName(new EventName(name));
        return this;
    }

    /**
     * Sets the {@code time start} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTimeStart(String eventTime) {
        descriptor.setTimeStart(new EventTime(eventTime));
        return this;
    }

    /**
     * Sets the {@code time end} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTimeEnd(String eventTime) {
        descriptor.setTimeEnd(new EventTime(eventTime));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }

}
