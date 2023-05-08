package ssginc_kdt_team3.BE.service.cust;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoService {
  public String getToken(String code) throws IOException {
    // 인가코드로 토큰받기
    String host = "https://kauth.kakao.com/oauth/token";
    URL url = new URL(host);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    String token = "";
    try {
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoOutput(true); // 데이터 기록 알려주기

      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
      StringBuilder sb = new StringBuilder();
      sb.append("grant_type=authorization_code");
      sb.append("&client_id=e7293e102967c3d3a4289b15815c5154");
      sb.append("&redirect_uri=http://127.0.0.1:8080/cust/kakao");
      sb.append("&code=" + code); // 인가코드를 전달해서 토큰을 받아야해

      bw.write(sb.toString());
      bw.flush();

      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);

      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      String result = "";
      while ((line = br.readLine()) != null) {
        result += line;
      }
      System.out.println("result = " + result);

      // json parsing
      JSONParser parser = new JSONParser();
      JSONObject elem = (JSONObject) parser.parse(result);

      String access_token = elem.get("access_token").toString();
      String refresh_token = elem.get("refresh_token").toString();
      System.out.println("refresh_token = " + refresh_token);
      System.out.println("access_token = " + access_token);

      token = access_token;

      br.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return token;
  }


  public Map<String, Object> getUserInfo(String access_token) throws IOException {
    String host = "https://kapi.kakao.com/v2/user/me";
    Map<String, Object> result = new HashMap<>();
    try {
      URL url = new URL(host);

      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
      urlConnection.setRequestMethod("GET");

      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);


      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      String res = "";
      while((line=br.readLine())!=null)
      {
        res+=line;
      }

      System.out.println("res = " + res);


      JSONParser parser = new JSONParser();
      JSONObject obj = (JSONObject) parser.parse(res);
      JSONObject kakao_account = (JSONObject) obj.get("kakao_account");
      JSONObject properties = (JSONObject) obj.get("properties");


      String id = obj.get("id").toString();
      String nickname = properties.get("nickname").toString();
      String age_range = kakao_account.get("age_range").toString();
      String birthday = kakao_account.get("birthday").toString();
      String gender = kakao_account.get("gender").toString();


      result.put("id", id);
      result.put("nickname", nickname);
      result.put("age_range", age_range);
      result.put("birthday", birthday);
      result.put("gender", gender);

      br.close();


    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    return result;
  }

  public String getAgreementInfo(String access_token)
  {
    String result = "";
    String host = "https://kapi.kakao.com/v2/user/scopes";
    try{
      URL url = new URL(host);
      HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setRequestProperty("Authorization", "Bearer "+access_token);

      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      String line = "";
      while((line=br.readLine())!=null)
      {
        result+=line;
      }

      int responseCode = urlConnection.getResponseCode();
      System.out.println("responseCode = " + responseCode);

      // result is json format
      br.close();

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


}