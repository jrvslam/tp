package seedu.address.model.event;

public enum EventStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public static final String MESSAGE_CONSTRAINTS = "Event status should be TODO, IN_PROGRESS, "
            + "or DONE";
}
