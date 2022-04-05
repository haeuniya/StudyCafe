import java.util.*;
import java.io.*;

public class StudyRoomManagement {
	public ArrayList<Room> roomArrayList = new ArrayList<>(); //�迭�� ����Ʈ�� �ٲ�
	private String managerPassword; //������ Password
	static int total_income; // �� ����
	private int[][] income = new int[12][31]; // �� �� ���� �迭
	public int dailyIncome;
	public int monthIncome;
	
	// ������
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
	
	// �Ŵ��� �н�����
	void setManagerPW(String changePassward) {
		this.managerPassword = changePassward;
	}
	
	// �Ŵ��� ���̵� Ȯ��
	boolean matchMangerID(String managerID) {
		return (this.managerPassword.equals(managerID));
	}
	
	// �� ũ�⿡ ���� ��� ã��
	ArrayList<Room> searchEmptyRoom(int size) throws Exception {
		ArrayList<Room> buff = new ArrayList<>();
		boolean foundRoom = false;
		
		for(int i = 0; i< roomArrayList.size();i++) {
			Room room = roomArrayList.get(i);
			if(room.getCapacity() >= size && !room.isRoomUsed()) {	//�Էµ� ���� ũ�� �̻��� capacity�� ���� ���
				buff.add(room);
				foundRoom = true;
			}
		}
		
		if(!foundRoom)	//ã�� ������ �ÿ� ���� ó��
			throw new Exception("��� ������ ���� �����ϴ�.");
		return buff;
	}
	
	//��� �� ã��
	ArrayList<Room> searchRoom() throws Exception {
		if(roomArrayList.size() == 0)
			throw new Exception("���� �����ϴ�");
		else 
			return roomArrayList;
	}
	
	//�� ����
	void makeRoom(Room newRoom) {
		roomArrayList.add(newRoom);
	}
	
	//�� ����  //roomArrayList.remove(roomNum);
	void removeRoom(int roomNum) throws Exception {		
		Room room = new Room(roomNum);
		boolean foundRoom = roomArrayList.contains(room);
		if(foundRoom) {
			roomArrayList.remove(room);
		}
		else {	//ã�� ������ �� ���� ó��
			throw new Exception("���� �����ϴ�");
		}
	}
	
	
	// �� ���(check-in) :equals ���� �Ϸ�
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

		// �� ��� ���� �� ��� �ݾ� ��ȯ :equals ���� �Ϸ�
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
		
		// ����(income)�� �����ϴ� �޼ҵ�, ���� ������Ʈ
		void setIncome(int payment) {
			// ��¥ Ȯ��
			GregorianCalendar calendar = new GregorianCalendar();
			int month = calendar.get(Calendar.MONTH);
			int date = calendar.get(Calendar.DATE);
			
			// ��¥�� �´� �迭�� ���� ������Ʈ
			income[month][date - 1] += payment;
		}
		
		// �Ϸ� ������ ��ȯ�ϴ� �޼ҵ�
		int getDailyIncome(int month, int date) {
			dailyIncome = income[month - 1][date - 1];
			return dailyIncome;
		}
		
		// �� �� ������ ��ȯ�ϴ� �޼ҵ�
		int getMonthIncome(int month) {
			//int monthIncome = 0;
			
			for (int i = 0; i < 31; i++)
				monthIncome += income[month - 1][i];
			
			return monthIncome;
		}
	
		// ������ ����, ObjectOutputStream�� UI���� ����
		void writeRoomInfos(ObjectOutputStream output, DataOutputStream outputPassword, DataOutputStream outputIncome) throws Exception {
			output.writeObject(roomArrayList);
			outputPassword.writeUTF(managerPassword);
			outputIncome.writeInt(total_income);
			outputIncome.writeInt(monthIncome);
			outputIncome.writeInt(dailyIncome);
		}
		
}
