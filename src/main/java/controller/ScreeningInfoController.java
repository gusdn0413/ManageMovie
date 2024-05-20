package controller;

import model.ScreeningInfoDTO;

import java.util.ArrayList;

public class ScreeningInfoController {

    private ArrayList<ScreeningInfoDTO> list;
    private int nextId;

    public ScreeningInfoController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public ArrayList<ScreeningInfoDTO> selectAllByTheaterId(int TheaterId) {
        ArrayList<ScreeningInfoDTO> result = new ArrayList<>();
        for (ScreeningInfoDTO screeningInfoDTO : list) {
            if (screeningInfoDTO.getTheaterId() == TheaterId) {
                result.add(screeningInfoDTO);
            }
        }
        return result;
    }

    public void insert(ScreeningInfoDTO screeningInfoDTO) {
        screeningInfoDTO.setId(nextId++);
        list.add(screeningInfoDTO);
    }

    public void update(ScreeningInfoDTO screeningInfoDTO) {
        list.set(list.indexOf(screeningInfoDTO), screeningInfoDTO);
    }

    public void delete(int theaterId) {
        list.remove(theaterId);
    }
}
