package com.mingle.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.mingle.dto.NewVideoDTO;

@Service
public class NewVideoAPIService {
	
    private String youtubeApiKey = System.getenv("GOOGLE_API_KEY");
	
	public List<NewVideoDTO> getLatestVideosFromNetflixKorea() throws IOException {
		
		HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	    // YouTube API 서비스 생성
	    YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
	        public void initialize(HttpRequest request) {
	            // 필요한 경우 초기화 코드 작성
	        }
	    }).setApplicationName("youtube-cmdline-search-sample").build();

	    // 넷플릭스 코리아 채널 ID
	    String channelId = "UCiEEF51uRAeZeCo8CJFhGWw";  // 실제 채널 ID로 교체해야 합니다.

	    // 검색 요청 설정
	    YouTube.Search.List search = youtube.search().list("id,snippet");
	    search.setKey(youtubeApiKey); // API 키 설정
	    search.setQ("공식 티저 예고편"); // 검색어 설정
	    search.setChannelId(channelId); // 채널 ID 설정
	    search.setType("video"); // 비디오만 검색
	    search.setOrder("date"); // 날짜 순으로 정렬
	    search.setMaxResults(4L); // 최대 결과 수 설정

	    // 검색 실행 및 결과 처리
	    List<SearchResult> searchResultList = search.execute().getItems();
	    List<NewVideoDTO> newVideos = new ArrayList<>();
	    for (SearchResult searchResult : searchResultList) {
	        NewVideoDTO newVideo = new NewVideoDTO();

	        String id = searchResult.getId().getVideoId();
	        newVideo.setId(id);
	        newVideo.setTitle(searchResult.getSnippet().getTitle());
	        newVideo.setDescription(searchResult.getSnippet().getDescription());
	        newVideo.setUrl("https://www.youtube.com/watch?v=" + id);
	        newVideo.setThumbnail("https://i1.ytimg.com/vi/"+id+"/mqdefault.jpg");

	        // 비디오 ID를 사용하여 비디오 객체 가져오기
	        YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics");
	        videoRequest.setId(id); // 비디오 ID 설정
	        videoRequest.setKey(youtubeApiKey); // API 키 설정
	        Video video = videoRequest.execute().getItems().get(0);

	        // 조회수와 좋아요 수 설정
	        newVideo.setViewCount(video.getStatistics().getViewCount().longValue());
	        newVideo.setLikeCount(video.getStatistics().getLikeCount().longValue());

	        newVideos.add(newVideo);
	    }

	    return newVideos;
	}


}
