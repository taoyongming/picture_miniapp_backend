package web.service;

import web.DO.WechatLoginRequest;

/**
 * ${DESCRIPTION}
 *
 * @author tym
 * @ceeate 2019/11/14
 **/
public interface WechatService {

    String getUserInfoMap(WechatLoginRequest loginRequest) throws Exception;

}