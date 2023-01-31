package com.api.ponline.dao.Response;

import java.util.ArrayList;
import java.util.List;

public class AbstractResponse<T> {

    private Boolean success;
    private List<String> messages = new ArrayList<>();
    private T payLoad;
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public List<String> getMessages() {
        return messages;
    }
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    public T getPayLoad() {
        return payLoad;
    }
    public void setPayLoad(T payLoad) {
        this.payLoad = payLoad;
    }

    
    
    
    
}
