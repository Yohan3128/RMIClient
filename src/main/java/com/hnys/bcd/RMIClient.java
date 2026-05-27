package com.hnys.bcd;

import com.hnys.bcd.client.Message;
import com.hnys.bcd.client.UserService;
import com.hnys.bcd.model.Data;
import com.hnys.bcd.model.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Properties;

public class RMIClient {
    public static void main(String[] args) {
        try {
//            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//            String[] list = registry.list();
//
//            for (String s : list) {
//                System.out.println(s);
//            }
//
//            Message message = (Message) registry.lookup("message_service");
//            String msg = message.hello();
//            Data data = message.getData();
//
//            System.out.println(msg);
//            System.out.println(data.getId());
//            System.out.println(data.getName());
//
//            String result = message.getResult(10, 20);
//            System.out.println(result);


//            UserService userService = (UserService) registry.lookup("user_service");

//            UserService userService =(UserService) Naming.lookup("rmi://127.0.0.1:1099/user_service");

            Properties prop = new Properties();
            prop.put(Context.PROVIDER_URL, "rmi://127.0.0.1:1099");
            prop.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");

            InitialContext initialContext = new InitialContext(prop);
            UserService userService = (UserService) initialContext.lookup("user_service");


            userService.addUser(1,new User(1,"yohan","Minuwangoda","yohan@gmail.com"));
            userService.addUser(2,new User(2,"amal","Minuwangoda","amal@gmail.com"));
            userService.addUser(3,new User(3,"Kamal","Minuwangoda","Kamal@gmail.com"));
            User user = userService.getUser(1);
            System.out.println("User : "+user.getId() +" "+ user.getName() +" "+user.getAddress() + " " + user.getEmail());
            userService.deleteUser(1);
            List<User> allUsers = userService.getAllUsers();
            for (User u : allUsers) {
                System.out.println("All Users : "+u.getId() +" "+ u.getName() +" "+u.getAddress() + " " + u.getEmail());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
