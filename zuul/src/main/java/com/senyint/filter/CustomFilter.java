package com.senyint.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class CustomFilter extends ZuulFilter {

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		Object accessToken = request.getParameter("accessToken");
		// http://localhost:5555/test?a=2&accessToken=123
		System.out.println("123123123");
		if (accessToken == null) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			return null;
		}
		// 连接redis验证用户信息
		return null;
	}

	@Override
	public boolean shouldFilter() {
		System.out.println("shouldFilter");
		return true;
	}

	@Override
	public int filterOrder() {
		System.out.println("filterOrder");
		return 0;
	}

	@Override
	public String filterType() {
		System.out.println("filterType");
		return "pre";
	}

}
