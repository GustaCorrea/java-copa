package com.projeto.copa.services;

import com.projeto.copa.dtos.CopaResponse;
import com.projeto.copa.dtos.IniciarCopaRequest;
import com.projeto.copa.entities.Copa;
import com.projeto.copa.entities.Partida;
import com.projeto.copa.entities.Time;
import com.projeto.copa.enums.FaseCopa;
import com.projeto.copa.enums.StatusCopa;
import com.projeto.copa.mappers.CopaMapper;
import com.projeto.copa.repositories.CopaRepository;
import com.projeto.copa.repositories.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CopaService {

    private final CopaRepository copaRepository;
    private final TimeRepository timeRepository;

    // Injeção de dependências pelo construtor
    public CopaService(CopaRepository copaRepository, TimeRepository timeRepository) {
        this.copaRepository = copaRepository;
        this.timeRepository = timeRepository;
    }

    public CopaResponse iniciarCopa(IniciarCopaRequest request) {
        // equipe escolhida
        Time timeJogador = timeRepository.findById(request.getIdTimeEscolhido())
                .orElseThrow(() -> new RuntimeException("Equipa não encontrada no sistema!"));

        // Busca todas as equipes e remove a do jogador da lista
        List<Time> todosOsTimes = timeRepository.findAll();
        todosOsTimes.removeIf(t -> t.getId().equals(timeJogador.getId()));

        // Embaralha os 46 times e escolhe 15
        Collections.shuffle(todosOsTimes);
        List<Time> timesSorteados = new ArrayList<>();

        // Adiciona equipe do jogador
        timesSorteados.add(timeJogador);

        for (int i = 0; i < 15; i++) {
            timesSorteados.add(todosOsTimes.get(i));
        }

        // Embaralha os 16 finalistas para que os confrontos sejam aleatórios
        Collections.shuffle(timesSorteados);

        // Cria a entidade Copa
        Copa novaCopa = new Copa();
        novaCopa.setTimeDoJogador(timeJogador);
        novaCopa.setFaseAtual(FaseCopa.OITAVAS);
        novaCopa.setStatus(StatusCopa.EM_ANDAMENTO);

        List<Partida> partidas = new ArrayList<>();

        // Montar os confrontos das Oitavas de Final
        // O loop salta de 2 em 2
        for (int i = 0; i < 16; i += 2) {
            Partida partida = new Partida();
            partida.setCopa(novaCopa);
            partida.setFase(FaseCopa.OITAVAS);
            partida.setTimeCasa(timesSorteados.get(i));
            partida.setTimeFora(timesSorteados.get(i + 1));
            partida.setConcluida(false);

            partidas.add(partida);
        }

        // Associa a lista de partidas à copa
        novaCopa.setPartidas(partidas);

        // 7. Guardar tudo na base de dados
        Copa copaSalva = copaRepository.save(novaCopa);

        // 8. Traduzir o resultado final para o Angular
        return CopaMapper.toResponse(copaSalva);
    }

    public CopaResponse findById(Long id) {
        Copa copa = copaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Copa não encontrada com o ID: " + id));
        return CopaMapper.toResponse(copa);
    }

    // Método delete
    public void deleteCopa(Long id) {
        copaRepository.deleteById(id);
    }

    // Método put(trocar time)
    public CopaResponse trocarTime(Long copaId, Long novoTimeId) {
        Copa copa = copaRepository.findById(copaId)
                .orElseThrow(() -> new RuntimeException("Copa não encontrada!"));

        Time novoTime = timeRepository.findById(novoTimeId)
                .orElseThrow(() -> new RuntimeException("Time não encontrado!"));

        copa.setTimeDoJogador(novoTime);
        Copa copaAtualizada = copaRepository.save(copa);

        return CopaMapper.toResponse(copaAtualizada);
    }
}