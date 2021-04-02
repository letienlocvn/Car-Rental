/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loclt.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author WIN
 */
public class ContextListen implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, String> file = new HashMap<>();
        ServletContext sc = sce.getServletContext();
        sc.getRealPath("/");
        String fileInWeb = "/WEB-INF/process.txt";
        String path = sc.getRealPath(fileInWeb);
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringTokenizer stk;
            while ((line = br.readLine()) != null) {
                stk = new StringTokenizer(line, "=");
                String key = stk.nextToken().trim();
                String value = stk.nextToken().trim();
                file.put(key, value);
            }
            sce.getServletContext().setAttribute("FILE", file);
            br.close();
            fr.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
