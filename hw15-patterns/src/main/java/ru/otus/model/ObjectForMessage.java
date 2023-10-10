package ru.otus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


    public ObjectForMessageBuilder toBuilder() {
        return new ObjectForMessageBuilder(Optional.ofNullable(this.getData())
                .map(list -> new ArrayList<>(list))
                .orElse(null)
        );
    }

    static class ObjectForMessageBuilder {

        private List<String> data;

        public ObjectForMessageBuilder withData(List<String> data) {
            this.data = data;
            return this;
        }

        public ObjectForMessageBuilder(List<String> data) {
            this.data = data;
        }

        public ObjectForMessage build() {
            ObjectForMessage objectForMessage = new ObjectForMessage();
            objectForMessage.setData(this.data);
            return objectForMessage;
        }

    }
}
