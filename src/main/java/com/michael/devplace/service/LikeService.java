package com.michael.devplace.service;

import com.michael.devplace.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository; // Like 엔티티의 Repository


}
