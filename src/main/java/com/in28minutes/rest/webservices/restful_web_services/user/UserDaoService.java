package com.in28minutes.rest.webservices.restful_web_services.user;


import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Component
public class UserDaoService {

    private static List<User> users=new ArrayList<>();
    private static int userCount=0;
    static {
        users.add(new User(++userCount,"Rachel", LocalDate.now().minusYears(25)));
        users.add(new User(++userCount,"Phoebe", LocalDate.now().minusYears(30)));
        users.add(new User(++userCount,"Monica", LocalDate.now().minusYears(32)));

    }

    public List<User> findAll(){
        return users;
    }
    public User findById(Integer id){

        Predicate<? super User> predicate=user->user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public User addUser(User user) {
        user.setId(++userCount);
        users.add(user);
        return user;
    }

    public void deleteById(Integer id) {

        Predicate<? super User> predicate=user->user.getId().equals(id);
        users.removeIf(predicate);

    }
}
