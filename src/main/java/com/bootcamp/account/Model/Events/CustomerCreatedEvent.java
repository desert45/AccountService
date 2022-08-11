package com.bootcamp.account.Model.Events;


import com.bootcamp.account.Model.document.Account;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerCreatedEvent extends Event<Account> {

}
