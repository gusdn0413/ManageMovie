package viewer;

import controller.UserController;
import lombok.Setter;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;

public class UserViewer {

    @Setter
    private UserController userController;
    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO logIn;
    @Setter
    private MovieViewer movieViewer;

    public void showIndex() {
        String message = "1. 로그인 2. 회원가입 3. 프로그램 종료";
        while (true) {
            int userChoice = nextInt(scanner, message);
            if (userChoice == 1) {
                auth();
                if (logIn != null) {
                    // 회원 메뉴 실행
                    showMenu();
                }
            } else if (userChoice == 2) {
                register();
            } else if (userChoice == 3) {
                System.out.println("사용해주셔서 감사합니다.");
                break;
            }
        }

    }

    private void auth() {
        String message;
        message = "아이디를 입력해주세요.";
        String username = nextLine(scanner, message);

        message = "비밀번호를 입력해주세요.";
        String password = nextLine(scanner, message);

        logIn = userController.auth(username, password);

        if (logIn == null) {
            System.out.println("잘못 입력하셨습니다. 로그인 정보를 다시 확인해주세요.");
        }
    }

    private void register() {
        String message = "사용하실 아이디를 입력해주세요";
        String username = nextLine(scanner, message);
        if (userController.validateUsername(username)) {

            message = "사용할 비밀번호를 입력해주세요";
            String password = nextLine(scanner, message);

            message = "사용할 닉네임을 입력해주세요";
            String nickname = nextLine(scanner, message);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setNickname(nickname);
            userDTO.setGrade(1);

            userController.insert(userDTO);
        } else {
            System.out.println("중복 된 아이디는 사용할 수 없습니다.");
        }
    }

    private void showMenu() {
        while (logIn != null) {
            movieViewer.setLogIn(logIn);
            if (logIn.getGrade() == 3) {
                System.out.println("==========관리자입니다===========");
                String message = "1. 영화로 2. 회원 정보 수정 3. 특정 회원 정보 수정 4. 로그 아웃";
                int userChoice = nextInt(scanner, message);
                if (userChoice == 1) {
                    // 영화 목록 보기
                    movieViewer.showIndex();
                } else if (userChoice == 2) {
                    printInfo();
                } else if (userChoice == 3) {
                    printList();
                } else if (userChoice == 4) {
                    logIn = null;
                    System.out.println("성공적으로 로그아웃");
                    break;
                }
            } else {
                String message = "1. 영화로 2. 회원 정보 수정 3. 로그 아웃";
                int userChoice = nextInt(scanner, message);
                if (userChoice == 1) {
                    movieViewer.showIndex();
                } else if (userChoice == 2) {
                    printInfo();
                } else if (userChoice == 3) {
                    logIn = null;
                    System.out.println("성공적으로 로그아웃");
                    break;
                }
            }
        }
    }

    private void printInfo() {
        System.out.println("============================");
        System.out.println(logIn.getNickname() + " 회원님의 정보");

        System.out.println("============================");
        System.out.println("아이디 : " + logIn.getId());
        System.out.println("닉네임 : " + logIn.getNickname());
        System.out.println("회원 등급 : " + logIn.getGrade());

        System.out.println("============================");
        String message = "1. 회원 정보 수정 2. 회원 탈퇴 3. 뒤로 가기";

        int userChoice = nextInt(scanner, message);
        if (userChoice == 1) {
            update();
        } else if (userChoice == 2) {
            delete();
        } else if (userChoice == 3) {
            System.out.println("돌아갑니다");
        }
    }

    private void printList() {
        ArrayList<UserDTO> list = userController.selectAll();
        for (UserDTO userDTO : list) {
            System.out.println("===============================");
            System.out.println("회원 id : " + userDTO.getId());
            System.out.println("회원 닉네임 : " + userDTO.getNickname());
            userController.showGrade(userDTO);
            System.out.println("===============================");
        }
        if (list.isEmpty()) {
            System.out.println("등록된 회원이 없습니다");
        } else {
            String message = "상세보기할 회원의 번호나 뒤로 가실려면 0을 입력해주세요";
            int userChoice = nextInt(scanner, message);
            while (!userController.validateInput(userChoice)) {
                System.out.println("잘못 입력하셨습니다.");
                userChoice = nextInt(scanner, message);
            }
            if (userChoice != 0) {
                printOne(userChoice);
            }
        }
    }

    private void printOne(int id) {
        UserDTO userDTO = userController.selectOne(id);
        System.out.println("============================");
        System.out.println("회원의 id : " + userDTO.getId());
        System.out.println("회원의 닉네임 : " + userDTO.getNickname());
        userController.showGrade(userDTO);

        String message = "1. 등급 수정 2. 탈퇴 3. 뒤로가기";
        int userChoice = nextInt(scanner, message, 1, 3);
        if (userChoice == 1) {
            message = "1. 일반 회원으로 2. 전문 평론가로 3. 관리자로";
            int newGrade = nextInt(scanner, message, 1, 3);
            userDTO.setGrade(newGrade);
        }
        if (userChoice == 2) {
            message = "정말이 회원을 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                message = "비밀번호를 입력해주세요";
                String password = nextLine(scanner, message);
                if (password.equals(logIn.getPassword())) {
                    userController.delete(userDTO.getId());
                } else {
                    System.out.println("비밀번호가 틀렸습니다.");
                }
            } else {
                System.out.println("회원 탈퇴를 취소합니다.");
            }
        }
    }

    private void update() {
        String message = "새로운 닉네임을 입력해주세요";
        String newNickname = nextLine(scanner, message);
        message = "새로운 비밀번호를 입력해주세요";
        String newPassword = nextLine(scanner, message);
        message = "기존 비밀번호를 입력해주세요";
        String oldPassword = nextLine(scanner, message);

        if (oldPassword.equals(logIn.getPassword())) {
            logIn.setNickname(newNickname);
            logIn.setPassword(newPassword);

            userController.update(logIn);
        } else {
            System.out.println("기존 비밀번호와 달라서 수정할 수 없습니다");
        }
    }

    private void delete() {
        String message = "정말로 탈퇴하시겠습니가? Y/N";
        String answer = nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            message = "비밀번호를 입력해주세요";
            String password = nextLine(scanner, message);
            if (password.equals(logIn.getPassword())) {
                userController.delete(logIn.getId());
                logIn = null;
            }
        }
    }
}
