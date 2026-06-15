package com.projeto.copa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.copa.entities.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
    Time findByNome(String nome);
}
