package com.labsit.contacorrente.repository;

import com.labsit.contacorrente.model.ContaCorrente;
import com.labsit.contacorrente.model.pk.ContaCorrentePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, ContaCorrentePK> {

    Optional<ContaCorrente> findContaCorrenteByNrAgAndNrCta(String nrAg, String nrCta);
}
