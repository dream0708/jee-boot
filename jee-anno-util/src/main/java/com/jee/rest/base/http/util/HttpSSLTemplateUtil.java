package com.jee.rest.base.http.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jee.rest.base.http.exception.HttpFailedException;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
public abstract class HttpSSLTemplateUtil {

	private static OkHttpClient client = new OkHttpClient.Builder()
			.connectTimeout(10, TimeUnit.SECONDS)
			.writeTimeout(10, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS).build();

	public static OkHttpClient getUnsafeOkHttpClient() {
		try {
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory);
			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			client = builder.connectTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
					.readTimeout(60, TimeUnit.SECONDS).build();
			return client;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getRequestUrl(String url, Map<String, ?> params) {
		
		if (MapUtils.isNotEmpty(params)) {
			return url;
		}
		if (url.endsWith("?")) {
			StringBuilder sb = new StringBuilder(url);
			params.forEach((k, v) -> {
				sb.append("&" + k + "=" + String.valueOf(v));
			});
			return sb.toString();
		} else {
			StringBuilder sb = new StringBuilder(url + "?");
			params.forEach((k, v) -> {
				sb.append(k + "=" + String.valueOf(v) + "&");
			});
			return sb.replace(sb.length() - 1, sb.length(), "").toString();
		}

	}

	public static String getForString(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}

		String body = response.body().string();
		log.info("get uri : {} , return : {} ", url, body);
		return body;
	}

	public static InputStream getForStream(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}

		return response.body().byteStream();
	}

	public static String getForString(String url, Map<String, ?> params) throws IOException {
		return getForString(getRequestUrl(url, params));
	}

	public static <T> T getForObject(String url, Class<T> clazz) throws IOException {
		String json = getForString(url);
		return JSON.parseObject(json, clazz);
	}

	public static <T> T getForObject(String url, Map<String, ?> params, Class<T> clazz) throws IOException {
		String json = getForString(url, params);
		return JSON.parseObject(json, clazz);
	}

	public static <T> T getForObject(String url, TypeReference<T> clazz) throws IOException {
		String json = getForString(url);
		return JSON.parseObject(json, clazz);
	}

	public static <T> T getForObject(String url, Map<String, ?> params, TypeReference<T> clazz) throws IOException {
		String json = getForString(url, params);
		return JSON.parseObject(json, clazz);
	}

	public static String postForString(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		String body = response.body().string();
		log.info("post uri : {} , return : {} ", url, body);
		return body;
	}

	public static InputStream postForStream(String url) throws IOException {
		Request request = new Request.Builder().url(url).build();
		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		return response.body().byteStream();
	}

	public static String postForString(String url, Map<String, ?> params) throws IOException {

		Builder builder = new FormBody.Builder();
		if (!MapUtils.isEmpty(params)) {
			params.forEach((k, v) -> {
				builder.add(k, String.valueOf(v));
			});

		}
		Request request = new Request.Builder().url(url).post(builder.build()).build();
		Response response = client.newCall(request).execute();

		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		return response.body().string();
	}

	public static InputStream postForStream(String url, Map<String, ?> params) throws IOException {

		Builder builder = new FormBody.Builder();
		if (!MapUtils.isEmpty(params)) {
			params.forEach((k, v) -> {
				builder.add(k, String.valueOf(v));
			});
		}
		Request request = new Request.Builder().url(url).post(builder.build()).build();
		Response response = client.newCall(request).execute();

		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		return response.body().byteStream();
	}

	public static <T> T postForObject(String url, Class<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url), clazz);
	}

	public static <T> T postForObject(String url, Map<String, ?> params, Class<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url, params), clazz);
	}

	public static <T> T postForObject(String url, TypeReference<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url), clazz);
	}

	public static <T> T postForObject(String url, Map<String, ?> params, TypeReference<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url, params), clazz);
	}

	public static String postForString(String url, String json) throws IOException {
		return postForString(url, null, json);
	}

	public static String postForString(String url, Map<String, ?> params, String json) throws IOException {

		Request.Builder requestBuilder = new Request.Builder().url(getRequestUrl(url, params));

		if (StringUtils.isNotBlank(json)) {
			requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json));
		}
		Response response = client.newCall(requestBuilder.build()).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}

		String body = response.body().string();
		log.info("post uri : {} , return : {} ", url, body);
		return body;
	}

	public static InputStream postForStream(String url, Map<String, ?> params, String json) throws IOException {

		Request.Builder requestBuilder = new Request.Builder().url(getRequestUrl(url, params));

		if (StringUtils.isNotBlank(json)) {
			requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json));
		}
		Response response = client.newCall(requestBuilder.build()).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		return response.body().byteStream();
	}

	public static InputStream postForStream(String url, String json) throws IOException {
		return postForStream(url, null, json);
	}

	public static <T> T postForObject(String url, String json, Class<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url, json), clazz);
	}

	public static <T> T postForObject(String url, Map<String, ?> params, String json, Class<T> clazz)
			throws IOException {
		return JSON.parseObject(postForString(url, params, json), clazz);
	}

	public static <T> T postForObject(String url, String json, TypeReference<T> clazz) throws IOException {
		return JSON.parseObject(postForString(url, json), clazz);
	}

	public static <T> T postForObject(String url, Map<String, ?> params, String json, TypeReference<T> clazz)
			throws IOException {
		return JSON.parseObject(postForString(url, params, json), clazz);
	}

	public static String postFormDataForString(String url, Map<String, ?> params, Map<String, File> files)
			throws IOException {

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (!MapUtils.isEmpty(params)) {
			params.forEach((k, v) -> {
				builder.addFormDataPart(k, String.valueOf(v));
			});
		}

		if (!MapUtils.isEmpty(files)) {
			files.forEach((k, v) -> {
				if (v != null) {
					RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), v);
					builder.addFormDataPart(k, v.getName(), fileBody);
				}
			});
		}
		Request request = new Request.Builder().url(url).post(builder.build()).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}

		String body = response.body().string();
		log.info("post uri : {} , return : {} ", url, body);
		return body;
	}

	public static InputStream postFormDataForInputStream(String url, Map<String, ?> params, Map<String, File> files)
			throws IOException {

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (!MapUtils.isEmpty(params)) {
			params.forEach((k, v) -> {
				builder.addFormDataPart(k, String.valueOf(v));
			});
		}

		if (!MapUtils.isEmpty(files)) {
			files.forEach((k, v) -> {
				if (v != null) {
					RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), v);
					builder.addFormDataPart(k, v.getName(), fileBody);
				}
			});
		}
		Request request = new Request.Builder().url(url).post(builder.build()).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}

		return response.body().byteStream();
	}

	public static String postFormByteDataForString(String url, Map<String, ?> params, Map<String, byte[]> files)
			throws IOException {

		MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (!MapUtils.isEmpty(params)) {
			params.forEach((k, v) -> {
				builder.addFormDataPart(k, String.valueOf(v));
			});
		}

		if (!MapUtils.isEmpty(files)) {
			files.forEach((k, v) -> {
				if (v != null) {
					RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), v);
					builder.addFormDataPart(k, k, fileBody);
				}
			});

		}

		Request request = new Request.Builder().url(url).post(builder.build()).build();

		Response response = client.newCall(request).execute();
		if (!response.isSuccessful()) {
			throw new HttpFailedException(response.code(), "请求失败");
		}
		String body = response.body().string();
		log.info("post uri : {} , return : {} ", url, body);
		return body;
	}

	public static <T> T postFormDataForObject(String url, Map<String, ?> params, Map<String, File> files,
			Class<T> clazz) throws IOException {
		return JSON.parseObject(postFormDataForString(url, params, files), clazz);
	}

	public static <T> T postFormDataForObject(String url, Map<String, ?> params, Map<String, File> files,
			TypeReference<T> clazz) throws IOException {
		return JSON.parseObject(postFormDataForString(url, params, files), clazz);
	}

	public static String postFormDataForString(String url, Map<String, ?> params) throws IOException {
		return postFormDataForString(url, params, null);
	}

	public static String postFormFileDataForString(String url, Map<String, File> files) throws IOException {
		return postFormDataForString(url, null, files);
	}

	public static <T> T postFormDataForObject(String url, Map<String, ?> params, Class<T> clazz) throws IOException {
		return JSON.parseObject(postFormDataForString(url, params, null), clazz);
	}

	public static <T> T postFormDataForObject(String url, Map<String, ?> params, TypeReference<T> clazz)
			throws IOException {
		return JSON.parseObject(postFormDataForString(url, params, null), clazz);
	}

	public static <T> T postFormFileDataForObject(String url, Map<String, File> files, Class<T> clazz)
			throws IOException {
		return JSON.parseObject(postFormFileDataForString(url, files), clazz);
	}

	public static <T> T postFormFileDataForObject(String url, Map<String, File> files, TypeReference<T> clazz)
			throws IOException {
		return JSON.parseObject(postFormFileDataForString(url, files), clazz);
	}

}
