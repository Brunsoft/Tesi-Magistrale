package takamaka.univr;

import takamaka.lang.Storage;
import takamaka.util.Date;

public class Exam extends Storage{
	private int id;
	private String name;
	private int cfu;
	private int professorId;
	private int evaluation;
	private Date date;
	
	public Exam(int id, String name, int cfu, int professorId) {
		this.id = id;
		this.name = name;
		this.cfu = cfu;
		this.professorId = professorId;
	}
	
	public Exam() {
		// TODO Auto-generated constructor stub
	}

	public void recordExam(int evaluation, Date date) {
		this.evaluation = evaluation;
		this.date = date;
	}
	
	public boolean isRegistered() {
		return(evaluation >= 18) ? true : false;
	}
	
	public int getEvaluation() {
		return evaluation;
	}
	
	public int getCfu() {
		return cfu;
	}
	
	public int getExamId() {
		return id;
	}
}
