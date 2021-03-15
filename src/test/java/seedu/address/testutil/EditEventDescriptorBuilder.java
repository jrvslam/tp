package seedu.address.testutil;

import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.model.event.*;

public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

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

    public EditEventDescriptorBuilder withEventName(String name) {
        descriptor.setEventName(new EventName(name));
        return this;
    }

    public EditEventDescriptorBuilder withTimeStart(String eventTime) {
        descriptor.setTimeStart(new EventTime(eventTime));
        return this;
    }

    public EditEventDescriptorBuilder withTimeEnd(String eventTime) {
        descriptor.setTimeEnd(new EventTime(eventTime));
        return this;
    }

    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }

}
