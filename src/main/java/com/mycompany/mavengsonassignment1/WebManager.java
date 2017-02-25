/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavengsonassignment1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author W207777797
 */
public class WebManager {
    public static void main(String[] args) {
        WebManager webManager = new WebManager();
        webManager.jsonToJavaObject();
    }
    
    public void jsonToJavaObject()
    {
        Gson gson = new GsonBuilder().create();
        
        String personRecordsJson = fetchData();
        
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(personRecordsJson);
        JsonArray jsonArr = jo.getAsJsonArray("records");
        List<Person> personList = gson.fromJson(jsonArr, ArrayList.class);
        System.out.println("Person List size is : "+ personList.size());
        System.out.println("Person  List Elements are  : "+ personList.toString());
    }
    
    public String fetchData()
    {
        URL url;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL("https://www.w3schools.com/angular/customers.php");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if(conn.getResponseCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code :" + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String output;
            System.out.println("Output from Server... \n");
            
            while((output = br.readLine()) != null)
            {
                sb.append(output);
                System.out.println(output);
            }
            
            conn.disconnect();
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sb.toString();
    }
}
