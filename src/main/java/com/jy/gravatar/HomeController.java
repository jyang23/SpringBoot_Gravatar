package com.jy.gravatar;

import com.jy.gravatar.Beans.Data;
import com.jy.gravatar.Configurations.UserService;
import com.jy.gravatar.Repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.*;
import java.security.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    DataRepository dataRepository;

    private AtomicLong theId = new AtomicLong();

    ArrayList<Data> allData = new ArrayList<>();

    //------------------------------------------------------------------------------------------------------------------
    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("datas",dataRepository.findAll());

        if(userService.getUser() != null)
        {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "index";
    }
    //-Issue------------------------------------------------------------------------------------------------------------
    @GetMapping("/add")
    public String issueForm(Model model)
    {
        model.addAttribute("data",new Data());
        return "issueform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Data data, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "issueform";
        }
        String hash = md5Hex(data.getEmail());
        hash = "https://www.gravatar.com/avatar/"+hash;
        data.setGravatar(hash);
        dataRepository.save(data);
        return "redirect:/";
    }
    //------------------------------------------------------------------------------------------------------------------
    /*
     * The following class will provide you with a static method
     * that returns the hex format md5 of an input string
     * See: http://en.gravatar.com/site/implement/images/java/
     * Call this as shown below:
     * String email = "someone@somewhere.com";
     * String hash = MD5Util.md5Hex(email);
     */

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
    //------------------------------------------------------------------------------------------------------------------
}
