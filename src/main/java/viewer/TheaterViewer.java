package viewer;

import controller.TheaterController;
import lombok.Setter;
import model.TheaterDTO;
import model.UserDTO;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;

public class TheaterViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private TheaterController theaterController;
    @Setter
    private UserDTO logIn;
    @Setter
    private ScreeningInfoViewer screeningInfoViewer;

    public void showIndex() {
        screeningInfoViewer.setLogIn(logIn);
        while (true) {
            if (logIn.getGrade() == 3) {
                String message = "1. 극장 등록 2. 극장 보기 3. 돌아가기";
                int userChoice = nextInt(scanner, message, 1, 3);
                if (userChoice == 1) {
                    insert();
                } else if (userChoice == 2) {
                    printList();
                } else if (userChoice == 3) {
                    System.out.println("돌아갑니다");
                    break;
                }
            } else {
                String message = "극장 목록을 보시겠습니까? Y/N";
                String answer = nextLine(scanner, message);
                if (answer.equalsIgnoreCase("Y")) {
                    printList();
                } else if (answer.equalsIgnoreCase("N")) {
                    System.out.println("돌아갑니다");
                    break;
                } else {
                    System.out.println("잘못누르셨습니다");
                }
            }
        }
    }

    private void insert() {
        String message = "극장 이름 : ";
        String name = nextLine(scanner, message);
        message = "극장 위치 : ";
        String location = nextLine(scanner, message);
        message = "극장 번호 : ";
        String telephoneNumber = nextLine(scanner, message);

        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setName(name);
        theaterDTO.setLocation(location);
        theaterDTO.setTelephoneNumber(telephoneNumber);

        theaterController.insert(theaterDTO);
    }

    private void printList() {
        ArrayList<TheaterDTO> list = theaterController.selectAll();
        for (TheaterDTO theaterDTO : list) {
            System.out.println("극장 id : " + theaterDTO.getId());
            System.out.println("극장 이름 : " + theaterDTO.getName());
            System.out.println("===========================");
        }
        if (list.isEmpty()) {
            System.out.println("등록된 극장이 없습니다.");
        } else {
            String message = "상세보기할 극장의 번호나 뒤로가시려면 0을 눌러주세요";
            int userChoice = nextInt(scanner, message);
            while (!theaterController.validateInput(userChoice)) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = nextInt(scanner, message);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int theaterId) {
        TheaterDTO theaterDTO = theaterController.selectOne(theaterId);
        System.out.println("=======================");
        System.out.println("극장 이름 : " + theaterDTO.getName());
        System.out.println("극장 위치 : " + theaterDTO.getLocation());
        System.out.println("극장 번호 : " + theaterDTO.getTelephoneNumber());

        if (logIn.getGrade() == 3) {
            String message = "1. 극장 수정 2. 극장 삭제 3. 상영중인 영화 보기 4 돌아가기";
            int userChoice = nextInt(scanner, message);
            if (userChoice == 1) {
                update(theaterId);
            } else if (userChoice == 2) {
                delete(theaterId);
            } else if (userChoice == 3) {
                screeningInfoViewer.showIndex(theaterDTO.getId());
            } else if (userChoice == 4) {
                System.out.println("돌아갑니다");
            }
        } else {
            String message = "상영중인 영화를 보시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                //평점
                screeningInfoViewer.showIndex(theaterDTO.getId());
            } else {
                System.out.println("돌아갑니다.");
            }
        }
    }

    private void update(int theaterId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "새로운 이름을 등록해주세요";
            String newName = nextLine(scanner, message);
            message = "새로운 위치를 입력해주세요";
            String newLocation = nextLine(scanner, message);
            message = "새로운 전화번호을 입력해주세요";
            String newTelephoneNumber = nextLine(scanner, message);

            TheaterDTO theaterDTO = theaterController.selectOne(theaterId);
            theaterDTO.setName(newName);
            theaterDTO.setLocation(newLocation);
            theaterDTO.setTelephoneNumber(newTelephoneNumber);

            theaterController.update(theaterDTO);
        } else {
            System.out.println("비밀번호가 틀렸습니다");
        }
    }

    private void delete(int theaterId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "정말 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                theaterController.delete(theaterId);
                System.out.println("삭제되었습니다.");
            } else {
                System.out.println("삭제를 취소합니다");
            }
        } else {
            System.out.println("비밀번호가 틀렸습니다");
        }
    }
}
