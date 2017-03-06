package com.example.mathe.handedalus;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Connection.Response;
import org.jsoup.Connection.Method;

import android.os.AsyncTask;
import android.util.Log;

public class dedalus{

    List<book> myLibrary;

    public dedalus(){
        myLibrary=new ArrayList<book>();
    }

    public static String format(String _name){
        String mem=_name.replace("<td class=\"td1\" valign=\"top\">","");
        String mem2 = "";
        mem2=mem.replace("</td>","");
        return mem2;
    }

    public static List<book> main(String _username, String _password) throws Exception{
        String userAgent = System.getProperty("http.agent");
        Response loginForm = Jsoup.connect("https://primofs1.sibi.usp.br/pds")
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();
        Response doc = Jsoup.connect(loginForm.url().toString())
                .header("Accept-Encoding", "gzip, deflate")
                .data("func", "login")
                .data("calling_system", "aleph")
                .data("term1", "short")
                .data("selfreg", "")
                .data("bor_id", _username)
                .data("bor_verification", _password)
                .data("institute", "USP")
                .data("term", "long")
                .data("url", "http://dedalus.usp.br:80/F/L7G49RY3FXMHAI5DQB8YE7XN59TUX7CJQSDIH9PDLN6SNNG1VX-14193?func=LOGIN-PAGE")
                .cookies(loginForm.cookies())
                .userAgent(userAgent)
                .timeout(100000)
                .followRedirects(true)
                .method(Method.POST)
                .timeout(10000)
                .execute();

        Document page = doc.parse();
        Element link = page.select("a").get(0);
        String url = link.attr("href");
        String remove = "/goto/logon/";
        String finalUrl = url.replace(remove,"");

        Response cookies = Jsoup.connect(finalUrl)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document cookiesDoc = cookies.parse();
        Element urlRedirect = cookiesDoc.select("script").get(0);
        String maker = urlRedirect.toString();
        int first = maker.indexOf("'");
        int last =maker.indexOf("'",first+1);
        String s = maker.substring(first+1,last);

        Response sUrl = Jsoup.connect(s)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document page2 = sUrl.parse();
        Element link2 = page2.select("a").get(0);
        String url2 = link2.attr("href");
        String remove2 = "/goto/logon/";
        String finalUrl2 = url2.replace(remove2,"");

        Response sUrl2 = Jsoup.connect(finalUrl2)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        String sUrl2String = sUrl2.parse().toString();
        int index = sUrl2String.indexOf("<td class=\"topbar\" valign=\"middle\" align=\"center\"><a href=\"http://dedalus.usp.br:");
        int finalIndex = sUrl2String.indexOf("bor-info\" title=\"Mostra");
        String infoUrl = sUrl2String.substring(index+59,finalIndex+8);

        Response sUrl3 = Jsoup.connect(infoUrl)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        String sUrl3String = sUrl3.parse().toString();

        int index2 = sUrl3String.indexOf("<td class=\"td1\"><a href=\"javascript:replacePage('http://dedalus.usp.br");
        int finalIndex2 = sUrl3String.indexOf("&amp;adm_library=USP50');\">",index2);
        String infoUrl2 = sUrl3String.substring(index2+49,finalIndex2+22);

        Response finalResponse = Jsoup.connect(infoUrl2)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document finalPage = finalResponse.parse();
        Element table = finalPage.select("table").get(6);
        Elements rows = table.select("tr");
        List<book> myLibrary = new ArrayList<book>();
        for(int i=1; i!=rows.size();i++){
            Element row = rows.get(i);
            Elements cols = row.select("td");
            book baby = new book();

            baby.author=format(cols.get(2).toString());
            baby.name=format(cols.get(3).toString());
            baby.data=format(cols.get(5).toString());
            baby.library=format(cols.get(8).toString());
            baby.days=Integer.parseInt(dias.getRelativeTime(baby.data));
            myLibrary.add(baby);
        }
        Log.d("test", myLibrary.get(0).author);
        return myLibrary;
    }

    public static List<book> renovar(String _username, String _password, List<String> _books) throws Exception{
        String userAgent = System.getProperty("http.agent");
        Response loginForm = Jsoup.connect("https://primofs1.sibi.usp.br/pds")
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();
        Response doc = Jsoup.connect(loginForm.url().toString())
                .header("Accept-Encoding", "gzip, deflate")
                .data("func", "login")
                .data("calling_system", "aleph")
                .data("term1", "short")
                .data("selfreg", "")
                .data("bor_id", _username)
                .data("bor_verification", _password)
                .data("institute", "USP")
                .data("term", "long")
                .data("url", "http://dedalus.usp.br:80/F/L7G49RY3FXMHAI5DQB8YE7XN59TUX7CJQSDIH9PDLN6SNNG1VX-14193?func=LOGIN-PAGE")
                .cookies(loginForm.cookies())
                .userAgent(userAgent)
                .timeout(100000)
                .followRedirects(true)
                .method(Method.POST)
                .timeout(10000)
                .execute();

        Document page = doc.parse();
        Element link = page.select("a").get(0);
        String url = link.attr("href");
        String remove = "/goto/logon/";
        String finalUrl = url.replace(remove,"");

        Response cookies = Jsoup.connect(finalUrl)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document cookiesDoc = cookies.parse();
        Element urlRedirect = cookiesDoc.select("script").get(0);
        String maker = urlRedirect.toString();
        int first = maker.indexOf("'");
        int last =maker.indexOf("'",first+1);
        String s = maker.substring(first+1,last);

        Response sUrl = Jsoup.connect(s)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document page2 = sUrl.parse();
        Element link2 = page2.select("a").get(0);
        String url2 = link2.attr("href");
        String remove2 = "/goto/logon/";
        String finalUrl2 = url2.replace(remove2,"");

        Response sUrl2 = Jsoup.connect(finalUrl2)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        String sUrl2String = sUrl2.parse().toString();
        int index = sUrl2String.indexOf("<td class=\"topbar\" valign=\"middle\" align=\"center\"><a href=\"http://dedalus.usp.br:");
        int finalIndex = sUrl2String.indexOf("bor-info\" title=\"Mostra");
        String infoUrl = sUrl2String.substring(index+59,finalIndex+8);

        Response sUrl3 = Jsoup.connect(infoUrl)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        String sUrl3String = sUrl3.parse().toString();

        int index2 = sUrl3String.indexOf("<td class=\"td1\"><a href=\"javascript:replacePage('http://dedalus.usp.br");
        int finalIndex2 = sUrl3String.indexOf("&amp;adm_library=USP50');\">",index2);
        String infoUrl2 = sUrl3String.substring(index2+49,finalIndex2+22);

        Response finalResponse = Jsoup.connect(infoUrl2)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.GET)
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();

        Document finalPage = finalResponse.parse();
        Element table = finalPage.select("table").get(6);
        Elements rows = table.select("tr");
        List<String> name= new ArrayList<String>();
        for(int i=1; i!=rows.size();i++){
            Element row = rows.get(i);
            Elements cols = row.select("td");
            for(String book : _books) {
                String candidato = format(cols.get(3).toString());
                boolean ifOrNot = candidato.equals(book);
                if (ifOrNot){
                    String nameR= cols.get(0).toString().substring(52,240);
                    name.add(nameR);
                    int um = nameR.indexOf("-EXP");
                    String sub = nameR.substring(0,um)+"-RENEW"+nameR.substring(um+4)+"&ill_unit=";
                    String end = sub.replace("amp;","");

                    Response algo = Jsoup.connect(end)
                            .header("Accept-Encoding", "gzip, deflate")
                            .method(Method.GET)
                            .cookies(loginForm.cookies())
                            .timeout(10000)
                            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                            .followRedirects(true)
                            .execute();
                }
            }
        }

        Response finalResponse2 = Jsoup.connect(infoUrl2)
                .header("Accept-Encoding", "gzip, deflate")
                .method(Method.POST)
                .data(name.get(0), "checked")
                .cookies(loginForm.cookies())
                .timeout(100000)
                .userAgent(userAgent)
                .followRedirects(true)
                .execute();


        finalPage = finalResponse2.parse();
        table = finalPage.select("table").get(6);
        rows = table.select("tr");
        List<book> myLibrary = new ArrayList<book>();
        for(int i=1; i!=rows.size();i++){
            Element row = rows.get(i);
            Elements cols = row.select("td");
            book baby = new book();

            baby.author=format(cols.get(2).toString());
            baby.name=format(cols.get(3).toString());
            baby.data=format(cols.get(5).toString());
            baby.library=format(cols.get(8).toString());
            baby.days=Integer.parseInt(dias.getRelativeTime(baby.data));
            myLibrary.add(baby);
        }
        Log.d("test", myLibrary.get(0).author);
        return myLibrary;
    }

}