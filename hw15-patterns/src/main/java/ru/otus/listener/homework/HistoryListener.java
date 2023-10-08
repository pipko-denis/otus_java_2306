package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long,Message> messagesMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        messagesMap.put(msg.getId(),msg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messagesMap.get(id));
    }
}
