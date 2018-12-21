package com.byka.snowlol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(final Model model) {
        return "index";
    }

    @RequestMapping(value = "/findFriends", method = RequestMethod.POST)
    public String findFriends(@RequestParam("cookie") String cookie, Model model) {
        if (cookie != null && !cookie.isEmpty()) {
            try {
                List<Long> ids = getIds(cookie);
                if (ids != null && !ids.isEmpty()) {
                    model.addAttribute("ids", ids.toString());
                    return "pressToSend";
                } else {
                    model.addAttribute("message", "friends list is empty");
                }
            } catch (Exception e) {
                model.addAttribute("message", "Exception " + e.getMessage());
                System.err.println(e);
            }
        } else {
            model.addAttribute("message", "set fucking cookies");
        }
        return "noFriends";
    }

    private List<Long> getIds(String cookie) throws Exception {
        URL url = new URL("https://ny.ru.leagueoflegends.com/api/snowballs/friends");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        setHeaders(connection, cookie);
        connection.connect();
        InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Long> ids = new ArrayList<Long>();
        String s = reader.readLine();
        while (s != null) {
            if (s.trim().equals("</span>[")) {
                while (!s.trim().equals("]</pre>")) {
                    if (s.trim().startsWith("\"id\"")) {
                        String id = s.substring(s.indexOf(": ") + 2, s.indexOf(','));
                        ids.add(Long.parseLong(id));
                    }
                    s = reader.readLine();
                }
                break;
            }
            s = reader.readLine();
        }
        in.close();
        connection.disconnect();
        return ids;
    }

    private void setHeaders(HttpURLConnection connection, String cookie) {
        connection.setRequestProperty("Cookie", cookie);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    }
}
