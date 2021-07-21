/*
  Copyright (C) 2020 Yahoo Japan Corporation. All Rights Reserved.
 */
package jp.co.yahoo.adssearchapi.sample.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;
import javax.net.ssl.HttpsURLConnection;

/**
 * Utility method collection for Java Sample Program.
 */
public class ApiUtils {

  private final static String API_VERSION;

  public final static long ACCOUNT_ID;

  private final static String CLIENT_ID;

  private final static String CLIENT_SECRET;

  private final static String REFRESH_TOKEN;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /*
   * static initializer
   */
  static {
    ResourceBundle bundle = ResourceBundle.getBundle("api_config");

    API_VERSION = bundle.getString("API_VERSION");
    ACCOUNT_ID = Long.parseLong(bundle.getString("ACCOUNT_ID"));
    CLIENT_ID = bundle.getString("CLIENT_ID");
    CLIENT_SECRET = bundle.getString("CLIENT_SECRET");
    REFRESH_TOKEN = bundle.getString("REFRESH_TOKEN");
  }

  /**
   * get service endpoint URL.
   *
   * @param serviceName REST API service name.
   * @param action      REST API action name. e.g. get/add/set/remove
   * @return endpoint URL
   */
  private static URL getServiceEndPointURL(String serviceName, String action) throws Exception {
    return new URL("https://ads-search.yahooapis.jp/api/" + API_VERSION + "/" + serviceName + "/" + action);
  }

  /**
   * get current timestamp value.(yyyyMMddHHmmss)
   *
   * @return current timestamp string.
   */
  public static String getCurrentTimestamp() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
  }

  /**
   * send request.
   *
   * @param serviceName  REST API service name
   * @param action       REST API action name. e.g. get/add/set/remove
   * @param request      request object
   * @param responseType response object type
   * @param <I>          request type
   * @param <T>          response type
   * @return T
   */
  public static <I, T> T execute(String serviceName, String action, I request, Class<T> responseType) throws Exception {
    // Call API
    System.out.println("############################################");
    System.out.println(serviceName + "::" + action);
    System.out.println("############################################");

    String accessToken = fetchAccessToken();
    System.out.println("### Hashed AccessToken \n" + calcHash(accessToken));

    URL url = getServiceEndPointURL(serviceName, action);

    byte[] body = objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8);

    HttpsURLConnection connection = createConnection(url, "application/json", body.length);
    connection.setRequestProperty("Authorization", "Bearer " + accessToken);
    System.out.println("### RequestHeaders \n" + connection.getRequestProperties());

    T response;
    try {
      connection.connect();
      request(connection, body);
      response = response(connection, responseType);
    } finally {
      connection.disconnect();
    }

    System.out.println("### RequestBody \n" +  objectMapper.writeValueAsString(request));
    System.out.println("### ResponseHeader \n" + connection.getHeaderFields());
    System.out.println("### ResponseBody \n" + objectMapper.writeValueAsString(response));
    return response;
  }

  /**
   * download data.
   *
   * @param serviceName  REST API service name
   * @param action       REST API action name. e.g. get/add/set/remove
   * @param request      request object
   * @param <I>          request type
   */
  public static <I> void download(String serviceName, String action, I request, String filename) throws Exception {
    // Call API
    System.out.println("############################################");
    System.out.println(serviceName + "::download");
    System.out.println("############################################");

    File downloadDir = new File(new File(".", "download").getAbsolutePath());
    if (!downloadDir.exists()) {
      downloadDir.mkdirs();
    }
    File filepath = new File(downloadDir, filename);

    String accessToken = fetchAccessToken();
    System.out.println("### Hashed AccessToken \n" + calcHash(accessToken));

    URL url = getServiceEndPointURL(serviceName, action);

    byte[] body = objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8);

    HttpsURLConnection connection = createConnection(url, "application/json", body.length);
    connection.setRequestProperty("Authorization", "Bearer " + accessToken);
    System.out.println("### RequestHeaders \n" + connection.getRequestProperties());

    connection.connect();
    request(connection, body);

    // download
    try(BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
      FileOutputStream fos = new FileOutputStream(filepath, false)) {
      int b;
      while ((b = is.read()) != -1) {
        fos.write(b);
      }
    }

    System.out.println("### RequestBody \n" + objectMapper.writeValueAsString(request));
    System.out.println("### ResponseHeader \n" + connection.getHeaderFields());
    System.out.println("### Downloaded into " + filepath);
  }

  /**
   * fetch Access Token
   */
  private static String fetchAccessToken() throws IOException {
    URL url = URI.create("https://biz-oauth.yahoo.co.jp/oauth/v1/token").toURL();

    byte[] parameter = ("grant_type=refresh_token&" //
      + "client_id=" + CLIENT_ID + "&" //
      + "client_secret=" + CLIENT_SECRET + "&" //
      + "refresh_token=" + REFRESH_TOKEN + "&" //
    ).getBytes(StandardCharsets.UTF_8);

    HttpsURLConnection connection = createConnection(url, "application/x-www-form-urlencoded", parameter.length);
    Map<String, String> response;
    try {
      connection.connect();
      request(connection, parameter);
      response = response(connection, new TypeReference<Map<String, String>>() {});
    } finally {
      connection.disconnect();
    }

    return response.get("access_token");
  }

  private static HttpsURLConnection createConnection(URL url, String contentType, int contentLength) throws IOException {
    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
    connection.setConnectTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setUseCaches(false);
    connection.setDoOutput(true);
    connection.setDoInput(true);

    connection.addRequestProperty("Content-Type", contentType);
    connection.addRequestProperty("Content-Length", String.valueOf(contentLength));

    return connection;
  }

  private static String calcHash(String accessToken){
    StringBuilder stringBuilder = new StringBuilder();

    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(accessToken.getBytes());
      byte[] hash = md.digest();
      stringBuilder = new StringBuilder(2 * hash.length);
      for(byte b: hash) {
        stringBuilder.append(String.format("%02x", b&0xff));
      }
    } catch (NoSuchAlgorithmException e){
      e.printStackTrace();
    }

    return stringBuilder.toString();
  }

  private static void request(HttpsURLConnection connection, byte[] body) throws IOException {
    BufferedOutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
    outputStream.write(body);
    outputStream.close();
  }

  private static <T> T response(HttpsURLConnection connection, Class<T> responseType) throws IOException {
    InputStream stream = connection.getInputStream();
    InputStreamReader inputStream = new InputStreamReader(stream);
    T response = objectMapper.readValue(inputStream, responseType);
    inputStream.close();

    return response;
  }

  private static <T> T response(HttpsURLConnection connection, TypeReference<T> typeReference) throws IOException {
    InputStream stream = connection.getInputStream();
    InputStreamReader inputStream = new InputStreamReader(stream);
    T response = objectMapper.readValue(inputStream, typeReference);
    inputStream.close();

    return response;
  }
}
