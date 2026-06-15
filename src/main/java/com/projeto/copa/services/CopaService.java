package com.projeto.copa.services;

import com.projeto.copa.dtos.CopaResponse;
import com.projeto.copa.dtos.IniciarCopaRequest;
import com.projeto.copa.dtos.PartidaResponse;
import com.projeto.copa.dtos.SalvarPlacarRequest;
import com.projeto.copa.entities.Copa;
import com.projeto.copa.entities.Partida;
import com.projeto.copa.entities.Time;
import com.projeto.copa.enums.FaseCopa;
import com.projeto.copa.enums.StatusCopa;
import com.projeto.copa.mappers.CopaMapper;
import com.projeto.copa.mappers.PartidaMapper;
import com.projeto.copa.repositories.CopaRepository;
import com.projeto.copa.repositories.PartidaRepository;
import com.projeto.copa.repositories.TimeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class CopaService {

    private final CopaRepository copaRepository;
    private final TimeRepository timeRepository;
    private final PartidaRepository partidaRepository;

    // Injeção de dependências pelo construtor
    public CopaService(CopaRepository copaRepository,
            TimeRepository timeRepository,
            PartidaRepository partidaRepository) {
        this.copaRepository = copaRepository;
        this.timeRepository = timeRepository;
        this.partidaRepository = partidaRepository;
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

    public PartidaResponse salvarPlacar(Long partidaId, SalvarPlacarRequest request) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada!"));

        // 1. Atualiza o placar do jogador
        partida.setPlacarCasa(request.getPlacarCasa());
        partida.setPlacarFora(request.getPlacarFora());
        partida.setConcluida(true);
        partida.setVencedorId(request.getPlacarCasa() > request.getPlacarFora()
                ? partida.getTimeCasa().getId()
                : partida.getTimeFora().getId());
        partidaRepository.save(partida);

        // Simular todas as outras partidas pendentes da fase atual
        Copa copa = partida.getCopa();
        for (Partida p : copa.getPartidas()) {
            if (p.getFase() == copa.getFaseAtual() && !p.getConcluida()) {
                // Se não é o jogo do jogador simula
                simularPlacar(p, false);
                partidaRepository.save(p);
            }
        }

        // Agora que todos foram concluídos, tenta avançar a fase
        avancarFaseSePossivel(copa.getId());

        return PartidaMapper.toResponse(partida);
    }

    private void simularPlacar(Partida partida, boolean ehJogoDoJogador) {
        Random random = new Random();
        int golsCasa, golsFora;

        do {
            if (ehJogoDoJogador) {
                golsCasa = random.nextInt(4);
                golsFora = random.nextInt(4);
            } else {
                golsCasa = random.nextInt(5);
                golsFora = random.nextInt(5);
            }
        } while (golsCasa == golsFora);

        partida.setPlacarCasa(golsCasa);
        partida.setPlacarFora(golsFora);
        partida.setConcluida(true);
        partida.setVencedorId(golsCasa > golsFora ? partida.getTimeCasa().getId() : partida.getTimeFora().getId());
    }

    public void avancarFaseSePossivel(Long copaId) {
        Copa copa = copaRepository.findById(copaId).orElseThrow();

        // Verifica se a fase atual acabou
        boolean faseAcabou = copa.getPartidas().stream()
                .filter(p -> p.getFase() == copa.getFaseAtual() && !p.getConcluida())
                .findFirst().isEmpty();

        if (faseAcabou) {
            List<Partida> partidasFaseAtual = copa.getPartidas().stream()
                    .filter(p -> p.getFase() == copa.getFaseAtual())
                    .toList();

            List<Time> vencedores = new ArrayList<>();
            for (Partida p : partidasFaseAtual) {
                Time vencedor = timeRepository.findById(p.getVencedorId()).orElseThrow();
                vencedores.add(vencedor);
            }

            FaseCopa proximaFase = definirProximaFase(copa.getFaseAtual());

            if (proximaFase != null) {
                // Cria as novas partidas e simula as que não são do jogador
                for (int i = 0; i < vencedores.size(); i += 2) {
                    Partida novaPartida = new Partida();
                    novaPartida.setCopa(copa);
                    novaPartida.setFase(proximaFase);
                    novaPartida.setTimeCasa(vencedores.get(i));
                    novaPartida.setTimeFora(vencedores.get(i + 1));
                    novaPartida.setConcluida(false);

                    boolean ehJogoDoJogador = novaPartida.getTimeCasa().getId().equals(copa.getTimeDoJogador().getId())
                            ||
                            novaPartida.getTimeFora().getId().equals(copa.getTimeDoJogador().getId());

                    if (!ehJogoDoJogador) {
                        simularPlacar(novaPartida, false);
                    }
                    copa.getPartidas().add(novaPartida);
                }
                copa.setFaseAtual(proximaFase);
                copaRepository.save(copa);
            } else {
                copa.setStatus(StatusCopa.CAMPEAO);
                copaRepository.save(copa);
            }
        }
    }

    // Método auxiliar apontando para as próximas fases
    private FaseCopa definirProximaFase(FaseCopa atual) {
        return switch (atual) {
            case OITAVAS -> FaseCopa.QUARTAS;
            case QUARTAS -> FaseCopa.SEMI;
            case SEMI -> FaseCopa.FINAL;
            case FINAL -> null;
        };
    }
}