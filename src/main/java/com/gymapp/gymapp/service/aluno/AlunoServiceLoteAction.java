package com.gymapp.gymapp.service.aluno;

import com.gymapp.gymapp.model.inputs.AlunotDtoInput;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoServiceLoteAction {

    public List<AlunotDtoInput> lerArquivoExcel(MultipartFile file) throws IOException {
        List<AlunotDtoInput> alunos = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;  // Ignorar o cabeçalho
                }
                String nome = getCellValueSafe(row, 0);
                String email = getCellValueSafe(row, 1);
                String endereco = getCellValueSafe(row, 2);
                String telefone = getCellValueSafe(row, 3);
                String img = getCellValueSafe(row, 4);

                AlunotDtoInput aluno = new AlunotDtoInput(nome, email, endereco, telefone, img);
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    private String getCellValueSafe(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell == null) ? "" : cell.getStringCellValue();
    }

}
