package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.NicknameNoun;



public interface NounRepository extends JpaRepository<NicknameNoun, Long> {
	// 닉네임 명사 총 개수 불러오기
	@Query("select count(*) from NicknameNoun")
	Long selectNounCount();
}
