package com.bootcamp.account.Model.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.account.Model.document.TypeAccount;


@Repository
public interface TypeAccountRepository extends ReactiveMongoRepository<TypeAccount, String>  {

}
