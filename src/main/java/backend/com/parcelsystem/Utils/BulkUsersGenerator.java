package backend.com.parcelsystem.Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Random;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import backend.com.parcelsystem.Models.Request.UserSignUp;
import java.util.Random;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.boot.archive.internal.FileInputStreamAccess;
import org.hibernate.mapping.Map;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.json.JsonParser;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;



public class BulkUsersGenerator {

    public static final String ADD_URL = "http://localhost:8080/api/users/signup";

    public static String generateRandomPassword(int length) {
        String capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_+-=[]{}|;':,./<>?";
        String allChars = capitalLetters + smallLetters + numbers + specialChars;

        Random random = new Random();
        char[] password = new char[length];

        // 随机选择一个字符集，并从中随机选择一个字符添加到密码中
        for (int i = 0; i < length; i++) {
            String charSet = allChars;
            int charSetIndex = random.nextInt(4);
            if (charSetIndex == 0) {
                charSet = capitalLetters;
            } else if (charSetIndex == 1) {
                charSet = smallLetters;
            } else if (charSetIndex == 2) {
                charSet = numbers;
            } else if (charSetIndex == 3) {
                charSet = specialChars;
            }
            int charIndex = random.nextInt(charSet.length());
            password[i] = charSet.charAt(charIndex);
        }

        return new String(password);
    }

    public static int bulkUserSignUp(int numberOfUsers) throws IOException {
        int fail = 0;

        File jsonFile = new File("SignUpJsonData.json");
        try {
            // 文件不存在就创建文件
            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfUsers; i++) {
            UserSignUp userSignUp = new UserSignUp();
            userSignUp.setPassword(generateRandomPassword(13));
            userSignUp.setUsername(RandomStringUtils.randomAlphabetic(10));
            userSignUp.setZipcode(String.valueOf(RandomUtils.nextInt(10000, 100000)));
            userSignUp.setUsername(RandomStringUtils.randomAlphabetic(10));
            userSignUp
                    .setFullname(RandomStringUtils.randomAlphabetic(10) + " " + RandomStringUtils.randomAlphabetic(10));
            userSignUp.setEmail(
                    RandomStringUtils.randomAlphabetic(10) + "@" + RandomStringUtils.randomAlphabetic(10) + ".com");
            userSignUp.setCity(RandomStringUtils.randomAlphabetic(10));
            userSignUp.setAddress(RandomStringUtils.randomAlphabetic(10));

            /*
             * String jsonData = userSignUp.toString(inMap);
             * FileWriter fileWriter = new FileWriter(jsonFile.getAbsoluteFile(), true);
             * BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             * bufferedWriter.write(jsonData);
             * bufferedWriter.close();
             * fileWriter.close();
             */

            // 下面是第二种写法
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            String json = writer.writeValueAsString(userSignUp);
            try (FileWriter fileWriter = new FileWriter("SignUpJsonData.json", true)) {
                fileWriter.write(json);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        /*
         * File file=new File("SignUpJsonData.json");
         * String content= FileUtils.readFileToString(file,"UTF-8");
         * JSONObject jsonObject=new JSONObject(content);
         */

        JsonReader jsonReader = new JsonReader(
                new InputStreamReader(new FileInputStream("SignUpJsonData.json"), "UTF-8"));
        Gson gson = new GsonBuilder().create();

        // strem mode读取文件
        jsonReader.beginObject();
        jsonReader.setLenient(true);
        String username = null;
        String password = null;
        String fullname = null;
        String email = null;
        String city = null;
        String address = null;
        String zipcode = null;
        String driverCode = "";
        int i = 1;

        while (jsonReader.hasNext()) {
            UserSignUp userSignUp = new UserSignUp();
            String name = jsonReader.nextName();
            switch (name) {
                case "username":
                    username = jsonReader.nextString();
                    break;
                case "password":
                    password = jsonReader.nextString();
                    break;
                case "fullname":
                    fullname = jsonReader.nextString();
                    break;
                case "email":
                    email = jsonReader.nextString();
                    break;
                case "city":
                    city = jsonReader.nextString();
                    break;
                case "address":
                    address = jsonReader.nextString();
                    break;
                case "zipcode":
                    zipcode = jsonReader.nextString();
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
            if (i % 8 == 0) {
                jsonReader.endObject();
                if (jsonReader.peek() != JsonToken.END_DOCUMENT)
                    jsonReader.beginObject();
                userSignUp.setUsername(username);
                userSignUp.setPassword(password);
                userSignUp.setFullname(fullname);
                userSignUp.setEmail(email);
                userSignUp.setCity(city);
                userSignUp.setAddress(address);
                userSignUp.setZipcode(zipcode);

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(userSignUp);
                fail = makeHttpURLConnection("http://localhost:8080/api/users/signup", "POST", jsonString);
            } // end of if
            i++;
            
        } // end of while

        return fail;

        /*
         * while (jsonReader.hasNext()) {
         * // Read data into object model
         * UserSignUp userSignUp = gson.fromJson(jsonReader, UserSignUp.class);
         * 
         * // Java对象转Json格式字符串
         * 
         * 
         * 
         * 
         * }
         * jsonReader.close();
         */

    }

    public static int bulkUserLogin() throws IOException{
        int fail = 0;

        JsonReader jsonReader = new JsonReader(
                new InputStreamReader(new FileInputStream("SignUpJsonData.json"), "UTF-8"));
        Gson gson = new GsonBuilder().create();

        // Read file in stream mode
        jsonReader.beginObject();
        jsonReader.setLenient(true);

        String password = null;

        String email = null;

        int i = 1;

        while (jsonReader.hasNext()) {
            
            String name = jsonReader.nextName();
            switch (name) {
               
                case "password":
                    password = jsonReader.nextString();
                    break;
                
                case "email":
                    email = jsonReader.nextString();
                    break;
                
                default:
                    jsonReader.skipValue();
                    break;
            }
            if (i % 8 == 0) {
                jsonReader.endObject();
                if (jsonReader.peek() != JsonToken.END_DOCUMENT)
                    jsonReader.beginObject();
                String userSignIn = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
                makeHttpURLConnection("http://localhost:8080/api/users/signIn", "PUT", userSignIn);

            } // end of if
            i++;
            
        } // end of while
        return fail;
    }

    public static int makeHttpURLConnection(String urlString, String requestMethodString, String jsonString) {
        int fail = 0;

            try {
                // 创建连接
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod(requestMethodString);
                connection.setUseCaches(false);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("Content-Type",
                        "application/json");
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(jsonString);
                // System.out.println(jsonString);
                out.flush();
                out.close();

                // 读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String lines;
                StringBuffer stringBuffer = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), "utf-8");
                    stringBuffer.append(lines);
                }
                System.out.println(stringBuffer);
                if (stringBuffer.toString().contains("token")) {
                    System.out.println("success"); 
                } else {
                    System.out.println("fail"); fail++;
                }
                reader.close();
                // 断开连接
                connection.disconnect();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println(jsonString);
            }
        return fail;


    }



}
