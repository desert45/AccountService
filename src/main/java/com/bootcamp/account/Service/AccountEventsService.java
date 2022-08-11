package com.bootcamp.account.Service;

import com.bootcamp.account.Model.Events.CustomerCreatedEvent;
import com.bootcamp.account.Model.Events.CustomerDeletedEvent;
import com.bootcamp.account.Model.Events.Event;
import com.bootcamp.account.Model.Events.EventType;
import com.bootcamp.account.Model.document.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



import java.util.Date;
import java.util.UUID;

@Component
public class AccountEventsService {
	
	@Autowired
	private KafkaTemplate<String, Event<?>> producer;
	
	@Value("${topic.account.name:accounts}")
	private String topicCustomer;
	
	public void created(Account account) {

		CustomerCreatedEvent created = new CustomerCreatedEvent();
		created.setData(account);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.CREATED);
		created.setDate(new Date());

		this.producer.send(topicCustomer, created);
	}
	
	public void deleted(String id) {

		CustomerDeletedEvent created = new CustomerDeletedEvent();
		created.setIdCustomer(id);
		created.setId(UUID.randomUUID().toString());
		created.setType(EventType.DELETED);
		created.setDate(new Date());

		this.producer.send(topicCustomer, created);
	}
	

}
