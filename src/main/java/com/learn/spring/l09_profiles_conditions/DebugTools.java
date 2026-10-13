package com.learn.spring.l09_profiles_conditions;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Profile expressions: "!prod" = every profile except prod. Also supported: "dev & cloud", "dev | test". */
@Component
@Profile("!prod")
public class DebugTools {
    public String dumpState() {
        return "debug info (never available in prod)";
    }
}
