package com.jy.gravatar.Configurations;

import com.jy.gravatar.Beans.Data;
import com.jy.gravatar.Beans.Role;
import com.jy.gravatar.Beans.User;
import com.jy.gravatar.Repository.DataRepository;
import com.jy.gravatar.Repository.RoleRepository;
import com.jy.gravatar.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    UserService userService;

    //This is moved to the user class to keep it consistent
    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public void run(String...strings) throws Exception{
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));

        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        //Constructors
        User user = new User("user@name.com", "password", "User", "Name", true, "user");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("system@admin.com", "password", "System", "Admin", true, "admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        Data data = new Data();
        data.setId(1);
        data.setName("Justin");
        data.setEmail("ymu@z.zgrco.com");
        data.setGravatar("https://s.gravatar.com/avatar/5623241a8751fef7598d621664854feb?s=80");
        dataRepository.save(data);

        data = new Data();
        data.setId(2);
        data.setName("Aaron");
        data.setEmail("lpr@s.rv55.com");
        data.setGravatar("https://s.gravatar.com/avatar/b1b191f1dad35861c0a90f4e188db81d?s=80");
        dataRepository.save(data);
    }
}
