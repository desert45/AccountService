package com.bootcamp.account.Util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bootcamp.account.Model.document.Account;
import com.bootcamp.account.Model.document.TypeAccount;
import lombok.extern.slf4j.*;
import com.bootcamp.account.Util.*;

@Slf4j
@Service
public class AccountValidations {

	List<TypeAccount> listTypeAccountsFinal = new ArrayList<>();

	public List<TypeAccount> comunicationWebClientObtenerTypeBankAccountsMock() {

		List<TypeAccount> typeAccounts = new ArrayList<>();

		typeAccounts.add(new TypeAccount("1", "Ahorro", 0, 3));
		typeAccounts.add(new TypeAccount("2", "Cuenta Corriente", 10, 0));
		typeAccounts.add(new TypeAccount("3", "Plazo Fijo", 0, 0));
		return typeAccounts;

	}

	public List<TypeAccount> validacionTypeBankAccounts(List<TypeAccount> typeBankAccounts, Account account) {

		if (!listTypeAccountsFinal.isEmpty()) {
			listTypeAccountsFinal.remove(listTypeAccountsFinal.size() - 1);
		}

		for (int i = 0; i < typeBankAccounts.size(); i++) {
			String str = typeBankAccounts.get(i).getId().toString();

			log.info("Ver Codigo de Total de Tipo Cuentas ---> " + str);

			for (int a = 0; a < account.getType().size(); a++) {

				if (str.equals(account.getType().get(a).getId().toString())) {

					log.info("Ver Codigo de Request Bank --- > " + account.getType().get(a).getId().toString());

					account.getType().get(a).setId(typeBankAccounts.get(i).getId());
					account.getType().get(a).setDescription(typeBankAccounts.get(i).getDescription());
					account.getType().get(a).setCommission(typeBankAccounts.get(i).getCommission());
					account.getType().get(a).setMaximumLimit(typeBankAccounts.get(i).getMaximumLimit());

					listTypeAccountsFinal.add(account.getType().get(a));
				}
			}
		}

		log.info("Obtener List Final -->" + listTypeAccountsFinal);

		account.setType(listTypeAccountsFinal);

		return listTypeAccountsFinal;
	}

	public Boolean ValidationAccountCustomer(List<Account> accountRegistred, Account account) {

		for (int i = 0; i < accountRegistred.size(); i++) {

			String doc = accountRegistred.get(i).getDocumentNumber().toString();

			log.info("Ver Tipo Cuenta ---> " + doc);

			for (int a = 0; a < account.getType().size(); a++) {

				if (accountRegistred.get(i).getType().get(0).getId().toString()
						.equals(account.getType().get(a).getId().toString())) {

					log.info("Ver Tipo de Cuenta existente --- > " + account.getType().get(a).getId().toString());
					return true;
				}
			}
		}

		log.info("Creando cuenta de tipo -->" + account.getType().get(0).getId().toString());

		//account.setType(listTypeAccountsFinal);

		return false;
	}
}
