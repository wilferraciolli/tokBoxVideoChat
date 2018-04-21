/**
 * (c) Midland Software Limited 2018
 * Name     : TokBoxSessionAppServer.java
 * Author   : ferraciolliw
 * Date     : 16 Apr 2018
 */
package com.wiltech.tokBox.tokbox;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.opentok.MediaMode;
import com.opentok.OpenTok;
import com.opentok.Session;
import com.opentok.SessionProperties;
import com.opentok.exception.OpenTokException;
import com.opentok.util.HttpClient;

/**
 *
 */
public class TokBoxSessionAppServer {
    private static final int apiKey = 46095662;
    private static final String apiSecret = "52108e5c1542bf80ef1c40cf6cf533c1206160f2";

    static{
        setProxyDetails();
        disableSslVerification();
    }

    public String test() {

        //Build TokBox
       // final OpenTok opentok = new OpenTok(apiKey, apiSecret);
        OpenTok.Builder builder = new OpenTok.Builder(apiKey, apiSecret);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy1.eu.webscanningservice.com", 3128));
        OpenTok opentok = builder.proxy(proxy, HttpClient.ProxyAuthScheme.BASIC, "proxy", "TestProxy").build();

        final Session session;
        String sessionId = "faileddddddddddddddddddddddddddddddddddddddddddddddddddddddd";

        try {
            System.out.println("Starting................................");
            //Create the session properties to allow archiving
            SessionProperties sessionProperties = new SessionProperties.Builder()
                    .mediaMode(MediaMode.ROUTED)
                    .build();
            session = opentok.createSession(sessionProperties);

            //get session id
            sessionId = session.getSessionId();

            // Generate a token from just a sessionId (fetched from a database)
            String token1 = opentok.generateToken(sessionId);
            String token2 = opentok.generateToken(sessionId);
            String token3 = opentok.generateToken(sessionId);
            // Generate a token by calling the method on the Session (returned from createSession)
            //            String tokenFromSession = session.generateToken();

            // Set some options in a token
            //            String tokenCustomized = session.generateToken(new TokenOptions.Builder()
            //                    .role(Role.MODERATOR)
            //                    .expireTime((System.currentTimeMillis() / 1000L) + (7 * 24 * 60 * 60)) // in one week
            //                    .data("name=Johnny")
            //                    .build());
            System.out.println(sessionId);
            System.out.println("Token 1 = " + token1);
            System.out.println("Token 2 = " + token2);
            System.out.println("Token 3 = " + token3);

        } catch (OpenTokException e) {
            System.out.println(e.getCause());
            e.printStackTrace();
        }
        // }

        return sessionId;
    }

    //  return sessionId;
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

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
                }

                public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            final HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (final KeyManagementException e) {
            e.printStackTrace();
        }
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
