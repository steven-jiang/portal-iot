package com.kii.extension.sdk.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PortalApiAccessBuilder {


	private final String baseUrl;

	private HttpUriRequest request;


	public PortalApiAccessBuilder(String baseUrl){

		this.baseUrl=baseUrl;

	}

	String subUrl=null;

	Object ctxObj=null;

	public PortalApiAccessBuilder buildLogin(String userName,String pwd)  {

		String fullUrl=baseUrl+"/api/v1/login?cb_token=login&callback=http://localhost&email="+userName+"&password="+pwd;


		request=new HttpPost(fullUrl);
		request.addHeader("Referer","http://en.kii.com/login-developers");

		return this;
	}



	public PortalApiAccessBuilder buildAppList(){

		request=new HttpGet(baseUrl+"/v2api/apps");

		return this;
	}

	public PortalApiAccessBuilder buildAppDetail(String appID){

		request=new HttpGet(baseUrl+"/v2api/apps/"+appID);

		return this;
	}

	public HttpUriRequest generRequest(){

		return request;
	}

}
