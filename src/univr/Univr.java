package takamaka.univr;

import takamaka.lang.Contract;
import takamaka.lang.Storage;
import takamaka.util.StorageMap;

public class Univr extends Contract {
	private final StorageMap<Integer, Course> courses = new StorageMap<>();
	
	public StorageMap<Integer, Exam> getMandatoryExams(int courseId) {
		return courses.get(courseId).mandatoryExams;
	}
	
	public Exam getExtraExam(int courseId, int examId) {
		return courses.get(courseId).extraExams.get(examId);
	}
	
	private class Course extends Storage {
		private final StorageMap<Integer, Exam> mandatoryExams = new StorageMap<>();
		private final StorageMap<Integer, Exam> extraExams = new StorageMap<>();
		
		private void insertMandatoryExam(Exam e) {
			if(mandatoryExams.get(e.getExamId()) == null)
				mandatoryExams.put(e.getExamId(), e);
		}
		
		private void deleteMandatoryExam(int examId) {
			if(mandatoryExams.get(examId) != null)
				mandatoryExams.remove(examId);
		}
	}
}
