package com.hnys.bcd.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Message extends Remote {
    public String hello() throws RemoteException;
}
