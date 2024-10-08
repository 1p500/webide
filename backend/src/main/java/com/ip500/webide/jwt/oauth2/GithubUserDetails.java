package com.ip500.webide.jwt.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class GithubUserDetails implements OAuth2UserInfo {
    private Map<String, Object> attributes;
    @Override
    public String getProvider() {
        return "github";
    }
    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }
    @Override
    public String getEmail() {
        try{
            return attributes.get("email").toString();
        }catch(Exception e) {
            return getName() + getProviderId() + "@github.com";
        }
    }
    @Override
    public String getName() {
        return attributes.get("login").toString();
    }
}
