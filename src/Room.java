import java.util.*;
import java.io.*;

public class Room implements  Serializable { // Ŭ���� ����ȭ�ϱ�
	private static final long serialVersionUID = -3818522415606558019L;
	
	//RoomManagement roomMan;
	
	// �ʵ� = ����ȭ ��� 
	static int total_Income;
	int capacity;    // �� �ο� ��
	int roomNum;  // �� ��ȣ
	int billPerHour; // �ð� �� ����
	int billPerDay;  // ��¥ �� ����
	// check-in, check-out �� ���
	boolean isUsed; // ���������
	User user; // ������� ����� ��ü
	private Calendar startTime; // ��� ���� �ð�(��� ���� �ð��� ����)
	//private long startTime;
	//private long endTime;
	
	//11/13 �߰�
	private boolean isEmpty = true; // ���͵�� ���� (����� �� true)
	private long checkInTime; // üũ�� �ð�
	private long checkOutTime; // üũ�ƿ� �ð�
	private int payment; // ���� �ݾ�

	// ������ 
	Room(int roomNum, int capacity, int billPerHour, int billPerDay) {
		this.capacity = capacity;
		this.roomNum = roomNum;
		this.billPerHour = billPerHour;
		this.billPerDay = billPerDay;
		this.isUsed = false;
	}
	
	// �޼ҵ� 
	Room(int roomNum) {
		this.roomNum = roomNum;
	}

	// ���� �ο�
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}

	// �� ��ȣ
	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}
	
	int getRoomNum() {
		return roomNum;
	}
	
	// �ð� �� ���
	public void setBillPerHour(int billPerHour) {
		this.billPerHour = billPerHour;
	}

	public int getBillPerHour() {
		return billPerHour;
	}
	
	// �� �� ���
	public void setBillPerDay(int billPerDay) {
		this.billPerDay = billPerDay;
	}

	public int getBillPerDay() {
		return billPerDay;
	}
	
	Room(User user){
		this.user = user;
	}
	
	// user ���� �޾ƿ���
	User getUser() {
		return user;
		 }

	// �� ����� T vs F
	boolean isRoomUsed() {
		return isUsed;
	}
	
	//equals �Լ� ������
	public boolean equals(Object obj) {
		if(obj instanceof Room) {
			//�� ��ȣ
			if(this.roomNum!=0 && ((Room) obj).getRoomNum()!=0 && this.roomNum == (((Room) obj).getRoomNum()))
					return true;
			//����� �̸�
			else if(this.user!=null && ((Room) obj).getUser()!=null && this.user.getphoneNo().equals(((Room) obj).getUser().getphoneNo()))
				return true;
			
			else
				return false;
		}
		
		return false;
	}
	
	// üũ��
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

			// üũ�ƿ�
			int checkOut() {
				int payBill = pay(startTime, new GregorianCalendar());
				isUsed = false;
				user = null;
				return payBill;
			}

		// �����ؾ��� �ݾ� ����
		int pay(Calendar start, Calendar end) {
			int usedTime = 0; // �ð����� ���
			int usedDay = 0; // ��¥�� ���
			
			// ��¥�� �Ѿ�� ���
			if (end.get(Calendar.DATE) > start.get(Calendar.DATE)) {
				usedDay = (end.get(Calendar.DATE) - start.get(Calendar.DATE));
				return usedDay * billPerDay;
			}
			
			// �� �̿��� ���
			else {
				// �⺻ �ð�
				usedTime = end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY);
				// ����� �� ���
				if ((60 - start.get(Calendar.MINUTE)) + end.get(Calendar.MINUTE) > 60
						|| start.get(Calendar.MINUTE) - end.get(Calendar.MINUTE) == 0)
					usedTime += 1; 
				return usedTime * billPerHour;
			}
		}
	
	//ObjectOutStream���� ����
		void writeRoomInfo(ObjectOutputStream output) throws Exception {	
			output.writeObject(this);
		}
}
