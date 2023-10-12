package com.gymapp.gymapp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUtils {

    // TODO Futuramente pretendo salvar em outro caminho e utilizar docker para criar um volume para que salve as imagens em máquina local

    @Value("${image.upload.dir}")
    private String imageUploadDir;

    public String saveImage(String base64Image) throws IOException {
        // Removendo o prefixo da string base64
        String base64ImageCleaned = base64Image.split(",")[1];

        // Decodificando a string base64 para um array de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageCleaned);

        // Criando um nome de arquivo único
        String fileName = UUID.randomUUID() + ".jpg";
        Path filePath = Paths.get(imageUploadDir, fileName);

        // Salvando o arquivo no diretório de upload
        Files.write(filePath, imageBytes);

        return filePath.toString();
    }
}

