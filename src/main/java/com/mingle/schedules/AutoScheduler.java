package com.mingle.schedules;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mingle.domain.repositories.NewVideoRepository;
import com.mingle.dto.NewVideoDTO;
import com.mingle.mappers.NewVideoMapper;
import com.mingle.services.NewVideoAPIService;

import jakarta.transaction.Transactional;

@Component
public class AutoScheduler {

//	함수명은 "기능Scheduler"로 통일 ex. 매일매일 휴지통에서 30일이 경과된 데이터를 비우는 스케쥴러
//	@Scheduled(cron = "0 0 0 * * *")
//	public void TrashScheduler() throws Exception {
//		sservice.autoDeleteInTrash();
//		abservice.autoDeleteInTrash();
//	}
	
	@Autowired
	private NewVideoRepository nvRepo;
	
	@Autowired
	private NewVideoAPIService nvAPIServ;
	
	@Autowired
	private NewVideoMapper nvMapper;
	

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void updateNetflixVideoData() throws IOException {
        List<NewVideoDTO> newVideos = nvAPIServ.getLatestVideosFromNetflixKorea();
        
        for (NewVideoDTO newVideo : newVideos) {
            if (!nvRepo.existsById(newVideo.getId())) {
                nvRepo.save(nvMapper.toEntity(newVideo));
            }
        }
    }
    
    @Scheduled(cron = "0 1 12 * * ?")
    @Transactional
    public void updateTvingVideoData() throws IOException {
        List<NewVideoDTO> newVideos = nvAPIServ.getLatestVideosFromTving();
        
        for (NewVideoDTO newVideo : newVideos) {
            if (!nvRepo.existsById(newVideo.getId())) {
                nvRepo.save(nvMapper.toEntity(newVideo));
            }
        }
    }
    
    @Scheduled(cron = "0 2 12 * * ?")
    @Transactional
    public void updateWavveVideoData() throws IOException {
        List<NewVideoDTO> newVideos = nvAPIServ.getLatestVideosFromWavve();
        
        for (NewVideoDTO newVideo : newVideos) {
            if (!nvRepo.existsById(newVideo.getId())) {
                nvRepo.save(nvMapper.toEntity(newVideo));
            }
        }
    }
    
    @Scheduled(cron = "0 3 12 * * ?")
    @Transactional
    public void updateWavvVideoData() throws IOException {
        List<NewVideoDTO> newVideos = nvAPIServ.getLatestVideosFromWatcha();
        
        for (NewVideoDTO newVideo : newVideos) {
            if (!nvRepo.existsById(newVideo.getId())) {
                nvRepo.save(nvMapper.toEntity(newVideo));
            }
        }
    }
	
}
