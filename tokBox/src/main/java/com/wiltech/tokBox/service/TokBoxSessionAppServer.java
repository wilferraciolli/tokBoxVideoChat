/**
 * (c) Midland Software Limited 2018
 * Name     : TokBoxSessionAppServer.java
 * Author   : ferraciolliw
 * Date     : 06 Apr 2018
 */
package com.wiltech.tokBox.service;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import com.opentok.MediaMode;
import com.opentok.OpenTok;
import com.opentok.Role;
import com.opentok.Session;
import com.opentok.SessionProperties;
import com.opentok.TokenOptions;
import com.opentok.exception.OpenTokException;

/**
 * The type Tok box session app server.
 */
public class TokBoxSessionAppServer {

    private static final int apiKey = 46095662;
    private static final String apiSecret = "52108e5c1542bf80ef1c40cf6cf533c1206160f2";

    public static void main(String[] args) {
        //    public String test() {
        //setProxyDetails();

        //Build TokBox
        final OpenTok opentok = new OpenTok(apiKey, apiSecret);

        final Session session;
        String sessionId = "faileddddddddddddddddddddddddddddddddddddddddddddddddddddddd";

        try {
            //Create the session properties to allow archiving
            SessionProperties sessionProperties = new SessionProperties.Builder()
                    .mediaMode(MediaMode.ROUTED)
                    .build();
            session = opentok.createSession(sessionProperties);

            //get session id
            sessionId = session.getSessionId();

            // Generate a token from just a sessionId (fetched from a database)
            String tokenFromSessionId = opentok.generateToken(sessionId);
            // Generate a token by calling the method on the Session (returned from createSession)
            //            String tokenFromSession = session.generateToken();

            // Set some options in a token
            //            String tokenCustomized = session.generateToken(new TokenOptions.Builder()
            //                    .role(Role.MODERATOR)
            //                    .expireTime((System.currentTimeMillis() / 1000L) + (7 * 24 * 60 * 60)) // in one week
            //                    .data("name=Johnny")
            //                    .build());

        } catch (OpenTokException e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        // }

        //  return sessionId;
    }

    private static void setProxyDetails() {
        // set the wildfly system properties values
        System.setProperty("http.proxyHost", "msl-svr135");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "msl-svr135");
        System.setProperty("https.proxyPort", "3128");

        // System.setProperty("http.proxyUser", "proxy");
        // System.setProperty("http.proxyPassword", "TestProxy");
        // System.setProperty("https.proxyUser", "proxy");
        // System.setProperty("https.proxyPassword", "TestProxy");

        // set username and password for the proxy
        Authenticator.setDefault(new ProxyAuthenticator("proxy", "TestProxy"));
        // Authenticator
        // .setDefault(new ProxyAuthenticator(System.getProperty("http.proxyUser"),
        // System.getProperty("http.proxyUser")));
    }

}

class ProxyAuthenticator extends Authenticator {

    private final String user;
    private final String password;

    /**
     * This class allows an application to provide user authentication through a proxy server.
     * @param user The username.
     * @param password The password.
     */
    public ProxyAuthenticator(final String user, final String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}