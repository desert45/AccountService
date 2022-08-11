package com.bootcamp.account.Model.Events;

import lombok.Data;

import java.util.Date;

@Data
public abstract class Event <T> {
    private String id;
    private Date date;
    private EventType type;
    private T data;
    private String idCustomer;
}
