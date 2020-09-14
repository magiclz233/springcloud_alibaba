package com.magic.granter;

import com.magic.token.MobileCodeAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义grant_type模式-手机号短信验证模式
 *
 * @author magic_lz
 * @version 1.0
 * @classname MobileCodeTokenGranter
 * @date 2020/9/14 : 17:03
 */
public class MobileCodeTokenGranter extends AbstractTokenGranter {

    public static final String GRANT_TYPE = "mobile";

    private final AuthenticationManager authenticationManager;

    public MobileCodeTokenGranter(AuthenticationManager authenticationManager,
                                  AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                  OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    private MobileCodeTokenGranter(AuthenticationManager authenticationManager,
                                   AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                                   OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String,String> parameters = new LinkedHashMap<>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        String code = parameters.get("code");
        Authentication authentication = new MobileCodeAuthenticationToken(mobile, code);
        ((AbstractAuthenticationToken)authentication).setDetails(parameters);
        try {
            authentication =authenticationManager.authenticate(authentication);
        }catch (AccountStatusException | BadCredentialsException ase) {
            // AccountSt    atusException covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            // BadCredentialsException If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(ase.getMessage());
        }
        if(authentication == null || !authentication.isAuthenticated()){
            throw new InvalidGrantException("Could not authenticate mobile: " + mobile);
        }
        OAuth2Request oAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(oAuth2Request,authentication);
    }
}
