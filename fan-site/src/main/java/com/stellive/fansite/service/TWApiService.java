package com.stellive.fansite.service;

import com.stellive.fansite.client.TWUserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TWApiService {

    private final TWUserClient userClient;

    public void test() {
        userClient.getTWUser();
    }
}
