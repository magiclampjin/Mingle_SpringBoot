package com.mingle.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mingle.domain.repositories.NewVideoRepository;
import com.mingle.dto.NewVideoDTO;
import com.mingle.mappers.NewVideoMapper;

import jakarta.transaction.Transactional;

@Service
public class NewVideoUpdateService {
	
	@Autowired
	private NewVideoRepository nvRepo;
	
	@Autowired
	private NewVideoAPIService nvAPIServ;
	
	@Autowired
	private NewVideoMapper nvMapper;
	

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void updateVideoData() throws IOException {
        List<NewVideoDTO> newVideos = nvAPIServ.getLatestVideosFromNetflixKorea();
        
        for (NewVideoDTO newVideo : newVideos) {
            if (!nvRepo.existsById(newVideo.getId())) {
                nvRepo.save(nvMapper.toEntity(newVideo));
            }
        }
    }
}
