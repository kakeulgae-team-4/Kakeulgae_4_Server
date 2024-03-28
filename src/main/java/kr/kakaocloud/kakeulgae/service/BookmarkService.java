package kr.kakaocloud.kakeulgae.service;

import java.util.NoSuchElementException;
import kr.kakaocloud.kakeulgae.service.dto.bookmark.BookmarkListDto;
import kr.kakaocloud.kakeulgae.domain.entity.Bookmark;
import kr.kakaocloud.kakeulgae.repository.JobPostingRepository;
import kr.kakaocloud.kakeulgae.repository.BookmarkRepository;
import kr.kakaocloud.kakeulgae.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MemberRepository memberRepository;

    public void registerBookmark(Long userId, Long postId) {
        if (!bookmarkRepository.existsByMemberIdAndJobPostingId(userId, postId)) {
            Bookmark bookmark = Bookmark.builder()
                .member(memberRepository.findById(userId).orElseThrow(() ->
                    new NoSuchElementException("해당 유저가 존재하지 않습니다")))
                .jobPosting(jobPostingRepository.findById(postId).orElseThrow(() ->
                    new NoSuchElementException("해당 공고글이 존재하지 않습니다")))
                .build();
            bookmarkRepository.save(bookmark);
        }
    }

    public void deleteBookmark(Long userId, Long postId) {
        Bookmark existingBookmark = bookmarkRepository.findByMemberIdAndJobPostingId(userId,
            postId);
        if (existingBookmark != null) {
            bookmarkRepository.delete(existingBookmark);
        }
    }

    public Slice<BookmarkListDto> getSliceBookmarkData(Long id,
        Pageable pageable) { // 사용자가 찜한 공고 정보를 담아 Slice 객체에 삽입
        Slice<BookmarkListDto> bookmarkListDtos = jobPostingRepository.findJobPostingIdsByUserIdToSlice(
                id, pageable)
            .map(BookmarkListDto::new);
        return bookmarkListDtos;
    }

    public Slice<BookmarkListDto> getSliceSearchBookmarkData(Long id, String keyword, Pageable pageable) {
        Slice<BookmarkListDto> bookmarkListSearchDtos = jobPostingRepository.findBySearchBookmarkData(id, keyword, pageable)
            .map(BookmarkListDto::new);
        return bookmarkListSearchDtos;
    }
}
