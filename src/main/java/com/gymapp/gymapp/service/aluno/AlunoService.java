package com.gymapp.gymapp.service.aluno;

import com.gymapp.gymapp.domain.Aluno;
import com.gymapp.gymapp.mappers.AlunoMapper;
import com.gymapp.gymapp.model.inputs.AlunotDtoInput;
import com.gymapp.gymapp.model.outputs.AlunoDtoOutput;
import com.gymapp.gymapp.repository.AlunoRepository;
import com.gymapp.gymapp.utils.FileUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    private AlunoServiceLoteAction alunoServiceLoteAction;
    @Autowired
    private AlunoRepository repository;

    @Autowired
    private AlunoMapper mapper;

    @Autowired
    private FileUtils fileUtils;

    public AlunoDtoOutput findById(Long id) {
        Optional<Aluno> exercicio = repository.findById(id);
        return mapper.toDto(exercicio.orElseThrow(EntityNotFoundException::new));
    }

    public Page<AlunoDtoOutput> findAll(Pageable pageable) {
        Page<Aluno> alunos = repository.findAll(pageable);
        return alunos.map(mapper::toDto);
    }

    public Long save(AlunotDtoInput input) throws IOException {
        Aluno aluno = mapper.toEntity(input);
        if (input.img() != null && !input.img().isEmpty()) {
            String path = fileUtils.saveImage(input.img());
            aluno.setImg(path);
        }
        Aluno save = repository.save(aluno);
        return save.getId();
    }

    public void saveLote(MultipartFile file) throws IOException {
        List<AlunotDtoInput> alunos = alunoServiceLoteAction.lerArquivoExcel(file);
        alunos.forEach(input -> {
            try {
                save(input);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
