package realmcrud.sodhankit.com.myvolleysample.activity;


import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import realmcrud.sodhankit.com.myvolleysample.R;
import realmcrud.sodhankit.com.myvolleysample.other.MySingleton;
import realmcrud.sodhankit.com.myvolleysample.other.UrlConstants;

public class MainActivity extends AppCompatActivity {

    ImageView image1,image2;
    NetworkImageView networkImageView;
    TextView tvJsonArrayRequest, tvJsonObjectRequest, tvSimpleRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1=(ImageView)findViewById(R.id.imageview);
        image2=(ImageView)findViewById(R.id.imageview1);
        networkImageView=(NetworkImageView)findViewById(R.id.ivNetworkImageView);
        tvJsonArrayRequest=(TextView)findViewById(R.id.tvJsonArrayRequest);
        tvJsonObjectRequest=(TextView)findViewById(R.id.tvJsonObjectRequest);
        tvSimpleRequest=(TextView)findViewById(R.id.tvSimpleRequest);
    }
    public void onSimpleRequest(View view)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                UrlConstants.SIMPLE_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tvSimpleRequest.setText("Response : "+response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Ankit Sodha");
                params.put("email", "sodhankit@hotmail.com");
                params.put("mobile", "1234567980");
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onImageRequest(View view)
    {
        ImageRequest imageRequest=new ImageRequest(UrlConstants.IMAGE_URL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        image1.setImageBitmap(response);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        image1.setImageResource(R.mipmap.ic_launcher);
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }

    public void onImageLoader(View view)
    {
        ImageLoader mImageLoader=MySingleton.getInstance(this).getImageLoader();
        mImageLoader.get(UrlConstants.IMAGE_URL,
                ImageLoader.getImageListener(image2,R.mipmap.ic_launcher,R.mipmap.ic_launcher));

    }

    public void onNetworkImageView(View view)
    {
        ImageLoader mImageLoader=MySingleton.getInstance(this).getImageLoader();
        networkImageView.setImageUrl(UrlConstants.IMAGE_URL,mImageLoader);
    }

    public void onJsonArrayRequest(View view)
    {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(UrlConstants.JSON_ARRAY_REQUEST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tvJsonArrayRequest.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public void onJsonObjectRequest(View view)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,
                UrlConstants.JSON_OBJECT_REQUEST, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvJsonObjectRequest.setText(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvJsonArrayRequest.setText(error.toString());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

}
