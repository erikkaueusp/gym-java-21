package com.gymapp.gymapp;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGeneratorTest {

    @Test
    public void testGenerateHashFor123456() {
        // Define o fator de custo (12 nesse exemplo)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        // Senha a ser hasheada
        String senha = "waap654321";

        // Gera o hash da senha
        String hash = encoder.encode(senha);

        // Imprime o hash no console
        System.out.println("Hash para "+ senha + ": " + hash);
    }
}
