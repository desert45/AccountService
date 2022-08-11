package com.bootcamp.account.Model.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "accounts")  
public class Account {  

  @Id 
  private String id;
  
  private String documentNumber;
  
  private String numberAccount;
  
  private List<TypeAccount> type;

}
