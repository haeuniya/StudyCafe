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
		// Scanner 설정
		Scanner scan = new Scanner(System.in); //h Scanner 클래스를 scan으로 선언, 생성한다
		StudyRoomManagement roomMan; // RoomManagement 클래스를 roomMan을 선언한다

		try { // 파일 try-catch
			//파일 생성
			File file = new File("./Room.dat"); // File클래스는 파일 이름을 매개변수로 하는 생성자로 객체를 생성할 수 있다.
			file.createNewFile();
			ObjectInputStream inputFile = null; // 객체 단위로 읽는 파일 선언
			try {
				inputFile = new ObjectInputStream(new FileInputStream(file)); // 객체 단위로 읽는 파일에  file을 변수로 하는 생성자를 만듦
			} catch (Exception e) {
			} 
			
			// management 필드를 저장하기 위한 파일 생성
			File file2 = new File("./ManagerPassword.dat"); // File클래스는 파일 이름을 매개변수로 하는 생성자로 객체를 생성할 수 있다.
			file2.createNewFile();
			DataInputStream inputFile2 = null; // 객체 단위로 읽는 파일 선언
			inputFile2 = new DataInputStream(new FileInputStream(file2)); // 객체 단위로 읽는 파일에  file을 변수로 하는 생성자를 만듦	
			
			// 수입
			File file3 = new File("./ManagerIncome.dat"); // File클래스는 파일 이름을 매개변수로 하는 생성자로 객체를 생성할 수 있다.
			file3.createNewFile();
			DataInputStream inputFile3 = null; // 객체 단위로 읽는 파일 선언
			inputFile3 = new DataInputStream(new FileInputStream(file3)); // 객체 단위로 읽는 파일에  file을 변수로 하는 생성자를 만듦
			
			roomMan = new StudyRoomManagement(inputFile3, inputFile2, inputFile); //RoomManagement클래스 생성자를 roomMan으로 선언
			//roomMan = new RoomManagement("manager0425", inputFile); //RoomManagement클래스 생성자를 roomMan으로 선언

			// mode 변경을 위한 변수
			boolean end = false; // 끝내는 모드인 end를 boolean으로 선언

			while (!end) { // true일 동안 while문 진행
				// mode 변경을 위한 변수
				boolean isUser = false; // user모드를 boolean - false로 선언
				boolean isManager = false; // manager모드를 boolean - false로 선언

				// 사용자모드 vs 관리자모드
				// 메뉴
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
						isUser = true; // user모드가 true로 됨
						break; // switch문 빠져나감
					case 2:
						isManager = true;
						break;
					case 3:
						end = true;
						break;
					default:
						System.out.println("잘못된 숫자입니다. 다시 입력해주세요");
						break;
					}
				} catch (InputMismatchException e) {
					scan = new Scanner(System.in);
					System.out.println("잘못 입력하셨습니다. 숫자를 입력해주세요");
				}
				
				/*
				// mode를 받았는지 확인
				boolean gotMode = false; // gotMode를 false로 선언,
				while (!gotMode) { // true일동안 while문 실행
					try {
						System.out.print("--> ");
						int num = scan.nextInt();
						System.out.println();

						switch (num) {
						case 1:
							isUser = true; // user모드가 true로 됨
							gotMode = false; // gotmode모드가 true로 됨 
							break; // switch문 빠져나감
						case 2:
							isManager = true;
							gotMode = true;
							break;
						case 3:
							end = true;
							gotMode = true;
							break;
						default:
							System.out.println("잘못된 숫자입니다. 다시 입력해주세요");
							break;
						}
					} catch (InputMismatchException e) {
						scan = new Scanner(System.in);
						System.out.println("잘못 입력하셨습니다. 숫자를 입력해주세요");
					}
				}*/
				
				boolean exit = false;
				// User 모드
				if (isUser) {
					// User 정보 입력
					
					System.out.println("사용자의 번호를 입력해주세요");
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
							
							case 0: // 빈 방 찾기
								System.out.println("[View Rooms]");
								System.out.print("인원수를 입력하세요: ");

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
									System.out.print("숫자를 입력해주세요");
								} catch (Exception e) {
									// 사용 가능한 방이 없을 경우
									System.out.println(e.getMessage());
								}
								break;

							case 1: // check-in
								boolean checkIn = false;
								
								System.out.println("1. 모든방 보기");
								System.out.println("2. 인원수에 따른 방 보기");
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
											
											System.out.print("방 번호를 입력해주세요: ");
											scan = new Scanner(System.in);
											int roomNum = scan.nextInt();

											roomMan.checkIn(roomNum, user);
											System.out.println(phoneNo + "님 체크인 준비가 완료되었습니다.");
											checkIn = false;
											break;
											
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}
										
									}
									
									else if (check == 2) {
										System.out.print("인원수를 입력하세요: ");

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
											System.out.print("방 번호를 입력해주세요: ");
											scan = new Scanner(System.in);
											int roomNum = scan.nextInt();

											roomMan.checkIn(roomNum, user);
											System.out.println(phoneNo + "님 체크인 준비가 완료되었습니다.");
											checkIn = false;
											break;
											
										} catch (InputMismatchException e) {
											scan = new Scanner(System.in);
											System.out.print("숫자를 입력해주세요");
										} catch (Exception e) {
											// 사용 가능한 방이 없을 경우
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
								System.out.println("잘못 입력하셨습니다. 다시 입력하세요");
								break;
							}
							System.out.println();

						} catch (InputMismatchException e) {
						}

					}
				}

				// Manager 모드
				else if (isManager && !exit) {
					// ManagerID 입력
					System.out.println("Passward: ");
					System.out.print("--> ");
					scan.nextLine();
					String managerPassward = scan.nextLine();
					System.out.println();

					// 올바른 managerID를 입력했는지 판별
					boolean correctID = false;
					while (!correctID && !exit) {
						if (roomMan.matchMangerID(managerPassward)) {
							correctID = true;
						} else {
							System.out.println("Passwaord가 틀립니다! 메인으로 돌아가시려면 4를 누르세요.");
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
							// 추후 추가 예정
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
									
								case 1: // 방 생성
									System.out.println("[Make Room]");
									boolean checkInfo = false;
									int roomNum = 0;
									int capacity = 0, billPerHour = 0, billPerDay =0;
									while (!checkInfo) {
										// 방 정보 입력
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

										// 입력된 정보 확인시키기
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

								case 2: // 방 삭제
									System.out.println("[Remove Room]");
									if (roomMan.roomArrayList.size() != 0) {
										System.out.print("삭제하려는 방 번호를 입력하세요: ");
										scan = new Scanner(System.in);
										int roomNumber = scan.nextInt();
										roomMan.removeRoom(roomNumber);
										System.out.println(roomNumber + "번 방이 삭제되었습니다.");
									} else {
										System.out.print("방이 존재하지 않습니다");
										System.out.println();
									}
									break;

								case 3: // 모든 방 이름 리스트 뽑기
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
											case 1: // 오늘 수입
												try {
													System.out.print("today's income");
													System.out.println();
													System.out.print("Month : ");
													month = scan.nextInt();
													System.out.print("Date : ");
													date = scan.nextInt();
													int dailyIncome = roomMan.getDailyIncome(month, date);
													System.out.println("[ " + month + "월 " + date + "일 매출 : " + dailyIncome + "원 ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("숫자를 입력해주세요");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;

											case 2: // 이번달 수입
												try {
													System.out.print("this month's income");
													System.out.println();
													System.out.print("Month : ");
													month = scan.nextInt();
													int monthIncome = roomMan.getMonthIncome(month);
													System.out.println("[ " + month + "월 매출 : " + monthIncome + "원 ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("숫자를 입력해주세요");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;
												
											case 3: // 총 수입
												try {
													System.out.print("total income");
													System.out.println();
													//int totalIncome = roomMan.getTotalIncome();
													//System.out.println("[ 총 매출 : " + totalIncome + "원 ]");
													System.out.println("[ 총 매출 : " + roomMan.total_income + "원 ]");
												} catch (InputMismatchException e) {
													scan = new Scanner(System.in);
													System.out.print("숫자를 입력해주세요");
												} catch (Exception e) {
													System.out.println(e.getMessage());
												}
												break;
											case 4:
												income = true;
												break;

											default:
												System.out.println("잘못 입력하셨습니다. 다시 입력하세요");
												break;
											}
											System.out.println();

										} catch (InputMismatchException e) {
											scan = new Scanner(System.in);
											System.out.println("잘못 입력하셨습니다. 숫자를 입력하세요.");
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
									System.out.println("잘못 입력하셨습니다.다시 입력하세요");
									break;
								}
							} catch (InputMismatchException e) {
								System.out.println("잘못 입력하셨습니다. 다시 입력하세요.");
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							System.out.println();

						} catch (InputMismatchException e) {
							scan = new Scanner(System.in);
							System.out.println("잘못 입력하셨습니다. 숫자를 입력하세요.");
						}

					}
				}
			}

			// UI 종료
			System.out.println("안녕히 가십시오");
			inputFile.close();
			// 역직렬화 objectOutputStream
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			DataOutputStream output2 = new DataOutputStream(new FileOutputStream(file2));
			DataOutputStream output3 = new DataOutputStream(new FileOutputStream(file3));
			roomMan.writeRoomInfos(output,output2,output3);
			output.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("파일 오류(FileNotFoundException)");
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("파일 읽기 오류(IOException)");
		} catch (Exception e) {
			System.out.println("알수 없는 오류"+e.getMessage());
			e.getMessage();
		}

		scan.close();
		

	}

}
