package ru.friendlyevil.profiler.configuration;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.friendlyevil.profiler.aspect.ProfilerAspect;

@Configuration
@ComponentScan("ru.friendlyevil.profiler")
public class ContextConfiguration {

    @Bean
    public ProfilerAspect profilerAspect() {
        return Aspects.aspectOf(ProfilerAspect.class);
    }
}
