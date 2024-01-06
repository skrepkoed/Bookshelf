package com.bookshelf.bookshelf_project.security_policy;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext>  {
    @Override
    public AuthorizationDecision check(Supplier authenticationSupplier, RequestAuthorizationContext ctx) {
        Long userId = Long.parseLong(ctx.getVariables().get("userId"));
        Authentication authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, userId));
    }
    public boolean hasUserId(Authentication authentication, Long userId) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (customUserDetails.getId().equals(userId)) {
            return true;
        }
        return false;
    }
}
