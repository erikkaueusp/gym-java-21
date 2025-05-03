package com.gymapp.gymapp.service;

import com.gymapp.gymapp.service.aluno.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class BiometriaWebSocketService extends TextWebSocketHandler {

    @Autowired
    private AlunoService alunoService;

    // Lista para armazenar todas as conexões abertas
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


    public Long registraBiometriaAluno(Long idAluno) {
        // 1. Valida se o aluno existe
        alunoService.findById(idAluno);
        // 2. Envia comando para o Middleware via WebSocket
        String comando = "cadastrar-digital:" + idAluno;
        this.enviarMensagemParaTodos(comando);
        return idAluno;
    }

    public Long cadastrarComInterrupcaoModoScan(Long idAluno) {
        alunoService.findById(idAluno);

        // 1. Desativa modo-scan
        this.enviarMensagemParaTodos("modo-scan:desativar");

        // 2. Aguarda um pouco para o middleware parar o loop
        try {
            Thread.sleep(1000); // 1 segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 3. Envia comando de cadastro
        this.enviarMensagemParaTodos("cadastrar-digital:" + idAluno);
        return idAluno;
    }

    public void ativarModoScan() {
        this.enviarMensagemParaTodos("modo-scan:ativar");
    }

    public void desativarModoScan() {
        this.enviarMensagemParaTodos("modo-scan:desativar");
    }

    public void destravar() {
        this.enviarMensagemParaTodos("destravar");
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Conexão WebSocket estabelecida: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("Mensagem recebida: " + payload);
        // Verifica se é confirmação da biometria
        if (payload.startsWith("digital-cadastrada:")) {
            Long idAluno = Long.parseLong(payload.split(":")[1]);
            alunoService.registraBiometriaAlunoById(idAluno);
            System.out.println("Aluno " + idAluno + " atualizado como biometriaCadastrada=true");
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Conexão WebSocket fechada: " + session.getId());
    }

    // Método para enviar mensagens para todos os middlewares conectados
    public void enviarMensagemParaTodos(String mensagem) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(mensagem));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
