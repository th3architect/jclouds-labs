package org.jclouds.vcloud.director.v1_5.filters;

import org.jclouds.http.HttpException;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpRequestFilter;

import com.google.common.net.HttpHeaders;

public class AddAcceptHeaderToRequest implements HttpRequestFilter {

   @Override
   public HttpRequest filter(HttpRequest request) throws HttpException {
      return request.toBuilder().replaceHeader(HttpHeaders.ACCEPT, "application/*+xml;version=1.5").build();
   }
}
