package com.bootcamp.account.Model.document;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class TypeAccount {

	private String id;
	
	private String description;
	 
	private Integer commission;
	 
	private Integer maximumLimit;
}
