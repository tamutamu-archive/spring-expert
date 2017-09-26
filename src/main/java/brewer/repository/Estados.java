package brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brewer.model.Estado;

@Repository
public interface Estados extends JpaRepository<Estado, Long> {

	public Optional<Estados> findByNomeIgnoreCase(String nome);
	
}
