package com.microsoft.onedriveaccess;

import com.microsoft.services.msa.LiveConnectSession;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.Retrofit;

/**
 * Concrete object to interface with the OneDrive service
 */
public class ODConnection {

    /**
     * The credentials session to use for this connection
     */
    private final LiveConnectSession mConnectSession;

    /**
     * Default Constructor
     *
     * @param connectSession The credentials session to use for this connection
     */
    public ODConnection(final LiveConnectSession connectSession) {
        mConnectSession = connectSession;
    }

    /**
     * Creates an instance of the IOneDriveService
     *
     * @return The IOneDriveService
     */
    public IOneDriveService getService(OkHttpClient client) {
        client = client.clone();
        client.interceptors().add(getInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(new JsonSimpleConverterFactory())
                .baseUrl("https://api.onedrive.com/v1.0/drive/")
                .client(client)
                .build();

        return retrofit.create(IOneDriveService.class);
    }

    private Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "bearer " + mConnectSession.getAccessToken())
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }
}
