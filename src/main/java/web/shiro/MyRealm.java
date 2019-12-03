package web.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm{



	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String username = (String) super.getAvailablePrincipal(principals);

		if (username != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();



			return info;
		} else {
			throw new AuthenticationException("请检查自己的用户名或密码!");
		}
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken tk) throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) tk;

		String username = token.getUsername();
		System.out.println("username="+username);


		return new SimpleAuthenticationInfo(null, null, null,
				getName());
	}


}