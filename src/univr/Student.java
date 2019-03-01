package takamaka.univr;
import java.util.Date;
import java.util.Map;
import java.util.stream.Stream;

import takamaka.lang.Contract;
import takamaka.lang.Entry;
import takamaka.lang.Payable;
import takamaka.lang.Storage;
import takamaka.util.StorageMap;

public class Student extends Contract {
	private final StorageMap<Integer, Career> careers = new StorageMap<>();
	private final Contract university;			// univr contract
	
	public Student(Contract university) {
		this.university = university;
	}
	
	public void newCareer(int regNumber, int courseId, int academicYear) {
		require(payer() == university, "Only univr can add a new carrer");
		careers.put(regNumber, new Career(regNumber, courseId, academicYear));
	}
	
	public void addExam(int regNumber, Exam e) {
		require(payer() == university, "Only univr can add a new exam");
		careers.get(regNumber).addExam(e);
	}
	
	public void addFee(int regNumber, Fee fee) {
		require(payer() == university, "Only univr can add a fee");
		careers.get(regNumber).addFee(fee);
	}
	
	public @Payable @Entry void payFee(int regNumber, int feeId) {
		require(!careers.get(regNumber).tax.get(feeId).isPaid(), "The fee has already been paid");
		require(balance() >= careers.get(regNumber).tax.get(feeId).getAmount(), "Budget not sufficient");
		careers.get(regNumber).payFee(feeId);
	}	
	
	public void recordEvaluation(int regNumber, int examId, int evaluation, Date date) {
		require(payer() == university, "only univr can register an exam");
		careers.get(regNumber).recordEvaluation(examId, evaluation, date);
	}
	
	public double getWeightedAverage(int regNumber) {
		return careers.get(regNumber).getWeightedAverage();
	}
	
	public double getArithmeticAverage(int regNumber) {
		return careers.get(regNumber).getWeightedAverage();
	}
	
	private class Career extends Storage {
		private final int regNumber;
		private final int courseId;
		private final StorageMap<Integer, Fee> tax = new StorageMap<>();
		private final StorageMap<Integer, Exam> exams = new StorageMap<>();
		private final int academicYear;				// 2017 => 2017/2018
		
		private Career(int regNumber, int courseId, int academicYear) {
			this.regNumber = regNumber;
			this.courseId = courseId;
			this.academicYear = academicYear;
			
			exams = university.getMandatoryExams(courseId);
		}
		
		private void addExam(Exam e) {
			exams.put(e.getExamId(), e);
		}
		
		private void recordEvaluation(int examId, int evaluation, Date date) {
			exams.get(examId).recordExam(evaluation, date);
		}
		
		private @Payable @Entry void payFee(int feeId) {
			pay(university, tax.get(feeId).getAmount());
			tax.get(feeId).setPayed();
		}
		
		private void addFee(Fee fee) {
			tax.put(fee.getFeeId(), fee);
		}
		
		private double getWeightedAverage() {
			int res = 0;
			int cfu = 0;
			
			for (Map.Entry<Integer, Exam> e : exams.entrySet()) {
				Exam exam = e.getValue();
				if (exam.isRegistered()) {
					res += exam.getEvaluation() * exam.getCfu();
					cfu += exam.getCfu();
				}
			}
			return res / cfu;
		}
		
		private double getArithmeticAverage() {
			int res = 0;
			int sum = 0;
			
			for (Map.Entry<Integer, Exam> e : exams.entrySet()) {
				Exam exam = e.getValue();
				if (exam.isRegistered()) {
					res += exam.getEvaluation();
					sum ++;
				}
			}
			return res / sum;
		}
	}
}
