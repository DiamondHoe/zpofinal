package com.example.zpofinal.repositories;

import com.example.zpofinal.models.Wine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends CrudRepository<Wine, Long> {
}
