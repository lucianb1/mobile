package com.example.botos.appointment.utils;

/**
 * Created by Botos on 12/7/2016.
 */
public class ApiLibrary {

//    public static void performPostCall(final String requestURL, final IDWApiResponse<ArrayList<NewsTimelineModel>> responseApi) {
//        DefaultExecutorSupplier.getInstance().getImageRequestsThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                URL url;
//                try {
//                    url = new URL(requestURL);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                    conn.setReadTimeout(15000);
//                    conn.setConnectTimeout(15000);
//                    conn.setRequestMethod("POST");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//
//                    setHeaders(conn);
//                    setParams(conn);
//
//                    int responseCode=conn.getResponseCode();
//
//                    if (responseCode == HttpsURLConnection.HTTP_OK) {
//                        onSuccessBlock(conn, responseApi);
//                    }
//                    else {
//                        onFailureBlock(conn, responseApi);
//                    }
//                } catch (final Exception e) {
//                    e.printStackTrace();
//                    DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (responseApi != null)
//                                responseApi.onFailure(e.getMessage());
//                        }
//                    });
//                }
//
//            }
//        });
//
//    }
//
//    private static void setHeaders(HttpURLConnection conn) {
//        conn.setRequestProperty(Constants.API_KEY, Constants.API_KEY_VALUE);
//        conn.setRequestProperty(Constants.API_VERSION, Constants.API_VERSION_VALUE);
//    }
//
//    private static void setParams(HttpURLConnection conn) {
//        try {
//            JSONObject parent = new JSONObject();
//            parent.put("page", "1");
//            parent.put("pagesize", "20");
//
//            OutputStream os = conn.getOutputStream();
//            os.write(parent.toString().getBytes("UTF-8"));
//            os.close();
//        } catch (Exception e) {}
//    }
//
//    private static void onSuccessBlock(final HttpURLConnection conn, final IDWApiResponse<ArrayList<NewsTimelineModel>> responseApi) {
//        final ArrayList<NewsTimelineModel> newsModels = new ArrayList<>();
//        try {
//            String line;
//            String response = "";
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            while ((line = br.readLine()) != null) {
//                response += line;
//            }
//
//            JSONObject jsonObject = new JSONObject(response);
//            try {
//                for (int i = 0; i < jsonObject.getJSONArray("News").length(); i++) {
//                    newsModels.add(getNewsModel((JSONObject) jsonObject.getJSONArray("News").get(i)));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            Log.d("error: ", e.getMessage());
//        }
//        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
//            @Override
//            public void run() {
//                if (responseApi != null)
//                    responseApi.onSuccess(newsModels);
//            }
//        });
//    }
//
//    private static void onFailureBlock(final HttpURLConnection conn, final IDWApiResponse<ArrayList<NewsTimelineModel>> responseApi) {
//        String response = "";
//        InputStream stream = conn.getErrorStream();
//        if (stream != null) {
//            response = convertStreamToString(stream);
//        }
//        final String finalResponse = response;
//        DefaultExecutorSupplier.getInstance().forMainThreadTasks().execute(new Runnable() {
//            @Override
//            public void run() {
//                if (responseApi != null)
//                    responseApi.onFailure(finalResponse);
//            }
//        });
//    }
//
//    private static String convertStreamToString(InputStream is) {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//
//        String line = null;
//        try {
//            while ((line = reader.readLine()) != null) {
//                sb.append(line).append('\n');
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return sb.toString();
//    }
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
