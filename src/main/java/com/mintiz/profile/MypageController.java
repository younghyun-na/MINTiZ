package com.mintiz.profile;

import com.mintiz.domain.Post;
import com.mintiz.domain.User;
import com.mintiz.file.FileStore;
import com.mintiz.user.service.LoginService;
import com.mintiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MypageController {

    private final UserService userService;
    private final LoginService loginService;
    private final Long userId = 1L;


    @GetMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request){

        User loginUser = loginService.getLoginUser(request);
        getMint(model, loginUser.getId());

        log.info("loginuser ={}", loginUser.getId());

        return "MyPage";
    }

    @GetMapping("/writing")
    public String writing(Model model){
        return "WritingList";
    }

    @GetMapping("/bookmark")
    public String bookmark(Model model){
        return "BookMark";
    }

    @GetMapping("/profile/{userId}")
    public String profile(@PathVariable Long userId, Model model){
        User user = userService.findUser(userId);
        getMint(model, userId);
        return "Profile";
    }


    @Transactional
    public void getMint(Model model, Long userId) {
        User user = userService.findUser(userId);

        model.addAttribute("user", user);
        List<Post> posts = user.getPosts();

        List<Integer> postMonth = new ArrayList<>();
        List<Integer> postDate = new ArrayList<>();
        List<Integer> postYear = new ArrayList<>();

        for (Post post : posts) {
            LocalDateTime createdDate = post.getCreatedAt();
            postMonth.add(createdDate.getMonth().getValue());
            postDate.add(createdDate.getDayOfMonth());
            postYear.add(createdDate.getYear());
        }

        model.addAttribute("postMonth", postMonth);
        model.addAttribute("postDate", postDate);
        model.addAttribute("postYear", postYear);
    }



}
