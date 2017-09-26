package hue.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Question {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	@Column(nullable = false, length = 20)
	private String title;

	@Column(nullable = false, length = 250)
	private String contents;

	@OneToMany(mappedBy="question")
	@OrderBy("id ASC")
	private List<Answer> answers;
	
	Question() {
	}

	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void update(String title, String contents) {
		this.contents = title;
		this.title = contents;
	}

	public boolean isSameUser(User loginUser) {
		return writer.equals(loginUser);
	}
//	INSERT INTO QUESTION (id, writer_id, title, contents) VALUES (1, 1, '제목1', '내용1');
//	INSERT INTO QUESTION (id, writer_id, title, contents) VALUES (2, 2, '제목2', '내용2');

}
