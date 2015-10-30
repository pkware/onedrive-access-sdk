package com.microsoft.onedriveaccess;

import com.microsoft.onedriveaccess.model.Item;
import com.microsoft.onedriveaccess.model.SearchResult;
import com.squareup.okhttp.RequestBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Service interface that will connect to OneDrive
 */
public interface IOneDriveService {

    /**
     * Gets the root of the default drive
     */
    @GET("/v1.0/drives/root")
    @Headers("Accept: application/json")
    Call<Item> getMyRoot();

    /**
     * Gets an item
     *
     * @param itemId the item id
     */
    @GET("items/{item-id}/")
    @Headers("Accept: application/json")
    Call<Item> getItemId(@Path("item-id") final String itemId);

    /**
     * Gets an item with options
     *
     * @param itemId  the item id
     * @param options parameter options for this request
     */
    @GET("items/{item-id}/")
    @Headers("Accept: application/json")
    Call<Item> getItemId(@Path("item-id") final String itemId,
                         @QueryMap Map<String, String> options);

    /**
     * Searchs the root directory for
     *
     * @param itemId the item id
     * @param query  text to search for
     */
    @GET("items/{item-id}/view.search")
    @Headers("Accept: application/json")
    Call<SearchResult> searchRoot(@Path("item-id") final String itemId,
                                  @Query("q") final String query);

    /**
     * Creates a Folder on OneDrive
     *
     * @param parentId the item id
     * @param newItem  The item to create
     */
    @POST("items/{parent-id}/children")
    @Headers("Accept: application/json")
    Call<Item> createFolder(@Path("parent-id") final String parentId,
                            @Body Item newItem);

    /**
     * Creates a file on OneDrive
     *
     * @param parentId the item id
     * @param fileName The name of the file that is being created
     * @param fileBody the contents of the file
     */
    @PUT("items/{item-id}/children/{file-name}/content")
    Call<Item> createItemId(@Path("item-id") final String parentId,
                            @Path("file-name") final String fileName,
                            @Body RequestBody fileBody);
}
