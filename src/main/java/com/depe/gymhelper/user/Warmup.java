package com.depe.gymhelper.user;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component("userWarmup")
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;

    Warmup(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(!roleRepository.existsByType(RoleType.USER)){
            roleRepository.save(new Role(RoleType.USER));
        }
        if(!roleRepository.existsByType(RoleType.ADMIN)){
            roleRepository.save(new Role(RoleType.ADMIN));
        }
    }
}
