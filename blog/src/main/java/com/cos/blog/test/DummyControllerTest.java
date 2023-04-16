package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

// html파일이 아니라 data를 return해주는 controller = RestController
@RestController
public class DummyControllerTest {
    @Autowired  // Autowired : DummyControllerTest가 메모리에 뜰 때 userRepository도 같이 뜨게 해줌. = 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/delete/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 ID는 DB에 없습니다.";
        }
        return "삭제되었습니다." + id;
    }

    //save 함수는 id를 전달하지 않으면 insert를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
    //email, password 수정
    // json 데이터를 요청 -> Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아준다. = @RequestBody
    @Transactional  // 함수 종료시 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
        System.out.println("id : "+ id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        //userRepository.save(user);

        // 더티 체킹
        // 변경감지 -> DB수정
        return user;

    }

    //localhost:8080/dummy/user
    @GetMapping("/dummy/user")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한 페이지당 두 건의 데이터 리턴 size가 리턴 받을 데이터 수
    //localhost:8080/dummy/user/page
    @GetMapping("/dummy/user/page")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction= Sort.Direction.DESC) Pageable pageable) {
       Page<User> pagingUser = userRepository.findAll(pageable);
       List<User> users = pagingUser.getContent();

        // List<User> users = userRepository.findAll(pageable).getContent();
        //  Page<User> users = userRepository.findAll(pageable) 함수타입 = Page<User> = 더 자세한 정보 줌
        return users;
    }

    //localhost:8080/dummy/user/3 <- 3이 ID로 들어옴
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user/4를 찾을때 내가 DB에서 못 찾아오면 user가 null이 될것이니까
        // 그럼 return null이 되잖아.. 그럼 프로그램에 문제가 있지 않겠니?
        // Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        // 요청 : 웹 브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹 브라우저가 이해할 수 있는 데이터) -> json
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동적용
        // 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
        return user;
    }
        // 람다식 version 상당히 간단해진다.
//       User user = userRepository.findById(id).orElseThrow(()->
//                    return new IllegalArgumentException("해당 사용자는 없습니다.");
//        });
//            return user;
//    }




    // http://localhost:8080/dummy/join (요청)
    // http의 body에 username, password, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    public String join(User user) {    // key:value (규칙)
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
