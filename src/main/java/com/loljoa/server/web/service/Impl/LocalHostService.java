package com.loljoa.server.web.service.Impl;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class LocalHostService {

    public String getLocalHostInfo() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return String.format("host-address: %s host-name: %s",
                    localhost.getHostAddress(),
                    localhost.getHostName());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
