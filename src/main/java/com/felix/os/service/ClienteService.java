package com.felix.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felix.os.domain.Pessoa;
import com.felix.os.domain.Cliente;
import com.felix.os.dtos.ClienteDTO;
import com.felix.os.exception.DataIntegratyViolationException;
import com.felix.os.exception.ObjectNotFoundException;
import com.felix.os.repositories.PessoaRepository;
import com.felix.os.repositories.ClienteRepository;



@Service
public class ClienteService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente findById(Integer id) {
		
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objet não encontrado!" + id + ",Tipo:" + Cliente.class.getName()));
		
	}

	public List<Cliente> findAll() {
		
		return clienteRepository.findAll();
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		return clienteRepository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
		//Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldobj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldobj.setNome(objDTO.getNome());
		oldobj.setCpf(objDTO.getCpf());
		oldobj.setTelefone(objDTO.getTelefone());
		
		return clienteRepository.save(oldobj);
		
	 }
	
     public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("O cliente possui ordem deServiço e não pode ser deletado!");
		}
        clienteRepository.deleteById(id);
		
	 }
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
		}

	

	
	
}
