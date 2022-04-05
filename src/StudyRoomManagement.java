import java.util.*;
import java.io.*;

public class StudyRoomManagement {
	public ArrayList<Room> roomArrayList = new ArrayList<>(); //배열을 리스트로 바꿈
	private String managerPassword; //관리자 Password
	static int total_income; // 총 수입
	private int[][] income = new int[12][31]; // 한 달 수입 배열
	public int dailyIncome;
	public int monthIncome;
	
	// 생성자
	StudyRoomManagement(InputStream outIncome,InputStream out, ObjectInputStream OI) {
		try {
			this.managerPassword = (((DataInput) out).readUTF());
			this.total_income = (((DataInput) outIncome).readInt());
			this.monthIncome = (((DataInput) outIncome).readInt());
			this.dailyIncome = (((DataInput) outIncome).readInt());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.managerPassword = "haeun123";
			//this.total_income = 10000;
		}
		
		try {
			roomArrayList = (ArrayList<Room>) OI.readObject();
		} catch (Exception e) {
		}
	  }
	
	// 매니저 패스워드
	void setManagerPW(String changePassward) {
		this.managerPassword = changePassward;
	}
	
	// 매니저 아이디 확인
	boolean matchMangerID(String managerID) {
		return (this.managerPassword.equals(managerID));
	}
	
	// 방 크기에 따른 빈방 찾기
	ArrayList<Room> searchEmptyRoom(int size) throws Exception {
		ArrayList<Room> buff = new ArrayList<>();
		boolean foundRoom = false;
		
		for(int i = 0; i< roomArrayList.size();i++) {
			Room room = roomArrayList.get(i);
			if(room.getCapacity() >= size && !room.isRoomUsed()) {	//입력된 방의 크기 이상의 capacity의 방을 출력
				buff.add(room);
				foundRoom = true;
			}
		}
		
		if(!foundRoom)	//찾지 못했을 시에 예외 처리
			throw new Exception("사용 가능한 방이 없습니다.");
		return buff;
	}
	
	//모든 방 찾기
	ArrayList<Room> searchRoom() throws Exception {
		if(roomArrayList.size() == 0)
			throw new Exception("방이 없습니다");
		else 
			return roomArrayList;
	}
	
	//방 생성
	void makeRoom(Room newRoom) {
		roomArrayList.add(newRoom);
	}
	
	//방 삭제  //roomArrayList.remove(roomNum);
	void removeRoom(int roomNum) throws Exception {		
		Room room = new Room(roomNum);
		boolean foundRoom = roomArrayList.contains(room);
		if(foundRoom) {
			roomArrayList.remove(room);
		}
		else {	//찾지 못했을 시 예외 처리
			throw new Exception("방이 없습니다");
		}
	}
	
	
	// 방 사용(check-in) :equals 수정 완료
		void checkIn(int roomName, User user) throws Exception {
			Room room = new Room(roomName);
			boolean foundRoom = roomArrayList.contains(room);
			if(foundRoom) {
				roomArrayList.get(roomArrayList.indexOf(room)).checkIn(user);
			}
			else {
				throw new Exception("no room");
			}
		}

		// 방 사용 종료 및 사용 금액 반환 :equals 수정 완료
		int checkOut(User user) throws Exception {
			Room room = new Room(user);
			boolean foundRoom = roomArrayList.contains(room);
			
			if(foundRoom) {
				return roomArrayList.get(roomArrayList.indexOf(room)).checkOut();
			}
			else {
				throw new Exception("no room");
			}
		}
		
		void income(User user) throws Exception {
			Room room = new Room(user);
			boolean foundRoom = roomArrayList.contains(room);
			if(foundRoom) {
				roomArrayList.get(roomArrayList.indexOf(room)).checkOut();
			}
			else {
				throw new Exception("no room");
			}
		}
		
		// 수입(income)을 설정하는 메소드, 수입 업데이트
		void setIncome(int payment) {
			// 날짜 확인
			GregorianCalendar calendar = new GregorianCalendar();
			int month = calendar.get(Calendar.MONTH);
			int date = calendar.get(Calendar.DATE);
			
			// 날짜에 맞는 배열에 수입 업데이트
			income[month][date - 1] += payment;
		}
		
		// 하루 수입을 반환하는 메소드
		int getDailyIncome(int month, int date) {
			dailyIncome = income[month - 1][date - 1];
			return dailyIncome;
		}
		
		// 한 달 수입을 반환하는 메소드
		int getMonthIncome(int month) {
			//int monthIncome = 0;
			
			for (int i = 0; i < 31; i++)
				monthIncome += income[month - 1][i];
			
			return monthIncome;
		}
	
		// 방정보 저장, ObjectOutputStream은 UI에서 생성
		void writeRoomInfos(ObjectOutputStream output, DataOutputStream outputPassword, DataOutputStream outputIncome) throws Exception {
			output.writeObject(roomArrayList);
			outputPassword.writeUTF(managerPassword);
			outputIncome.writeInt(total_income);
			outputIncome.writeInt(monthIncome);
			outputIncome.writeInt(dailyIncome);
		}
		
}
