package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Assinatura;
import com.gymapp.gymapp.enumx.Periodicidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long>, JpaSpecificationExecutor<Assinatura> {


    @Query("""
    SELECT SUM(p.preco)
    FROM Assinatura s JOIN s.plano p
    WHERE p.periodicidade = :per
      AND s.dataInicio BETWEEN :inicio AND :fim
""")
    BigDecimal somarReceitaPorPeriodicidadeNoPeriodo(Periodicidade per, LocalDate inicio, LocalDate fim);

    List<Assinatura> findByAtivaTrueAndDataFimBefore(LocalDate data);

    @Query("""
               SELECT s
               FROM Assinatura s
               WHERE s.ativa = true
                 AND :ref BETWEEN s.dataInicio AND s.dataFim
            """)
    Page<Assinatura> findAtivasEm(@Param("ref") LocalDate ref, Pageable pageable);


}