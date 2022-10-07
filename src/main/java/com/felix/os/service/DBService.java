package com.felix.os.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felix.os.domain.Cliente;
import com.felix.os.domain.OS;
import com.felix.os.domain.Tecnico;
import com.felix.os.enums.Prioridade;
import com.felix.os.enums.Status;
import com.felix.os.repositories.ClienteRepository;
import com.felix.os.repositories.OsRepository;
import com.felix.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private OsRepository osRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	
	public void instanciaDB() {
       
		Tecnico t1 = new Tecnico(null, "Linus Santos", "505.967.386-85,", "(11) 92234-6489");
		Tecnico t2 = new Tecnico(null, " Valdir Cezar", "305.667.586-80,", "(11) 91234-5489");
		Cliente c1 = new Cliente(null, "betina campos", "305.867.586-98", "(11)91234-5489");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create os", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);
		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));

	}
}
