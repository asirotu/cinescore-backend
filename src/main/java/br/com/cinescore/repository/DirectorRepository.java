package br.com.cinescore.repository;

import br.com.cinescore.model.DirectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<DirectorModel, Long> {
}
