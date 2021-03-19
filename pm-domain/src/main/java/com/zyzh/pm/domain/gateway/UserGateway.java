package com.zyzh.pm.domain.gateway;

/**
 * @author wcy
 */
public interface UserGateway {
    boolean existForUsername(String username);

    boolean validPwd(String username, String password);
}
