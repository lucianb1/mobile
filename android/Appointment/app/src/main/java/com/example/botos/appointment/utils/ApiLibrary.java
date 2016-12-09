package com.example.botos.appointment.utils;

import android.util.Log;

import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.threadPool.DefaultExecutorSupplier;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Botos on 12/7/2016.
 */
public class ApiLibrary {

    public static void postParamRequest(final String requestURL, final HashMap<String, String> params, final AppointmentApiResponse<UserModel> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);

                    setParams(conn, params);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onUserSuccessBlock(conn, responseApi);
                    }
                    else {
                        onUserFailureBlock(conn, responseApi);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (responseApi != null)
                                responseApi.onFailure(e.getMessage());
                        }
                    });
                }

            }
        });

    }

    private static void setHeaders(HttpURLConnection conn, HashMap<String, String> map) {
        for (HashMap.Entry<String, String> entry : map.entrySet())
        {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private static void setParams(HttpURLConnection conn, HashMap<String, String> params) {
        try {
            JSONObject parent = new JSONObject();
            for (HashMap.Entry<String, String> entry : params.entrySet())
            {
                parent.put(entry.getKey(), entry.getValue());
            }

            OutputStream os = conn.getOutputStream();
            os.write(parent.toString().getBytes("UTF-8"));
            os.close();
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    private static void onUserSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<UserModel> responseApi) throws JSONException {
        String response = "";
        try {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }

        } catch (Exception e) {
            Log.d("error: ", e.getMessage());
        }
        final JSONObject jsonObject = new JSONObject(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {

                if (responseApi != null)
                    responseApi.onSuccess(ConvertFromJson.toUserModel(jsonObject));
            }
        });
    }

    private static void onUserFailureBlock(final HttpURLConnection conn, final AppointmentApiResponse<UserModel> responseApi) {
        String response = "";
        InputStream stream = conn.getErrorStream();
        if (stream != null) {
            response = convertStreamToString(stream);
        }
        final String finalResponse = response;
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                if (responseApi != null)
                    responseApi.onFailure(finalResponse);
            }
        });
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
//
//    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//        boolean first = true;
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if (first)
//                first = false;
//            else
//                result.append("&");
//
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//        }
//
//        return result.toString();
//    }
//
//    public static void downloadImage(final String url, final IDWApiResponse<Bitmap> responseApi) {
//        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                final Bitmap bitmap = download_Image(url);
//                if (bitmap != null) {
//                    info.androidhive.volleyexamples.utils.Cache.getInstance().getLru().put(url, bitmap);
//                    DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (responseApi != null)
//                                responseApi.onSuccess(bitmap);
//                        }
//                    });
//                } else {
//                    DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (responseApi != null)
//                                responseApi.onFailure();
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//    private static Bitmap download_Image(String url) {
//        Bitmap bmp = null;
//        try{
//            URL ulrn = new URL(url);
//            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
//            InputStream is = con.getInputStream();
//            bmp = BitmapFactory.decodeStream(is);
//        }catch(Exception e){
//            Log.d("error: ", e.getMessage());
//        }
//        return bmp;
//    }
}
