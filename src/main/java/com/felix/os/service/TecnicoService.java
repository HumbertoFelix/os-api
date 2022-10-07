package com.felix.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felix.os.domain.Pessoa;
import com.felix.os.domain.Tecnico;
import com.felix.os.dtos.TecnicoDTO;
import com.felix.os.exception.DataIntegratyViolationException;
import com.felix.os.exception.ObjectNotFoundException;
import com.felix.os.repositories.PessoaRepository;
import com.felix.os.repositories.TecnicoRepository;



@Service
public class TecnicoService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objet não encontrado!" + id + ",Tipo:" + Tecnico.class.getName()));
		
	}

	public List<Tecnico> findAll() {
		
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return tecnicoRepository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
		//Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldobj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldobj.setNome(objDTO.getNome());
		oldobj.setCpf(objDTO.getCpf());
		oldobj.setTelefone(objDTO.getTelefone());
		
		return tecnicoRepository.save(oldobj);
		
	 }
	
     public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("O técnico possui ordem deServiço e não pode ser deletado!");
		}
        tecnicoRepository.deleteById(id);
		
	 }
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
		}

	

	
	
}
