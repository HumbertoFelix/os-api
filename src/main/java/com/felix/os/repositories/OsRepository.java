package com.felix.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felix.os.domain.OS;

@Repository
public interface OsRepository extends JpaRepository<OS, Integer>{

}
