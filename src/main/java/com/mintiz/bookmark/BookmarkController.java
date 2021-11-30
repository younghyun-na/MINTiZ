package com.mintiz.bookmark;

import com.mintiz.bookmark.model.BookmarkReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/add")
    public boolean addBookmark(@RequestBody BookmarkReq bookmarkReq){
        return bookmarkService.addBookmark(bookmarkReq.getUserId(),bookmarkReq.getPostId());
    }
    @PostMapping("delete")
    private boolean deleteBookmark(@RequestBody BookmarkReq bookmarkReq){
        return bookmarkService.cancelBookmark(bookmarkReq.getUserId(), bookmarkReq.getPostId());
    }
}
