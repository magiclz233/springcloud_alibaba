package com.magic.service;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * OAuth客户端服务
 *
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/16 21:25
 */
@Service
public class SysClientDetailsService {

    public BaseClientDetails selectById(String clientId) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setAuthorities(new ArrayList<>());
        clientDetails.setClientId("magic");
        // 这个客户端秘钥和密码一样存BCryptPasswordEncoder加密后的接口，具体看定义的加密器
        clientDetails.setClientSecret("$2a$10$WXF5lxbSGw0766M4h5DPO.gQdLbukHCG31qOo/LNlQNGnyaiGkWj2");
        // 设置accessToken和refreshToken的时效，如果不设置则使tokenServices的配置的
        clientDetails.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(2));
        clientDetails.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));
        // 资源id列表，需要注意的是这里配置的需要与ResourceServerConfig中配置的相匹配
        List<String> resourceIds = new ArrayList<>();
        resourceIds.add("auth-server");
        resourceIds.add("resource-server");
        clientDetails.setResourceIds(resourceIds);
        List<String> scope = new ArrayList<>(1);
        scope.add("sever");
        clientDetails.setScope(scope);
        List<String> grantTypes = new ArrayList<>();
        grantTypes.add("password");
        grantTypes.add("refresh_token");
        grantTypes.add("authorization_code");
        grantTypes.add("implicit");
        grantTypes.add("mobile");
        clientDetails.setAuthorizedGrantTypes(grantTypes);
        Set<String> sets = new HashSet<>(1);
        sets.add("http://www.baidu.com");
        clientDetails.setRegisteredRedirectUri(sets);
        List<String> autoApproveScopes = new ArrayList<>(1);
        autoApproveScopes.add("sever");
        // 自动批准作用于，授权码模式时使用，登录验证后直接返回code，不再需要下一步点击授权
        clientDetails.setAutoApproveScopes(autoApproveScopes);
        return clientDetails;
    }
}
