package com.learn.spring.l09_profiles_conditions;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** Active for "dev", and also when NO profile is set (the special "default" profile). */
@Service
@Profile({"dev", "default"})
public class LocalDiskStorage implements StorageService {
    @Override
    public String store(String fileName) {
        return "saved " + fileName + " to /tmp/uploads";
    }
}
