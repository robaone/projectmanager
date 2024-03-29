/*
 * Copyright 2007 AOL, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.robaone.api.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.robaone.api.data.AppDatabase;
import com.robaone.api.oauth.ROAPIOAuthProvider;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

/**
 * Request token request handler
 * 
 * @author Praveen Alavilli
 */
public class RequestTokenServlet extends HttpServlet {
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }
        
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
        	AppDatabase.writeLog("00031: RequestTokenServlet.processRequest(...)");
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            AppDatabase.writeLog("00032: Received signature = "+requestMessage.getParameter("oauth_signature"));
            OAuthConsumer consumer = ROAPIOAuthProvider.getConsumer(requestMessage);
            AppDatabase.writeLog("00033: consumer found");
            OAuthAccessor accessor = new OAuthAccessor(consumer);
            ROAPIOAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
            AppDatabase.writeLog("00034: message validated");
            {
                // Support the 'Variable Accessor Secret' extension
                // described in http://oauth.pbwiki.com/AccessorSecret
                String secret = requestMessage.getParameter("oauth_accessor_secret");
                if (secret != null) {
                    accessor.setProperty(OAuthConsumer.ACCESSOR_SECRET, secret);
                }
            }
            // generate request_token and secret
            ROAPIOAuthProvider.generateRequestToken(accessor);
            AppDatabase.writeLog("00035: request token ("+accessor.requestToken+") generated");
            response.setContentType("text/plain");
            OutputStream out = response.getOutputStream();
            OAuth.formEncode(OAuth.newList("oauth_token", accessor.requestToken,
                                           "oauth_token_secret", accessor.tokenSecret),
                             out);
            out.close();
            
        } catch (Exception e){
        	e.printStackTrace();
            ROAPIOAuthProvider.handleException(e, request, response, true);
        }
        
    }

    private static final long serialVersionUID = 1L;

}
