package com.felix.os.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felix.os.domain.Cliente;
import com.felix.os.domain.OS;
import com.felix.os.domain.Tecnico;
import com.felix.os.dtos.OSDTO;
import com.felix.os.enums.Prioridade;
import com.felix.os.enums.Status;
import com.felix.os.exception.ObjectNotFoundException;
import com.felix.os.repositories.OsRepository;

@Service
public class OsService {

	@Autowired
	private OsRepository osRepository;
	
	@Autowired
	private TecnicoService tecnicoService; 
	
	@Autowired
	private ClienteService clienteService; 
	
	
	public OS findById(Integer id) {
		Optional<OS> obj = osRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!" + id + ", Tipo: " + OS.class.getName()));
	}
	
	public List<OS> findAll(){
		return osRepository.findAll();
	}
	
	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	
	private OS fromDTO(OSDTO obj) {
	 OS newObj = new OS();
	 newObj.setId(obj.getId());
	 newObj.setObservacoes(obj.getObservacoes());
	 newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade().getCod()));
	 newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
	 
	 Tecnico tec =  tecnicoService.findById(obj.getTecnico());
	 Cliente cli =  clienteService.findById(obj.getCliente());
	 
	 newObj.setTecnico(tec);
	 newObj.setCliente(cli);
	 
	 if(newObj.getStatus().getCod().equals(2)) {
		 newObj.setDataFechamento(LocalDateTime.now());
	 }
	 
	 return osRepository.save(newObj);
	}

	
}
