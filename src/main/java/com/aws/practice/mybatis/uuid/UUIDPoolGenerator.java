package com.aws.practice.mybatis.uuid;

import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class UUIDPoolGenerator implements UUIDGenerator {

    @Override
    public String gain() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
