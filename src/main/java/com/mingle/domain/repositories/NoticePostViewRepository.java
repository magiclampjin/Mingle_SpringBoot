package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.NoticePostView;

public interface NoticePostViewRepository extends JpaRepository<NoticePostView, Long>{

	// 최신 10개의 게시물을 rownum 기준으로 내림차순으로 가져오는 쿼리
    @Query("SELECT npv FROM NoticePostView npv ORDER BY npv.rownum DESC")
    List<NoticePostView> findTop10ByOrderByRownumDescNoticePostView(Pageable pageable);	
    
    @Query("SELECT npv FROM NoticePostView npv ORDER BY npv.rownum DESC")
    List<NoticePostView> findAllNoticePostView();
}
