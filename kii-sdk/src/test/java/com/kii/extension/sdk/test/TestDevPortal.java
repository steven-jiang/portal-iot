package com.kii.extension.sdk.test;

import static junit.framework.TestCase.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.kii.extension.sdk.entity.AppDetail;
import com.kii.extension.sdk.entity.AppInfoEntity;
import com.kii.extension.sdk.service.KiiCloudDevPortalService;

public class TestDevPortal extends TestTemplate {


	@Autowired
	private KiiCloudDevPortalService service;

	private String cookie="u8xAN0y1gMGoGPHEqRNYew00";

	@Test
	public void testLogin() throws UnsupportedEncodingException {



		service.login("steven.jiang@kii.com","qwerty");

		List<AppInfoEntity> list=service.getAppList();

		assertEquals(11, list.size());


	}

	@Test
	public void testAppInfos(){

		service.login("steven.jiang@kii.com", "qwerty");

		List<AppInfoEntity> list=service.getAppList();

		assertEquals(11,list.size());

		for(AppInfoEntity  entity:list){


			AppDetail detail=service.getAppInfoDetail(entity.getId());
			assertEquals(detail.getAppID(),entity.getAppID());
		}

	}
}
