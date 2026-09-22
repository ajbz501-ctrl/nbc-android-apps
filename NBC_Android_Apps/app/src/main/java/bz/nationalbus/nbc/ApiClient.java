package bz.nationalbus.nbc;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public final class ApiClient {
    public interface Callback { void done(JSONObject result); void error(Exception e); }
    private static final String BASE = "https://nationalbusbelize.com";
    private final Context context; private final SharedPreferences prefs; private final ExecutorService executor = Executors.newCachedThreadPool();
    public ApiClient(Context c) { context=c.getApplicationContext(); prefs=context.getSharedPreferences("nbc_session",0); }
    public boolean loggedIn() { return prefs.getString("cookie", "").length() > 0; }
    public String baseUrl() { return BASE; }
    public void logout() { prefs.edit().clear().apply(); }
    public void call(String path, JSONObject params, Callback callback) {
        executor.execute(() -> { try { callback.done(request(path, params)); } catch(Exception e) { callback.error(e); } });
    }
    public void login(String user, String password, Callback callback) {
        executor.execute(() -> { try {
            String db=discoverDatabase();
            JSONObject p=new JSONObject(); p.put("db",db); p.put("login",user); p.put("password",password);
            URL url=new URL(BASE+"/web/session/authenticate"); HttpURLConnection con=(HttpURLConnection)url.openConnection(); con.setRequestMethod("POST"); con.setDoOutput(true); con.setRequestProperty("Content-Type","application/json");
            JSONObject body=new JSONObject().put("jsonrpc","2.0").put("method","call").put("params",p).put("id",1); write(con,body.toString());
            int code=con.getResponseCode(); String json=read(code>=400?con.getErrorStream():con.getInputStream()); JSONObject out=new JSONObject(json);
            if(code<400 && out.optJSONObject("result")!=null && !out.optJSONObject("result").optBoolean("uid",false)) { /* Odoo returns uid as number; handled below */ }
            String cookie=con.getHeaderField("Set-Cookie"); if(cookie!=null) prefs.edit().putString("cookie",cookie.split(";",2)[0]).apply();
            callback.done(out);
        } catch(Exception e) { callback.error(e); } });
    }
    private String discoverDatabase() throws Exception {
        URL url=new URL(BASE+"/web/database/list"); HttpURLConnection con=(HttpURLConnection)url.openConnection(); con.setRequestMethod("POST"); con.setDoOutput(true); con.setConnectTimeout(10000); con.setReadTimeout(15000); con.setRequestProperty("Content-Type","application/json");
        JSONObject body=new JSONObject().put("jsonrpc","2.0").put("method","call").put("params",new JSONObject()).put("id",1); write(con,body.toString());
        int code=con.getResponseCode(); String raw=read(code>=400?con.getErrorStream():con.getInputStream());
        if(code>=400) throw new IOException("NBC sign-in service returned HTTP "+code);
        try { JSONObject out=new JSONObject(raw); org.json.JSONArray result=out.optJSONArray("result"); if(result!=null&&result.length()>0)return result.getString(0); } catch(Exception ignored) {}
        throw new IOException("NBC sign-in service did not return a database");
    }
    private JSONObject request(String path, JSONObject params) throws Exception {
        URL url=new URL(BASE+path); HttpURLConnection con=(HttpURLConnection)url.openConnection(); con.setRequestMethod("POST"); con.setDoOutput(true); con.setConnectTimeout(15000); con.setReadTimeout(25000); con.setRequestProperty("Content-Type","application/json");
        String cookie=prefs.getString("cookie",""); if(!cookie.isEmpty()) con.setRequestProperty("Cookie",cookie);
        JSONObject body=new JSONObject().put("jsonrpc","2.0").put("method","call").put("params",params==null?new JSONObject():params).put("id",System.currentTimeMillis()); write(con,body.toString());
        int code=con.getResponseCode(); String raw=read(code>=400?con.getErrorStream():con.getInputStream()); if(code>=400) throw new IOException("HTTP "+code+": "+raw); return new JSONObject(raw);
    }
    private static void write(HttpURLConnection c,String s)throws Exception{try(OutputStream o=c.getOutputStream()){o.write(s.getBytes(StandardCharsets.UTF_8));}}
    private static String read(InputStream in)throws Exception{if(in==null)return "";try(BufferedReader r=new BufferedReader(new InputStreamReader(in,StandardCharsets.UTF_8))){StringBuilder b=new StringBuilder();String x;while((x=r.readLine())!=null)b.append(x);return b.toString();}}
}
