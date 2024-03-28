package kr.kakaocloud.kakeulgae.repository;

import kr.kakaocloud.kakeulgae.domain.entity.JobPosting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

public interface JobPostingRepositorySearch {
    Slice<JobPosting> findBySearchBookmarkData(Long id, @Param("keyword") String keyword, Pageable pageable);
}