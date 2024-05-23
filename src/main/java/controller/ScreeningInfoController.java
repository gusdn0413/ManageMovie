package controller;

import model.ScreeningInfoDTO;

import java.util.ArrayList;
import java.util.Iterator;

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

    public void delete(int id) {
        Iterator<ScreeningInfoDTO> iterator = list.iterator();
        while (iterator.hasNext()) {
            ScreeningInfoDTO screeningInfoDTO = iterator.next();
            if (screeningInfoDTO.getId() == id) {
                iterator.remove();
            }
        }
    }

    public ScreeningInfoDTO selectOne(int id) {
        for (ScreeningInfoDTO screeningInfoDTO : list) {
            if (screeningInfoDTO.getId() == id) {
                return screeningInfoDTO;
            }
        }
        return null;
    }
}
