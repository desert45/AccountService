package com.bootcamp.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@ToString
public class customerDto {

  private String documentNumber;
  
  private String documentType;
  
  private String firstName;
  
  private String lastName;
  
  private String createAt;
  
  private TypeCustomerDTO typeCustomers; 

}
