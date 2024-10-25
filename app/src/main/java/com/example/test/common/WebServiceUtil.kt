package com.example.test.common

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.model.Login
import com.google.gson.Gson
import org.json.JSONObject
import java.util.Objects

fun getRequest(
    resource: String,
    token: String,
    responseListener: Response.Listener<String>,
    errorListener: Response.ErrorListener
): StringRequest {
    return object : StringRequest(
        Method.GET, GatewayUtil.BASE_URL + resource, responseListener, errorListener
    ) {
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"
            return headers
        }
    }
}

fun postRequest(
    resource: String,
    body: JSONObject,
    responseListener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener
): JsonObjectRequest {
    return JsonObjectRequest(
        Request.Method.POST, GatewayUtil.BASE_URL + resource, body, responseListener, errorListener
    )
}

fun postRequest(
    resource: String,
    token: String,
    body: JSONObject,
    responseListener: Response.Listener<JSONObject>,
    errorListener: Response.ErrorListener
): JsonObjectRequest {
    return object : JsonObjectRequest(
        Request.Method.POST, GatewayUtil.BASE_URL + resource, body, responseListener, errorListener
    ) {
        override fun getHeaders(): Map<String, String> {
            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"
            return headers
        }
    }
}

class GatewayUtil {

    companion object {
        const val BASE_URL = "http://abcall-load-balancer-1563043008.us-east-1.elb.amazonaws.com"
    }

}