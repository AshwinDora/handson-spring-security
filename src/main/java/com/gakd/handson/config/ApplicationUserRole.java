package com.gakd.handson.config;

import static com.gakd.handson.config.ApplicationUserPermission.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
    STUDENT(new HashSet<ApplicationUserPermission>()),
    ADMIN(getAllAdminPermission()),
    ADMIN_TRAINEE(getAllAdminTraineePermission());

    private static Set<ApplicationUserPermission> getAllAdminPermission() {
        Set<ApplicationUserPermission> permissions = new HashSet();
        Collections.addAll(permissions, ApplicationUserPermission.values());
        return permissions;
    }

    private static Set<ApplicationUserPermission> getAllAdminTraineePermission() {
        Set<ApplicationUserPermission> adminTraineePermissions = new HashSet();
        ApplicationUserPermission[] permissions = new ApplicationUserPermission[]{COURSE_READ, STUDENT_READ};
        Collections.addAll(adminTraineePermissions, permissions);
        return adminTraineePermissions;
    }

    private Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> ga = this.permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        ga.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return ga;
    }


}
