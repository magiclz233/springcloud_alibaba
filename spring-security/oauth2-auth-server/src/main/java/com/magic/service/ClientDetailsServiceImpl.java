package com.magic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/16 21:24
 */
@Service
@Slf4j
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final SysClientDetailsService clientDetailsService;

    public ClientDetailsServiceImpl(SysClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.info("客户端查询 : {}", clientId);
        BaseClientDetails clientDetails = clientDetailsService.selectById(clientId);
        if(clientDetails == null){
            throw new NoSuchClientException("没有找到该clientId : "+clientId);
        }
        return clientDetails;
    }
}
