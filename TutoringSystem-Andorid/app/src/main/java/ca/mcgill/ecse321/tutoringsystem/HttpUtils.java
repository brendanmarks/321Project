package ca.mcgill.ecse321.tutoringsystem;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {

    public static final String DEFAULT_BASE_URL = "https://tutoringsystem-backend.herokuapp.com/";

    private static String baseURL;
    private static AsyncHttpClient client = new AsyncHttpClient();

    static{
        baseURL = DEFAULT_BASE_URL;
    }

    public static void setBaseURL(String baseURL) {
        HttpUtils.baseURL = baseURL;
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url,params,responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url,params,responseHandler);
    }

    public static String getAbsoluteUrl(String relativeUrl) {
        return baseURL + relativeUrl;
    }

}
