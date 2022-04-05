import java.io.Serializable;

public class User implements Serializable {
	private String phoneNo; //사용자 이름
	long startTime;
	long endTime;

	public User(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public void setphoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getphoneNo() {
		return this.phoneNo;
	}
	
	public long getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	} 
	
	public long getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	} 
}
