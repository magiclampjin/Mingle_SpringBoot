package com.mingle.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    // YouTube 서비스 초기화 메소드
    private YouTube initializeYouTube() {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        return new YouTube.Builder(httpTransport, jsonFactory, new HttpRequestInitializer() {
            public void initialize(HttpRequest request) {
                // 초기화 코드
            }
        }).setApplicationName("youtube-cmdline-search-sample").build();
    }

    // 검색 쿼리 실행 및 결과 처리 메소드
    private List<NewVideoDTO> executeSearchQuery(String query, String channelId, String ottName) throws IOException {
        YouTube youtube = initializeYouTube();

        YouTube.Search.List search = youtube.search().list("id,snippet");
        search.setKey(youtubeApiKey);
        search.setQ(query);
        search.setChannelId(channelId);
        search.setType("video");
        search.setOrder("date");
        search.setMaxResults(10L);

        List<SearchResult> searchResultList = search.execute().getItems();
        List<NewVideoDTO> newVideos = new ArrayList<>();
        for (SearchResult searchResult : searchResultList) {
            NewVideoDTO newVideo = new NewVideoDTO();

            String id = searchResult.getId().getVideoId();
            newVideo.setId(id);
            newVideo.setTitle(searchResult.getSnippet().getTitle());
            newVideo.setDescription(searchResult.getSnippet().getDescription());
            newVideo.setUrl("https://www.youtube.com/watch?v=" + id);
            newVideo.setThumbnail("https://i1.ytimg.com/vi/" + id + "/mqdefault.jpg");
            newVideo.setOtt(ottName);

            YouTube.Videos.List videoRequest = youtube.videos().list("snippet,statistics");
            videoRequest.setId(id);
            videoRequest.setKey(youtubeApiKey);
            Video video = videoRequest.execute().getItems().get(0);

            newVideo.setViewCount(video.getStatistics().getViewCount().longValue());
            newVideo.setLikeCount(video.getStatistics().getLikeCount().longValue());

            newVideos.add(newVideo);
        }

        return newVideos;
    }

    public List<NewVideoDTO> getLatestVideosFromNetflixKorea() throws IOException {
        return executeSearchQuery("공식 예고편", "UCiEEF51uRAeZeCo8CJFhGWw", "넷플릭스");
    }

    public List<NewVideoDTO> getLatestVideosFromWatcha() throws IOException {
        return executeSearchQuery("공식 예고편", "UCgmmc51A3qyAR3MvVX-rzCQ", "왓챠");
    }

    public List<NewVideoDTO> getLatestVideosFromTving() throws IOException {
        return executeSearchQuery("공식 예고편", "UCNIiH_4ArJNd_cDZApZ7AFg", "티빙");
    }

    public List<NewVideoDTO> getLatestVideosFromWavve() throws IOException {
        return executeSearchQuery("공식 예고편", "UCym5538xAEEppbridXozfgw", "웨이브");
    }
}

