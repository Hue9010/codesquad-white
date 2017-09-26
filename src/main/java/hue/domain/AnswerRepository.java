package hue.domain;

import org.springframework.data.repository.CrudRepository;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
	Answer findByQuestion(Question question);
}
