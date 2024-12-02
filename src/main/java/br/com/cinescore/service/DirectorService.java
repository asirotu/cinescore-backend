package br.com.cinescore.service;

import br.com.cinescore.dto.DirectorDto;
import br.com.cinescore.exception.ResourceNotFoundException;
import br.com.cinescore.mapper.CustomModelMapper;
import br.com.cinescore.model.DirectorModel;
import br.com.cinescore.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository repository;


    public DirectorDto create(DirectorDto directorDto) {
        DirectorModel directorModel = CustomModelMapper.parseObject(directorDto, DirectorModel.class);
        return CustomModelMapper.parseObject(repository.save(directorModel), DirectorDto.class);
    }

    public DirectorDto findById(Long id) {
        DirectorModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Diretor não encontrado com o ID: " + id));
        return CustomModelMapper.parseObject(found, DirectorDto.class);
    }

    public DirectorDto update(DirectorDto directorDto) {
        DirectorModel found = repository.findById(directorDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Diretor não encontrado com o ID: " + directorDto.getId()));

        found.setName(directorDto.getName());
        found.setBio(directorDto.getBio());

        return CustomModelMapper.parseObject(repository.save(found), DirectorDto.class);
    }

    public void delete(Long id) {
        DirectorModel found = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Diretor não encontrado com o ID: " + id));
        repository.delete(found);
    }

    public Page<DirectorDto> findAll(Pageable pageable) {
        var directors = repository.findAll(pageable);
        return directors.map(director -> CustomModelMapper.parseObject(director, DirectorDto.class));
    }
}
