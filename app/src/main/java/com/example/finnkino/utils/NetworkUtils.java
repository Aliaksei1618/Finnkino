package com.example.finnkino.utils;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class NetworkUtils {

    public static String generateStringURL (String baseURI, String ... param) {
        Uri.Builder uriBuilder = Uri.parse(baseURI).buildUpon();
        for (int i = 0; i < param.length; i = i + 2) {
            uriBuilder.appendQueryParameter(param[i], param[i + 1]).build();
        }
        Uri uriString = uriBuilder.build();
        return uriString.toString();
    }

    public static Document getDocument(String uriString) {
        Document document = null;
        try {
            document =  DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uriString);
            document.getDocumentElement().normalize();
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static JSONObject getJSONFromURL(String uriString) throws IOException {
        JSONObject jsonObject = null;
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(uriString).openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            jsonObject = new JSONObject(reader.lines().collect(Collectors.joining()));
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return jsonObject;
    }
}
