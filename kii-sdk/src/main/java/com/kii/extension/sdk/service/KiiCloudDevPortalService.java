package com.kii.extension.sdk.service;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kii.extension.sdk.entity.AppDetail;
import com.kii.extension.sdk.entity.AppInfo;
import com.kii.extension.sdk.entity.AppInfoEntity;
import com.kii.extension.sdk.impl.KiiCloudClient;
import com.kii.extension.sdk.impl.PortalApiAccessBuilder;

@Component
public class KiiCloudDevPortalService {


	@Autowired
	private KiiCloudClient client;


	@Autowired
	private ObjectMapper mapper;

	@Value("${portal.master.dev-portal.url}")
	private String devPortalUrl;

	HttpClientContext context;

	CookieStore cookieStore=new BasicCookieStore();

	@PostConstruct
	public void init(){


		context = HttpClientContext.create();
		context.setCookieStore(cookieStore);

		RequestConfig globalConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD)
				.build();
		context.setRequestConfig(globalConfig);
	}


	private PortalApiAccessBuilder getBuilder(){
		return new PortalApiAccessBuilder(devPortalUrl);

	}

	private void bindCookies(HttpUriRequest  request){

		String session=null;
		for(Cookie cookie:cookieStore.getCookies()){
			if(cookie.getName().equals("devportal_session")){
				session=cookie.getValue();
			}
		}

		request.setHeader("Cookie", "devportal_session="+session);

	}



	public void login(String user,String pwd){


		try {
			user= URLEncoder.encode("steven.jiang@kii.com", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}

		HttpUriRequest request=getBuilder().buildLogin(user,pwd).generRequest();

		client.doRequest(request, context);

	}




	public AppDetail getAppInfoDetail(String appInfoID){

		HttpUriRequest request= getBuilder().buildAppDetail(appInfoID).generRequest();

		bindCookies(request);

		String result= client.executeRequest(request);


		try {
			return  mapper.readValue(result,AppDetail.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}


	}


	public List<AppInfo>  getAppInfoList(){

		List<AppInfoEntity> list=getAppList();

		List<AppInfo> infoList=new ArrayList<>();
		for(AppInfoEntity  entity:list){

			AppDetail detail=getAppInfoDetail(entity.getId());
			infoList.add(detail.getAppInfo());
		}

		return infoList;
	}

	public List<AppInfoEntity> getAppList(){

		HttpUriRequest request= getBuilder().buildAppList().generRequest();

		bindCookies(request);

		String result= client.executeRequest(request);


		try {
			return  mapper.readValue(result,new TypeReference<List<AppInfoEntity>>(){}  );
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}


}
