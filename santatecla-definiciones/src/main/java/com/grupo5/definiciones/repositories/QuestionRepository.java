package com.grupo5.definiciones.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo5.definiciones.model.Concept;
import com.grupo5.definiciones.model.Question;
import com.grupo5.definiciones.model.User;

public interface QuestionRepository extends JpaRepository<Question, Long>{

	Page<Question> findByMarkedAndAnswer_Concept(boolean marked, Concept concept, Pageable page);

	Page<Question> findByMarkedAndAnswer_ConceptAndUser(boolean marked, Concept concept, User user, Pageable page);

}
