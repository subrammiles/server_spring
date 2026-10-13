package com.learn.spring.l09_profiles_conditions;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class CloudStorage implements StorageService {
    @Override
    public String store(String fileName) {
        return "uploaded " + fileName + " to s3://company-bucket";
    }
}
