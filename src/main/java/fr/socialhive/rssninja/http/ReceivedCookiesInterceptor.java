package fr.socialhive.rssninja.http;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by ganitzsh on 1/29/17.
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            WebServiceSingleton.getInstance().setCookies(cookies);
        }

        return originalResponse;
    }
}