package request.action;

import request.MessageRequest;

@FunctionalInterface
public interface Action {

    void apply(MessageRequest message);
}
