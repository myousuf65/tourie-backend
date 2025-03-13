package xyz.yousuf.tourie.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.yousuf.tourie.entity.UserModel;
import xyz.yousuf.tourie.repository.UserRepository;

@Service
public class UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
    private UserRepository userRepository;

    public boolean checkCredentials(UserModel user) {

        UserModel _user = userRepository.findByName(user.getName());

        if(encoder.matches(user.getPassword(),_user.getPassword())){
            return true;
        }else{
           return false;
        }

    }

    public UserModel createUser(UserModel user) throws Exception {

        if(userRepository.existsByName(user.getName())){
            throw new Exception("USER ALREADY EXISTS IN DATABASE");
        }else{
            System.out.println("ENCODING PASSWORD FOR USER");
            user.setPassword(encoder.encode(user.getPassword()));
            System.out.println("CREATED USER OBJECT" + user);
            return userRepository.save(user);
        }
    }


}
