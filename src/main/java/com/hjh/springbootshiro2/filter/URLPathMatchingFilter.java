package com.hjh.springbootshiro2.filter;

import com.hjh.springbootshiro2.service.PermissionService;
import com.hjh.springbootshiro2.utils.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {
	@Autowired
	PermissionService permissionService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if(null==permissionService) 
			permissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
		
		String requestURI = getPathWithinApplication(request);
		System.out.println("requestURI:" + requestURI);

		Subject subject = SecurityUtils.getSubject();
		// 如果没有登录和没有记住密码，就跳转到登录页面
		if (!subject.isAuthenticated()&&!subject.isRemembered()) {
			WebUtils.issueRedirect(request, response, "/login");
			return false;
		}

		// 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
		System.out.println("permissionService:"+permissionService);
		boolean needInterceptor = permissionService.needInterceptor(requestURI);
		if (!needInterceptor) {
			return true;
		} else {
			boolean hasPermission = false;
			String userName = subject.getPrincipal().toString();
			Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
			for (String url : permissionUrls) {
				// 这就表示当前用户有这个权限
				if (url.equals(requestURI)) {
					hasPermission = true;
					break;
				}
			}

			if (hasPermission)
				return true;
			else {
				UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
				subject.getSession().setAttribute("message", ex.getMessage());

				WebUtils.issueRedirect(request, response, "/unauthorized");
				return false;
			}

		}

	}
}