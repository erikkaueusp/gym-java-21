package com.gymapp.gymapp.repository;

import com.gymapp.gymapp.domain.Assinatura;
import com.gymapp.gymapp.enumx.Periodicidade;
import com.gymapp.gymapp.model.outputs.AssinaturaVencimentoOutput;
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

    @Query("""
        SELECT new com.gymapp.gymapp.model.outputs.AssinaturaVencimentoOutput(
            al.nome,
            p.nome,
            a.dataFim,
            0L
        )
        FROM Assinatura a
        JOIN a.aluno al
        JOIN a.plano p
        WHERE a.ativa = true
          AND a.dataFim BETWEEN :inicio AND :fim
        ORDER BY a.dataFim ASC
        """)
    Page<AssinaturaVencimentoOutput> buscarProximosVencimentosAtivos(
            Pageable pageable,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );






}