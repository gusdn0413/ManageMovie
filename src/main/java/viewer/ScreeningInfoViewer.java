package viewer;

import controller.MovieController;
import controller.ScreeningInfoController;
import lombok.Setter;
import model.ScreeningInfoDTO;
import model.UserDTO;

import java.util.ArrayList;
import java.util.Scanner;

import static util.ScannerUtil.*;
import static util.ScannerUtil.nextLine;

public class ScreeningInfoViewer {

    @Setter
    private Scanner scanner;
    @Setter
    private ScreeningInfoController screeningInfoController;
    @Setter
    private UserDTO logIn;
    @Setter
    private MovieController movieController;

    public void showIndex(int theaterId) {
        while (true) {
            if (logIn.getGrade() == 3) {
                String message = "1. 상영 정보 등록 2. 상영 정보 보기 3. 돌아가기";
                int userChoice = nextInt(scanner, message, 1, 3);
                if (userChoice == 1) {
                    register(theaterId);
                } else if (userChoice == 2) {
                    printList(theaterId);
                } else if (userChoice == 3) {
                    System.out.println("돌아갑니다");
                    break;
                }
            } else {
                printList(theaterId);
                String message = "다시 보시겠습니까? Y/N";
                String answer = nextLine(scanner, message);
                if (answer.equalsIgnoreCase("Y")) {

                } else if (answer.equalsIgnoreCase("N")) {
                    System.out.println("돌아갑니다");
                    break;
                }
            }
        }
    }

    private void register(int theaterId) {
        System.out.println("극장 번호 : " + theaterId);
        String message = "영화 번호 : ";
        int movieId = nextInt(scanner, message);
        message = "상영 시간 : ";
        String screenTime = nextLine(scanner, message);

        ScreeningInfoDTO screeningInfoDTO = new ScreeningInfoDTO();
        screeningInfoDTO.setTheaterId(theaterId);
        screeningInfoDTO.setMovieId(movieId);
        screeningInfoDTO.setScreenTime(screenTime);

        screeningInfoController.insert(screeningInfoDTO);

    }

    private void printList(int theaterId) {
        ArrayList<ScreeningInfoDTO> list = screeningInfoController.selectAllByTheaterId(theaterId);
        for (ScreeningInfoDTO screeningInfoDTO : list) {
            System.out.println("영화 제목 : " + movieController.selectOne(screeningInfoDTO.getMovieId()).getTitle());
            System.out.println("상영 시간 : " + screeningInfoDTO.getScreenTime());
            System.out.println("=========================");
        }
        if (list.isEmpty()) {
            System.out.println("상영중인 영화가 없습니다.");
        }
        if (logIn.getGrade() == 3) {
            while (true) {
                String message = "1. 영화 수정 2. 영화 삭제 3. 돌아가기";
                int userChoice = nextInt(scanner, message,1,3);
                if (userChoice == 1) {
                    update(theaterId);
                } else if (userChoice == 2) {
                    delete(theaterId);
                } else if (userChoice == 3) {
                    System.out.println("돌아갑니다");
                    break;
                }
            }
        }
    }

    private void update(int theaterId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "새로운 영화 번호를 입력해주세요";
            int newMovieId = nextInt(scanner, message);
            message = "새로운 상영 시간을 입력해주세요";
            String newScreenTime = nextLine(scanner, message);

            ScreeningInfoDTO screeningInfoDTO = new ScreeningInfoDTO();
            screeningInfoDTO.setMovieId(newMovieId);
            screeningInfoDTO.setScreenTime(newScreenTime);

            screeningInfoController.update(screeningInfoDTO);
        } else {
            System.out.println("비밀번호가 틀렸습니다.");
        }
    }

    private void delete(int theaterId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "정말 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                screeningInfoController.delete(theaterId);
                System.out.println("삭제되었습니다.");
            } else {
                System.out.println("삭제를 취소합니다");
            }
        } else {
            System.out.println("비밀번호가 틀렸습니다");
        }
    }
}
