import java.util.*;
import java.io.*;

public class Room implements  Serializable { // 클래스 직렬화하기
	private static final long serialVersionUID = -3818522415606558019L;
	
	//RoomManagement roomMan;
	
	// 필드 = 직렬화 대상 
	static int total_Income;
	int capacity;    // 방 인원 수
	int roomNum;  // 방 번호
	int billPerHour; // 시간 당 가격
	int billPerDay;  // 날짜 당 가격
	// check-in, check-out 시 사용
	boolean isUsed; // 사용중인지
	User user; // 사용중인 사용자 객체
	private Calendar startTime; // 사용 시작 시간(사용 종료 시간은 생략)
	//private long startTime;
	//private long endTime;
	
	//11/13 추가
	private boolean isEmpty = true; // 스터디룸 상태 (비었을 때 true)
	private long checkInTime; // 체크인 시간
	private long checkOutTime; // 체크아웃 시간
	private int payment; // 지불 금액

	// 생성자 
	Room(int roomNum, int capacity, int billPerHour, int billPerDay) {
		this.capacity = capacity;
		this.roomNum = roomNum;
		this.billPerHour = billPerHour;
		this.billPerDay = billPerDay;
		this.isUsed = false;
	}
	
	// 메소드 
	Room(int roomNum) {
		this.roomNum = roomNum;
	}

	// 수용 인원
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}

	// 방 번호
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	
	int getRoomNum() {
		return roomNum;
	}
	
	// 시간 당 요금
	public void setBillPerHour(int billPerHour) {
		this.billPerHour = billPerHour;
	}

	public int getBillPerHour() {
		return billPerHour;
	}
	
	// 일 당 요금
	public void setBillPerDay(int billPerDay) {
		this.billPerDay = billPerDay;
	}

	public int getBillPerDay() {
		return billPerDay;
	}
	
	Room(User user){
		this.user = user;
	}
	
	// user 정보 받아오기
	User getUser() {
		return user;
		 }

	// 방 사용중 T vs F
	boolean isRoomUsed() {
		return isUsed;
	}
	
	//equals 함수 재정의
	public boolean equals(Object obj) {
		if(obj instanceof Room) {
			//방 번호
			if(this.roomNum!=0 && ((Room) obj).getRoomNum()!=0 && this.roomNum == (((Room) obj).getRoomNum()))
					return true;
			//사용자 이름
			else if(this.user!=null && ((Room) obj).getUser()!=null && this.user.getphoneNo().equals(((Room) obj).getUser().getphoneNo()))
				return true;
			
			else
				return false;
		}
		
		return false;
	}
	
	// 체크인
			void checkIn(User user) {
				this.user = user;
				startTime = new GregorianCalendar();
				isUsed = true;
			}

			void checkIn(User user, Calendar startTime) {
				this.user = user;
				this.startTime = startTime;
				isUsed = true;
			}

			// 체크아웃
			int checkOut() {
				int payBill = pay(startTime, new GregorianCalendar());
				isUsed = false;
				user = null;
				return payBill;
			}

		// 지불해야할 금액 설정
		int pay(Calendar start, Calendar end) {
			int usedTime = 0; // 시간으로 계산
			int usedDay = 0; // 날짜로 계산
			
			// 날짜를 넘어갔을 경우
			if (end.get(Calendar.DATE) > start.get(Calendar.DATE)) {
				usedDay = (end.get(Calendar.DATE) - start.get(Calendar.DATE));
				return usedDay * billPerDay;
			}
			
			// 그 이외의 경우
			else {
				// 기본 시간
				usedTime = end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
				// 사용한 분 고려
				if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60
						|| start.get(Calendar.MINUTE) - end.get(Calendar.MINUTE) == 0)
					usedTime += 1; 
				return usedTime * billPerHour;
			}
		}
	
	//ObjectOutStream으로 수정
		void writeRoomInfo(ObjectOutputStream output) throws Exception {	
			output.writeObject(this);
		}
}
