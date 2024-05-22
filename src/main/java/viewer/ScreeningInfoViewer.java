package viewer;

import controller.MovieController;
import controller.ScreeningInfoController;
import lombok.Setter;
import model.MovieDTO;
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
                break;

            }
        }
    }

    private void register(int theaterId) {
        System.out.println("극장 번호 : " + theaterId);
        String message = "영화 번호 : ";
        int movieId = nextInt(scanner, message);
        MovieDTO movieDTO = movieController.selectOne(movieId);
        if (movieDTO == null) {
            System.out.println("해당 번호의 영화가 없습니다.");
        } else {
            message = "상영 시간 : ";
            String screenTime = nextLine(scanner, message);

            ScreeningInfoDTO screeningInfoDTO = new ScreeningInfoDTO();
            screeningInfoDTO.setTheaterId(theaterId);
            screeningInfoDTO.setMovieId(movieId);
            screeningInfoDTO.setScreenTime(screenTime);

            screeningInfoController.insert(screeningInfoDTO);
        }

    }

    private void printList(int theaterId) {
        boolean loop = false;
        ArrayList<ScreeningInfoDTO> list = screeningInfoController.selectAllByTheaterId(theaterId);
        for (ScreeningInfoDTO screeningInfoDTO : list) {
            if (screeningInfoDTO != null) {
                MovieDTO movieDTO = movieController.selectOne(screeningInfoDTO.getMovieId());
                if (movieDTO == null) {
                    loop = false;
                } else {
                    System.out.println("상영 정보 번호 : " + screeningInfoDTO.getId());
                    System.out.println("영화 번호 : " + movieDTO.getId());
                    System.out.println("영화 제목 : " + movieDTO.getTitle());
                    System.out.println("상영 시간 : " + screeningInfoDTO.getScreenTime());
                    System.out.println("=========================");
                    loop = true;
                }
            }
        }
        if (loop) {
            if (logIn.getGrade() == 3) {
                String message = "상영정보를 수정하시려면 상영정보의 번호를 누르시고 뒤로가시려면 0을 눌러주세요";
                int userChoice = nextInt(scanner, message);
                while (!screeningInfoController.validateInput(userChoice)) {
                    System.out.println("잘못 입력하셨습니다.");
                    userChoice = nextInt(scanner, message);
                }
                if (userChoice != 0) {
                    printOne(userChoice);
                }
            }
        } else {
            System.out.println("등록된 상영정보가 없습니다");
        }
    }

    private void printOne(int screenId) {
        String message = "1. 수정 2. 삭제 3. 돌아가기";
        int userChoice = nextInt(scanner, message, 1, 3);
        if (userChoice == 1) {
            update(screenId);
        } else if (userChoice == 2) {
            delete(screenId);
        } else if (userChoice == 3) {
            System.out.println("돌아갑니다");
        }
    }

    private void update(int screenId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "새로운 상영 시간을 등록해주세요";
            String newScreenTime = nextLine(scanner, message);
            ScreeningInfoDTO screeningInfoDTO = screeningInfoController.selectOne(screenId);
            screeningInfoDTO.setScreenTime(newScreenTime);
            screeningInfoController.update(screeningInfoDTO);
            System.out.println("등록되었습니다");
        } else {
            System.out.println("비밀번호가 틀렸습니다.");
        }
    }

    private void delete(int screenId) {
        String message = "비밀번호를 입력해주세요";
        String password = nextLine(scanner, message);
        if (password.equals(logIn.getPassword())) {
            message = "정말 삭제하시겠습니까? Y/N";
            String answer = nextLine(scanner, message);
            if (answer.equalsIgnoreCase("Y")) {
                screeningInfoController.delete(screenId);
                System.out.println("삭제되었습니다.");
            } else {
                System.out.println("삭제를 취소합니다");
            }
        } else {
            System.out.println("비밀번호가 틀렸습니다");
        }
    }
}
