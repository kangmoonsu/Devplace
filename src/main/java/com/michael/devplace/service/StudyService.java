package com.michael.devplace.service;

import com.michael.devplace.dto.PostDTO;
import com.michael.devplace.dto.StudyDTO;
import com.michael.devplace.dto.UserDTO;
import com.michael.devplace.entity.PositionEntity;
import com.michael.devplace.entity.StudyEntity;
import com.michael.devplace.entity.TechEntity;
import com.michael.devplace.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudyService {
    @Autowired
    private StudyRepository studyRepository;

    public List<Map<String, Object>> recruitList() {
        List<Map<String,Object>> list = new ArrayList<>();
        List<StudyEntity> studyList = studyRepository.findAll();
        for (StudyEntity s: studyList) {
            Map<String, Object> map = new HashMap<>();
            StudyDTO studyDTO = StudyDTO.toStudyDTO(s);
            PostDTO postDTO = PostDTO.toPostDTO(s.getPostEntity());
            UserDTO userDTO = UserDTO.toUserDTO(s.getPostEntity().getUserEntity());

            List<TechEntity> techList = s.getTechEntityList();
            List<String> techStackList = techList.stream()
                    .map(TechEntity::getTech)
                    .collect(Collectors.toList());
            studyDTO.setTechStack(techStackList);
            List<PositionEntity> positionList = s.getPositionEntityList();
            List<String> positionNames = positionList.stream()
                    .map(PositionEntity::getPosition)
                    .collect(Collectors.toList());
            studyDTO.setPositions(positionNames);

            map.put("userDTO", userDTO);
            map.put("studyDTO",studyDTO);
            map.put("postDTO", postDTO);

            list.add(map);
        }
        return list;
    }

    public Map<String, Object> findById(Integer id) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
