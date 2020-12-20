package ru.asmisloff.eshop.shopdatabase.util;

import org.springframework.stereotype.Component;
import ru.asmisloff.eshop.shopdatabase.entities.Authority;
import ru.asmisloff.eshop.shopdatabase.entities.User;
import ru.asmisloff.eshop.shopdatabase.services.AuthorityService;
import ru.asmisloff.eshop.shopdatabase.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class SampleData {

    private UserService userService;
    private AuthorityService authorityService;

    public SampleData(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @PostConstruct
    public void init() {

        Authority admin = new Authority("ROLE_ADMIN");
        Authority manager = new Authority("ROLE_MANAGER");
        Authority customer = new Authority("ROLE_CUSTOMER");

        User user1 = new User("user1", "Пользователь №1", "user01@mail.org");
        User user2 = new User("user2", "Пользователь №2", "user02@mail.org");
        User user3 = new User("user3", "Пользователь №3", "user03@mail.org");
        User user4 = new User("user4", "Пользователь №4", "user04@mail.org");

        authorityService.saveAll(Arrays.asList(admin, manager, customer));

        user1.addAuthority(admin);
        user1.addAuthority(manager);
        Stream.of(user2, user3, user4).forEach(u -> u.addAuthority(manager));

        userService.saveAll(Arrays.asList(user1, user2, user3, user4));
    }

}
