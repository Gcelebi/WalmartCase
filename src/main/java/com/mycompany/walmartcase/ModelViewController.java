/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.walmartcase;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.apache.ApacheHttpTransport;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author User
 */
@RestController
public class ModelViewController {
private final String ApiKey = "ghnk54dbsnghhp9exmttckap";

        @RequestMapping(value={"/home"}, method = RequestMethod.GET)
	public ModelAndView home(@RequestParam(value="p", defaultValue = "1") String page) throws IOException{
		ModelAndView mv = new ModelAndView();                
                
                
                
                //start parameter calculation
                int p = Integer.parseInt(page); 
                
                if(p<=0){
                mv.addObject("page_error","page number below 1");
            
                }
                else{
                p = (p-1)*20+1;
                String pnum = Integer.toString(p);
                

                //http call
                HttpRequestFactory requestFactory = new ApacheHttpTransport().createRequestFactory();
		GenericUrl genericUrl = new GenericUrl(
                        "http://api.walmartlabs.com/v1/"
                                + "search?apiKey="
                                +  ApiKey 
                                + "&query=*&categoryId=3920&numItems=20&start="+ pnum);
		HttpResponse resp = requestFactory.buildGetRequest(genericUrl).execute();
                
                //read response
                BufferedReader br = new BufferedReader(new InputStreamReader(resp.getContent()));
                StringBuilder content = new StringBuilder();
                String line="";
                while ((line = br.readLine()) != null ){content.append(line);}
                
                //convert to Json
                Object obj=JSONValue.parse(content.toString());
                JSONObject jobject=(JSONObject)obj;
                JSONArray items = (JSONArray) jobject.get("items");
                System.out.print(items);
                ArrayList<Object> listdata = new ArrayList<Object>();     
                if (items != null) { 
                for (int i=0;i<items.size();i++){ 
                Object object = items.get(i);
                listdata.add(object);}} 
                

                //add to view
                List<Object> l1 = listdata.subList(0, 4);            
                List<Object> l2 = listdata.subList(4, 8);                
                List<Object> l3 = listdata.subList(8, 12);                
                List<Object> l4 = listdata.subList(12, 16);
                List<Object> l5 = listdata.subList(16, 20);
                mv.addObject("items1", l1);
                mv.addObject("items2", l2);
                mv.addObject("items3", l3);
                mv.addObject("items4", l4);
                mv.addObject("items5", l5);
                }
                return mv;
        }
        
        
        @RequestMapping(value={"/product"}, method = RequestMethod.GET)
	public ModelAndView product(@RequestParam("id") String id) throws IOException{
		ModelAndView mv = new ModelAndView();
                
                HttpRequestFactory requestFactory = new ApacheHttpTransport().createRequestFactory();
		GenericUrl genericUrl = new GenericUrl("http://api.walmartlabs.com/v1/items/" 
                        + id + "?apiKey="
                        + ApiKey + "&format=json");
                HttpResponse resp = requestFactory.buildGetRequest(genericUrl).execute();
                
                //read response
                BufferedReader br = new BufferedReader(new InputStreamReader(resp.getContent()));
                StringBuilder content = new StringBuilder();
                String line="";
                while ((line = br.readLine()) != null ){content.append(line);}
                
                //convert to Json
                Object obj=JSONValue.parse(content.toString());
                JSONObject item=(JSONObject)obj;
                item.putIfAbsent("customerRating", "");
                item.putIfAbsent("customerRatingImage", "");
                item.putIfAbsent("msrp", "");
                mv.addObject("item",item);
                GenericUrl genericUrlr = new GenericUrl("http://api.walmartlabs.com/v1/reviews/"
                        + id + "?apiKey="+ApiKey+"&format=json");
                HttpResponse resp2 = requestFactory.buildGetRequest(genericUrlr).execute();

                BufferedReader brr = new BufferedReader(new InputStreamReader(resp2.getContent()));
                line="";
                StringBuilder content2 = new StringBuilder();
                while ((line = brr.readLine()) != null ){content2.append(line);}
                
                Object obj2=JSONValue.parse(content2.toString());
                JSONObject review=(JSONObject)obj2;
                JSONArray reviews = (JSONArray) review.get("reviews");
                
                ArrayList<Object> listdata = new ArrayList<Object>();     
                if (reviews != null) { 
                for (int i=0;i<reviews.size();i++){ 
                Object object = reviews.get(i);
                listdata.add(object);}} 
                System.out.println(listdata);
                mv.addObject("reviews",listdata);
                return mv;
        }
}
