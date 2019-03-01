package takamaka.univr;
import takamaka.lang.Contract;

public class Fee {
	private Contract beneficiary;		// univr contract
	private int id;
	private int amount;
	private String descr;
	private boolean payed;
	
	public Fee(int id, int amount, String descr, Contract beneficiary) {
		this.id = id;
		this.amount = amount;
		this.descr = descr;
		this.payed = false;
		this.beneficiary = beneficiary;
	}
	
	public boolean isPaid() {
		return payed;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public int getFeeId() {
		return id;
	}
	
	public void setPayed() {
		this.payed = true;
	}
}
