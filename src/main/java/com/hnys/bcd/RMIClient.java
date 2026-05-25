package com.hnys.bcd;

import com.hnys.bcd.client.Message;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            String[] list = registry.list();

            for (String s : list) {
                System.out.println(s);
            }

            Message message = (Message) registry.lookup("message_service");
            String msg = message.hello();

            System.out.println(msg);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
