package com.app.atsz7.viewpagerautoscroll.module;

import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.BaseEncoding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Okio;

 class AbstractTransformingDecodingInterceptor implements Interceptor {



    @Override
    @SuppressWarnings("resource")
    public final Response intercept(final Chain chain)
            throws IOException {
        final Request request = chain.request();
        final Response response = chain.proceed(request);
        final ResponseBody body = response.body();
        String newRes = response.body().string();
       newRes = newRes.replace("/","");

        InputStream targetStream = new ByteArrayInputStream(newRes.getBytes());
        return response.newBuilder()
                .body(ResponseBody.create(
                        body.contentType(),
                        body.contentLength(),
                        Okio.buffer(Okio.source(targetStream))
                ))
                .build();
    }
     protected InputStream transformInputStream(final InputStream inputStream) {
         return BaseEncoding.base64().decodingStream(new InputStreamReader(inputStream));
     }

}