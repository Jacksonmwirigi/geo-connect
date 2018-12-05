//package com.jackoyee.geopics.network;
//
//import android.app.Application;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//
//import com.bumptech.glide.signature.ObjectKey;
//import com.google.gson.Gson;
//import com.jackoyee.geopics.session.Session;
//
//import java.io.File;
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.Cache;
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class App extends Application{
//
//    private Session session;
//    private AuthenticationListener authenticationListener;
//
//
//    private ApiService apiService;
//    private InternetConnectionListener mInternetConnectionListener;
//    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//    }
//
//    public Session getSession(){
//       if (session==null){
//           session =new Session() {
//               @Override
//               public boolean isLoggedIn() {
//                   return true;
//               }
//
//               @Override
//               public String getToken() {
//                   return token;
//               }
//
//               @Override
//               public void saveEamil(String email) {
//
//               }
//
//               @Override
//               public String getEmail() {
//                   return email ;
//               }
//
//               @Override
//               public void savePasword(String password) {
//
//               }
//
//               @Override
//               public String getPassword() {
//                   return password;
//               }
//
//               @Override
//               public void invalidate() {
//
//                   if (authenticationListener != null) {
//                       authenticationListener.onUserLoggedOut();
//                   }
//               }
//
//               @Override
//               public void saveToken(String token) {
//
//               }
//           };
//       }
//       return  session;
//    }
//
//    public interface AuthenticationListener {
//        void onUserLoggedOut();
//    }
//
//    public void setAuthenticationListener(AuthenticationListener listener) {
//        this.authenticationListener = listener;
//    }
//
//
//    public void setInternetConnectionListener(InternetConnectionListener listener) {
//        mInternetConnectionListener = listener;
//    }
//
//    public void removeInternetConnectionListener() {
//        mInternetConnectionListener = null;
//    }
//
//    public ApiService getApiService() {
//
//        if (apiService==null){
//            apiService = provideRetrofit(ApiService.URL).create(ApiService.class);
//        }
//        return apiService;
//    }
//
//    private boolean isInternetAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    private Retrofit provideRetrofit(String url){
//        return new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create(new Gson()))
//                .build();
//    }
//
//
//    private OkHttpClient  provideOkHttpClient(){
//        OkHttpClient.Builder okhttpClientBuilder=new OkHttpClient.Builder();
//        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
//        okhttpClientBuilder.readTimeout(30,TimeUnit.SECONDS);
//        okhttpClientBuilder.writeTimeout(30,TimeUnit.SECONDS);
//
//
//        okhttpClientBuilder.addInterceptor(new ConnectionInterceptor() {
//            @Override
//            public boolean isInternetAvailable() {
//                return App.this.isInternetAvailable();
//            }
//
//            @Override
//            public void onInternetUnavailable() {
//
//                mInternetConnectionListener.onInternetUnavailable();
//            }
//
//            @Override
//            public void onCacheUnavailable() {
//
//                if (mInternetConnectionListener != null) {
//                    mInternetConnectionListener.onCacheUnavailable();
//                }
//            }
//        } );
//
//        okhttpClientBuilder.cache(getCache());
//        return  okhttpClientBuilder.build();
//    }
//
//
//    public Cache getCache()  {
//
//        File cacheDir=new File(getCacheDir(),"cache");
//
//        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE );
//
//        return cache;
//    }
//}
