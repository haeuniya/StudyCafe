import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StudyRoomUI {

	public static void main(String[] args) {
		// Scanner ����
		Scanner scan = new Scanner(System.in); //h Scanner Ŭ������ scan���� ����, �����Ѵ�
		StudyRoomManagement roomMan; // RoomManagement Ŭ������ roomMan�� �����Ѵ�

		try { // ���� try-catch
			//���� ����
			File file = new File("./Room.dat"); // FileŬ������ ���� �̸��� �Ű������� �ϴ� �����ڷ� ��ü�� ������ �� �ִ�.
			file.createNewFile();
			ObjectInputStream inputFile = null; // ��ü ������ �д� ���� ����
			try {
				inputFile = new ObjectInputStream(new FileInputStream(file)); // ��ü ������ �д� ���Ͽ�  file�� ������ �ϴ� �����ڸ� ����
			} catch (Exception e) {
			} 
			
			// management �ʵ带 �����ϱ� ���� ���� ����
			File file2 = new File("./ManagerPassword.dat"); // FileŬ������ ���� �̸��� �Ű������� �ϴ� �����ڷ� ��ü�� ������ �� �ִ�.
			file2.createNewFile();
			DataInputStream inputFile2 = null; // ��ü ������ �д� ���� ����
			inputFile2 = new DataInputStream(new FileInputStream(file2)); // ��ü ������ �д� ���Ͽ�  file�� ������ �ϴ� �����ڸ� ����	
			
			// ����
			File file3 = new File("./ManagerIncome.dat"); // FileŬ������ ���� �̸��� �Ű������� �ϴ� �����ڷ� ��ü�� ������ �� �ִ�.
			file3.createNewFile();
			DataInputStream inputFile3 = null; // ��ü ������ �д� ���� ����
			inputFile3 = new DataInputStream(new FileInputStream(file3)); // ��ü ������ �д� ���Ͽ�  file�� ������ �ϴ� �����ڸ� ����
			
			roomMan = new StudyRoomManagement(inputFile3, inputFile2, inputFile); //RoomManagementŬ���� �����ڸ� roomMan���� ����
			//roomMan = new RoomManagement("manager0425", inputFile); //RoomManagementŬ���� �����ڸ� roomMan���� ����

			// mode ������ ���� ����
			boolean end = false; // ������ ����� end�� boolean���� ����

			while (!end) { // true�� ���� while�� ����
				// mode ������ ���� ����
				boolean isUser = false; // user��带 boolean - false�� ����
				boolean isManager = false; // manager��带 boolean - false�� ����

				// ����ڸ�� vs �����ڸ��
				// �޴�
				System.out.println("[ Welcome to Study Room :) ]");
				System.out.println("1.User");
				System.out.println("2.Manager");
				System.out.println("3.Exit");
				
				try {
					System.out.print("--> ");
					int num = scan.nextInt();
					System.out.println();

					switch (num) {
					case 1:
						isUser = true; // user��尡 true�� ��
						break; // switch�� ��������
					case 2:
						isManager = true;
						break;
					case 3:
						end = true;
						break;
					default:
						System.out.println("�߸��� �����Դϴ�. �ٽ� �Է����ּ���");
						break;
					}
				} catch (InputMismatchException e) {
					scan = new Scanner(System.in);
					System.out.println("�߸� �Է��ϼ̽��ϴ�. ���ڸ� �Է����ּ���");
				}
				
				/*
				// mode�� �޾Ҵ��� Ȯ��
				boolean gotMode = false; // gotMode�� false�� ����,
				while (!gotMode) { // true�ϵ��� while�� ����
					try {
						System.out.print("--> ");
						int num = scan.nextInt();
						System.out.println();

						switch (num) {
						case 1:
							isUser = true; // user��尡 true�� ��
							gotMode = false; // gotmode��尡 true�� �� 
							break; // switch�� ��������
						case 2:
							isManager = true;
							gotMode = true;
							break;
						case 3:
							end = true;
							gotMode = true;
							break;
						default:
							System.out.println("�߸��� �����Դϴ�. �ٽ� �Է����ּ���");
							break;
						}
					} catch (InputMismatchException e) {
						scan = new Scanner(System.in);
						System.out.println("�߸� �Է��ϼ̽��ϴ�. ���ڸ� �Է����ּ���");
					}
				}*/
				
				boolean exit = false;
				// User ���
				if (isUser) {
					// User ���� �Է�
					
					System.out.println("������� ��ȣ�� �Է����ּ���");
					System.out.print("--> ");
					scan.nextLine(); 
					String phoneNo = scan.nextLine();
					System.out.println();
					User user = new User(phoneNo);

					// User Menu
					while (!exit) {
						/*String phoneNo;
						String roomNum;*/
						//User user = new User(phoneNo);
						
						try {
							System.out.println("[Menu]");
							//System.out.println("1. View Rooms");
							System.out.println("1. Check in");
							System.out.println("2. Check Out");
							System.out.println("3. Go back to main");
							System.out.print("--> ");
							int num = scan.nextInt();
							System.out.println();

							switch (num) {
							
							case 0: // �� �� ã��
								System.out.println("[View Rooms]");
								System.out.print("�ο����� �Է��ϼ���: ");

								try {
									int roomSize = scan.nextInt();
									ArrayList<Room> Room = roomMan.searchEmptyRoom(roomSize);
									System.out.println("Available Rooms are");
									System.out.println("list\tRoomNum\tCapacity\tBillPerHour\tBillPerDay");
									for (int i = 0; i < Room.size(); i++) {
										Room room = Room.get(i);
										if (room == null)
											break;
										else {
											System.out.println((i + 1) + "|\t" + room.getRoomNum() + "\t"
													+ room.getCapacity() + "\t\t" + room.getBillPerHour()+ "\t\t" + room.getBillPerDay());
										}
									}
								} catch (InputMismatchException e) {
									scan = new Scanner(System.in);
									System.out.print("���ڸ� �Է����ּ���");
								} catch (Exception e) {
									// ��� ������ ���� ���� ���
									System.out.println(e.getMessage());
								}
								break;

							case 1: // check-in
								boolean checkIn = false;
								
								System.out.println("1. ���� ����");
								System.out.println("2. �ο����� ���� �� ����");
								System.out.print("--> ");
								int check = scan.nextInt();
								System.out.println();
								while (!checkIn) {
									if (check == 1) {
										System.out.println("[View all Room]");
										ArrayList<Room> Room = roomMan.searchRoom();
										System.out.println("Rooms: ");
										System.out.println("num\troomNum\tCapacity\tBillPerHour\tBillPerDay\tUsing");
										for (int i = 0; i < Room.size(); i++) {
											Room room = Room.get(i);
											if (room == null)
												break;
											else {
												System.out.println(
														(i + 1) + "|\t" + room.getRoomNum() + "\t" + room.getCapacity()
																+ "\t\t" + room.getBillPerHour()+ "\t\t" + room.getBillPerDay() + "\t\t" + room.isRoomUsed());
											}
										}
										
										try {
											
											System.out.print("�� ��ȣ�� �Է����ּ���: ");
											scan = new Scanner(System.in);
											int roomNum = scan.nextInt();

											roomMan.checkIn(roomNum, user);
											System.out.println(phoneNo + "�� üũ�� �غ� �Ϸ�Ǿ����ϴ�.");
											checkIn = false;
											break;
											
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}
										
									}
									
									else if (check == 2) {
										System.out.print("�ο����� �Է��ϼ���: ");

										try {
											int roomSize = scan.nextInt();
											ArrayList<Room> Room = roomMan.searchEmptyRoom(roomSize);
											System.out.println("Available Rooms are");
											System.out.println("list\tRoomNum\tCapacity\tBillPerHour\tBillPerDay");
											for (int i = 0; i < Room.size(); i++) {
												Room room = Room.get(i);
												if (room == null)
													break;
												else {
													System.out.println((i + 1) + "|\t" + room.getRoomNum() + "\t"
															+ room.getCapacity() + "\t\t" + room.getBillPerHour()+ "\t\t" + room.getBillPerDay());
												}
											}
											System.out.print("�� ��ȣ�� �Է����ּ���: ");
											scan = new Scanner(System.in);
											int roomNum = scan.nextInt();

											roomMan.checkIn(roomNum, user);
											System.out.println(phoneNo + "�� üũ�� �غ� �Ϸ�Ǿ����ϴ�.");
											checkIn = false;
											break;
											
										} catch (InputMismatchException e) {
											scan = new Scanner(System.in);
											System.out.print("���ڸ� �Է����ּ���");
										} catch (Exception e) {
											// ��� ������ ���� ���� ���
											System.out.println(e.getMessage());
										}
									}
								}
								break;
								
							case 2: // check -out
								System.out.println("##Check out##");
								try {
									int payBill = roomMan.checkOut(user);
									System.out.println(
											"Your total bill would be " + payBill + "\nThank you. See you again!");
									roomMan.total_income += payBill;
									roomMan.setIncome(payBill);
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
								break;

							case 3:
								exit = true;
								break;

							default:
								System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է��ϼ���");
								break;
							}
							System.out.println();

						} catch (InputMismatchException e) {
						}

					}
				}

				// Manager ���
				else if (isManager && !exit) {
					// ManagerID �Է�
					System.out.println("Passward: ");
					System.out.print("--> ");
					scan.nextLine();
					String managerPassward = scan.nextLine();
					System.out.println();

					// �ùٸ� managerID�� �Է��ߴ��� �Ǻ�
					boolean correctID = false;
					while (!correctID && !exit) {
						if (roomMan.matchMangerID(managerPassward)) {
							correctID = true;
						} else {
							System.out.println("Passwaord�� Ʋ���ϴ�! �������� ���ư��÷��� 4�� ��������.");
							System.out.print("--> ");
							managerPassward = scan.nextLine();
							System.out.println();
							if (managerPassward.equals(new String("4")))
								exit = true;
						}
					}

					// Manager Menu
					while (!exit) {

						try {
							// ���� �߰� ����
							System.out.println("[Menu]");
							System.out.println("1. Make Room");
							System.out.println("2. Remove Room");
							System.out.println("3. View All Rooms");
							System.out.println("4. Check Income");
							System.out.println("5. Go Back To Main");
							System.out.println("6. Change Password");
							System.out.print("--> ");
							scan = new Scanner(System.in);
							int num = scan.nextInt();
							System.out.println();

							
							try {
								switch (num) {
								case 0:
									//System.out.println("Enter Password");
									//System.out.println("->");
									//scan.nextLine();
									//String pw = scan.nextLine();
									//roomMan.setManagerID(pw);
									
								case 1: // �� ����
									System.out.println("[Make Room]");
									boolean checkInfo = false;
									int roomNum = 0;
									int capacity = 0, billPerHour = 0, billPerDay =0;
									while (!checkInfo) {
										// �� ���� �Է�
										System.out.println("Enter Room Info");
										System.out.print("Room Num: ");
										scan = new Scanner(System.in);
										roomNum = scan.nextInt();
										System.out.print("Room Capacity: ");
										capacity = scan.nextInt();
										System.out.print("Bill per hour: ");
										billPerHour = scan.nextInt();
										System.out.print("Bill per Day: ");
										billPerDay = scan.nextInt();
										System.out.println();
										checkInfo = true;

										// �Էµ� ���� Ȯ�ν�Ű��
										System.out.println("Enter y/Y if following info is correct");
										System.out.println("Room Number: " + roomNum);
										System.out.println("Room Capacity: " + capacity);
										System.out.println("Bill(per hour): " + billPerHour);
										System.out.println("Bill(per hour): " + billPerDay);
										System.out.print("--> ");
										scan = new Scanner(System.in);
										String check = scan.nextLine();
										check = check.toUpperCase();

										checkInfo = check.equals("Y");
									}
									
									Room tmpRoom = new Room(roomNum, capacity, billPerHour,billPerDay);
									roomMan.makeRoom(tmpRoom);
									System.out.println("Successfully made room " + roomNum);
									break;

								case 2: // �� ����
									System.out.println("[Remove Room]");
									if (roomMan.roomArrayList.size() != 0) {
										System.out.print("�����Ϸ��� �� ��ȣ�� �Է��ϼ���: ");
										scan = new Scanner(System.in);
										int roomNumber = scan.nextInt();
										roomMan.removeRoom(roomNumber);
										System.out.println(roomNumber + "�� ���� �����Ǿ����ϴ�.");
									} else {
										System.out.print("���� �������� �ʽ��ϴ�");
										System.out.println();
									}
									break;

								case 3: // ��� �� �̸� ����Ʈ �̱�
									System.out.println("[View all Room]");
									ArrayList<Room> Room = roomMan.searchRoom();
									System.out.println("Rooms: ");
									System.out.println("num\troomNum\tCapacity\tBillPerHour\tBillPerDay\tUsing\tUser");
									for (int i = 0; i < Room.size(); i++) {
										Room room = Room.get(i);
										if (room == null)
											break;
										else {
											System.out.println(
													(i + 1) + "|\t" + room.getRoomNum() + "\t" + room.getCapacity()
															+ "\t\t" + room.getBillPerHour()+ "\t\t" + room.getBillPerDay() + "\t\t" + room.isRoomUsed()+ "\t\t" + room.getUser());
										}
									}
									break;
									
								case 4:
									boolean income = false;
									while(!income) {
										int month, date;
										try {
											System.out.println("1. Check Daily income");
											System.out.println("2. Check Monthly income");
											System.out.println("3. Check Total income");
											System.out.println("4. Go back to main");
											System.out.print("--> ");
											int n = scan.nextInt();
											System.out.println();

											switch (n) {
											case 1: // ���� ����
												try {
													System.out.print("today's income");
													System.out.println();
													System.out.print("Month : ");
													month = scan.nextInt();
													System.out.print("Date : ");
													date = scan.nextInt();
													int dailyIncome = roomMan.getDailyIncome(month, date);
													System.out.println("[ " + month + "�� " + date + "�� ���� : " + dailyIncome + "�� ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("���ڸ� �Է����ּ���");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;

											case 2: // �̹��� ����
												try {
													System.out.print("this month's income");
													System.out.println();
													System.out.print("Month : ");
													month = scan.nextInt();
													int monthIncome = roomMan.getMonthIncome(month);
													System.out.println("[ " + month + "�� ���� : " + monthIncome + "�� ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("���ڸ� �Է����ּ���");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;
												
											case 3: // �� ����
												try {
													System.out.print("total income");
													System.out.println();
													//int totalIncome = roomMan.getTotalIncome();
													//System.out.println("[ �� ���� : " + totalIncome + "�� ]");
													System.out.println("[ �� ���� : " + roomMan.total_income + "�� ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("���ڸ� �Է����ּ���");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;
											case 4:
												income = true;
												break;

											default:
												System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է��ϼ���");
												break;
											}
											System.out.println();

										} catch (InputMismatchException e) {
											scan = new Scanner(System.in);
											System.out.println("�߸� �Է��ϼ̽��ϴ�. ���ڸ� �Է��ϼ���.");
										}
									}	
								case 5:
									exit = true;
									break;
								case 6:
									System.out.println("Enter Password");
									System.out.println("->");
									scan.nextLine();
									String pw = scan.nextLine();
									roomMan.setManagerPW(pw);
									break;

								default:
									System.out.println("�߸� �Է��ϼ̽��ϴ�.�ٽ� �Է��ϼ���");
									break;
								}
							} catch (InputMismatchException e) {
								System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է��ϼ���.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							System.out.println();

						} catch (InputMismatchException e) {
							scan = new Scanner(System.in);
							System.out.println("�߸� �Է��ϼ̽��ϴ�. ���ڸ� �Է��ϼ���.");
						}

					}
				}
			}

			// UI ����
			System.out.println("�ȳ��� ���ʽÿ�");
			inputFile.close();
			// ������ȭ objectOutputStream
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			DataOutputStream output2 = new DataOutputStream(new FileOutputStream(file2));
			DataOutputStream output3 = new DataOutputStream(new FileOutputStream(file3));
			roomMan.writeRoomInfos(output,output2,output3);
			output.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("���� ����(FileNotFoundException)");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("���� �б� ����(IOException)");
		} catch (Exception e) {
			System.out.println("�˼� ���� ����"+e.getMessage());
			e.getMessage();
		}

		scan.close();
		

	}

}
