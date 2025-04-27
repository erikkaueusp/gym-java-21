package com.gymapp.gymapp.service.assinatura;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.domain.Assinatura;
import com.gymapp.gymapp.domain.Plano;
import com.gymapp.gymapp.enumx.Periodicidade;
import com.gymapp.gymapp.model.inputs.AssinaturaFilter;
import com.gymapp.gymapp.model.outputs.TotaisFinanceirosOutput;
import com.gymapp.gymapp.repository.AlunoRepository;
import com.gymapp.gymapp.repository.AssinaturaRepository;
import com.gymapp.gymapp.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gymapp.gymapp.specification.AssinaturaSpecifications.comFiltro;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository repository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Transactional
    public Assinatura criarAssinatura(Long alunoId, Long planoId){
        LocalDate inicio = LocalDate.now();
        Aluno a = alunoRepository.findById(alunoId).orElseThrow();
        Plano p = planoRepository.findById(planoId).orElseThrow();

        LocalDate fim = p.getPeriodicidade().calcularFim(inicio);
        Assinatura s = new Assinatura(a, p, inicio, fim, true);
        return repository.save(s);
    }

    @Scheduled(cron = "0 0 0,12 * * *") // Executa todos os dias às 00:00 e às 12:00
    @Transactional
    public void expirarAssinaturas(){
        List<Assinatura> exp = repository.findByAtivaTrueAndDataFimBefore(LocalDate.now());
        exp.forEach(s -> s.setAtiva(false));
    }

    public BigDecimal totalPorPeriodicidade(Periodicidade periodicidade, LocalDate inicio, LocalDate fim) {
        return Optional.ofNullable(
                repository.somarReceitaPorPeriodicidadeNoPeriodo(periodicidade, inicio, fim)
        ).orElse(BigDecimal.ZERO);
    }

    public TotaisFinanceirosOutput calcularTotaisFinanceiros(LocalDate inicio, LocalDate fim) {
        Map<Periodicidade, BigDecimal> totais = Map.of(
                Periodicidade.DAILY,   totalPorPeriodicidade(Periodicidade.DAILY, inicio, fim),
                Periodicidade.MONTHLY, totalPorPeriodicidade(Periodicidade.MONTHLY, inicio, fim),
                Periodicidade.ANNUAL,  totalPorPeriodicidade(Periodicidade.ANNUAL, inicio, fim)
        );

        BigDecimal totalGeral = totais.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TotaisFinanceirosOutput(inicio, fim, totais, totalGeral);
    }

    public Page<Assinatura> listarPorFiltro(AssinaturaFilter filter, Pageable pageable) {
        return repository.findAll(comFiltro(filter), pageable);
    }

}
