package com.immortals.notesapp.audit;

import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

//        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(User.class::cast)
//                .map(User::getUsername)
//                .orElse("Unknown-User");
        String currentUser = "3";

        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        audit.setUser(currentUser);

    }
}
