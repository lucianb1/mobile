package com.example.botos.appointment.utils;

import android.util.Log;

import com.example.botos.appointment.models.CompanyModel;
import com.example.botos.appointment.models.DomainModel;
import com.example.botos.appointment.models.MemberModel;
import com.example.botos.appointment.models.ServicesModel;
import com.example.botos.appointment.models.UserModel;
import com.example.botos.appointment.platform.AppointmentApiResponse;
import com.example.botos.appointment.threadPool.DefaultExecutorSupplier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Botos on 12/7/2016.
 */
public class ApiLibrary {

    private static void setupConnection(HttpURLConnection conn, String type) throws ProtocolException {
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod(type);
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

    private static String setParamsForGetRequest(String url, HashMap<String, String> params) {
        boolean firstTime = true;
        url += "?";
        for (HashMap.Entry<String, String> entry : params.entrySet())
        {
            if (firstTime) {
                url += entry.getKey() + "=" + entry.getValue();
                firstTime = false;
            } else {
                url += "&" + entry.getKey() + "=" + entry.getValue();
            }
        }
        return url;
    }

    private static void onFailureBlock(final HttpURLConnection conn, final AppointmentApiResponse responseApi) {
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

    public static void postRequestUserModel(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<UserModel> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.POST);
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);

                    if (header != null)
                        setHeaders(conn, header);
                    if (params != null)
                        setParams(conn, params);

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onUserSuccessBlock(conn, responseApi);
                    } else {
                        onFailureBlock(conn, responseApi);
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

    public static void postRequestString(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<String> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.POST);

                    if (header != null)
                        setHeaders(conn, header);
                    if (params != null)
                        setParams(conn, params);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onStringSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onStringSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<String> responseApi) {
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
        try {
            final JSONObject jsonObject = new JSONObject(response);
            DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
                @Override
                public void run() {

                    if (responseApi != null)
                        responseApi.onSuccess(jsonObject.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
                @Override
                public void run() {

                    if (responseApi != null)
                        responseApi.onSuccess("Email Success");
                }
            });
        }
    }

    public static void putRequestString(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<String> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.PUT);

                    if (header != null)
                        setHeaders(conn, header);
                    if (params != null)
                        setParams(conn, params);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onStringSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    public static void getRequestDomains(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<ArrayList<DomainModel>> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.GET);

                    if (header != null)
                        setHeaders(conn, header);
                    if (params != null)
                        setParams(conn, params);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onDomainsSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onDomainsSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<ArrayList<DomainModel>> responseApi) throws JSONException {
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
        final JSONArray jsonArray = new JSONArray(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<DomainModel> domainModels = new ArrayList<>();
                if (responseApi != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            domainModels.add(ConvertFromJson.toDomainModel(jsonArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    responseApi.onSuccess(domainModels);
                }
            }
        });
    }

    public static void getRequestCompanies(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<ArrayList<CompanyModel>> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    if (params != null) {
                        url = new URL(setParamsForGetRequest(requestURL, params));
                    } else {
                        url = new URL(requestURL);
                    }
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.GET);

                    if (header != null)
                        setHeaders(conn, header);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onCompaniesSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onCompaniesSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<ArrayList<CompanyModel>> responseApi) throws JSONException {
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
        final JSONArray jsonArray = new JSONArray(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<CompanyModel> companyModels = new ArrayList<>();
                if (responseApi != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            companyModels.add(ConvertFromJson.toCompanyModel(jsonArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    responseApi.onSuccess(companyModels);
                }
            }
        });
    }

    public static void getRequestMembrers(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<ArrayList<MemberModel>> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    if (params != null) {
                        url = new URL(setParamsForGetRequest(requestURL, params));
                    } else {
                        url = new URL(requestURL);
                    }
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.GET);

                    if (header != null)
                        setHeaders(conn, header);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onMembersSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onMembersSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<ArrayList<MemberModel>> responseApi) throws JSONException {
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
        final JSONArray jsonArray = new JSONArray(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<MemberModel> memberModels = new ArrayList<>();
                if (responseApi != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            memberModels.add(ConvertFromJson.toMemberModel(jsonArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    responseApi.onSuccess(memberModels);
                }
            }
        });
    }

    public static void getRequestMembrerServices(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<ArrayList<ServicesModel>> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    if (params != null) {
                        url = new URL(setParamsForGetRequest(requestURL, params));
                    } else {
                        url = new URL(requestURL);
                    }
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.GET);

                    if (header != null)
                        setHeaders(conn, header);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onMemberServicesSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onMemberServicesSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<ArrayList<ServicesModel>> responseApi) throws JSONException {
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
        final JSONArray jsonArray = new JSONArray(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                ArrayList<ServicesModel> servicesModels = new ArrayList<>();
                if (responseApi != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            servicesModels.add(ConvertFromJson.toMemberServicesModel(jsonArray.getJSONObject(i)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    responseApi.onSuccess(servicesModels);
                }
            }
        });
    }

    public static void getRequestMembrerTimeTable(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<int[]> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    if (params != null) {
                        url = new URL(setParamsForGetRequest(requestURL, params));
                    } else {
                        url = new URL(requestURL);
                    }
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.GET);

                    if (header != null)
                        setHeaders(conn, header);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        onMemberTimeTableSuccessBlock(conn, responseApi);
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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

    private static void onMemberTimeTableSuccessBlock(final HttpURLConnection conn, final AppointmentApiResponse<int[]> responseApi) throws JSONException {
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
        final JSONObject baseJsonObj = new JSONObject(response);
        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int[] ints = new int[baseJsonObj.getJSONArray("timetable").length()];
                    if (responseApi != null) {
                        for (int i = 0; i < baseJsonObj.getJSONArray("timetable").length(); i++) {
                            ints[i] = baseJsonObj.getJSONArray("timetable").getInt(i);
                        }

                        responseApi.onSuccess(ints);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void postRequest(final String requestURL, final HashMap<String, String> params, final HashMap<String, String> header, final AppointmentApiResponse<String> responseApi) {
        DefaultExecutorSupplier.getInstance().getServerRequestsThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(requestURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    setupConnection(conn, Constants.POST);

                    if (header != null)
                        setHeaders(conn, header);
                    if (params != null)
                        setParams(conn, params);

                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        if (responseApi != null)
                            responseApi.onSuccess(String.valueOf(HttpsURLConnection.HTTP_OK));
                    }
                    else {
                        onFailureBlock(conn, responseApi);
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
