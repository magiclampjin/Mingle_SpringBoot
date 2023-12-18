package com.mingle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostFileDTO {
	
	private Long id;
	private String oriName;
	private String sysName;
	private Long postId;
	
	@Builder
	public PostFileDTO(Long id, String oriName, String sysName, Long postId) {
		super();
		this.id = id;
		this.oriName = oriName;
		this.sysName = sysName;
		this.postId = postId;
	}
	
	
	

}
