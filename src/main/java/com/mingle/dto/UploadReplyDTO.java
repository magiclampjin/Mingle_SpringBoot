package com.mingle.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder // 클래스 레벨에서 빌더 어노테이션 추가
public class UploadReplyDTO {
    
    private Long id;
    private String content;
    private Instant writeDate;
    private Long postId;
    private String memberId;
    private Long replyParentId; // null 허용
    private Long replyAdoptiveParentId; // null 허용

    // 빌더를 위한 생성자는 하나만 있어야 하며, 모든 필드를 포함해야 합니다.
    @Builder
    public UploadReplyDTO(Long id, String content, Instant writeDate, Long postId, String memberId, Long replyParentId, Long replyAdoptiveParentId) {
        this.id = id;
        this.content = content;
        this.writeDate = writeDate;
        this.postId = postId;
        this.memberId = memberId;
        this.replyParentId = replyParentId;
        this.replyAdoptiveParentId = replyAdoptiveParentId;
    }
}
