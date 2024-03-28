package kr.kakaocloud.kakeulgae.controller;

import kr.kakaocloud.kakeulgae.security.LoginUserId;
import kr.kakaocloud.kakeulgae.service.BookmarkService;
import kr.kakaocloud.kakeulgae.service.dto.bookmark.BookmarkListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/register/{id}")
    public ResponseEntity<String> handleBookmarkRequest(@LoginUserId Long userId,
        @PathVariable(value = "id") Long postId) { // 즐겨찾기 등록 API -> {id}는 공고 id
        try {
            bookmarkService.registerBookmark(userId, postId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 공고입니다");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> handleBookmarkDelete(@LoginUserId Long userId,
        @PathVariable(value = "id") Long postId) { // 즐겨찾기 삭제 API -> {id}는 공고 id
        try {
            bookmarkService.deleteBookmark(userId, postId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 공고입니다");
        }
    }

    @GetMapping("/likes") // 즐겨찾기 조회 API -> 토큰을 통해 사용자 식별하고 페이지네이션을 활용하여 조회
    public Slice<BookmarkListDto> getMyBookmark(@LoginUserId Long id, Pageable pageable) {
        return bookmarkService.getSliceBookmarkData(id, pageable);
    }

    @GetMapping("/search")
    public Slice<BookmarkListDto> getSearchBookmark(@LoginUserId Long id,
        @RequestParam(value = "keyword") String keyword, Pageable pageable) { // 검색 API
        return bookmarkService.getSliceSearchBookmarkData(id, keyword, pageable);
    }
}
