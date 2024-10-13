package com.example.test.common

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

/**
 * Esta función se utiliza para realizar peticiones de tipo GET a un recurso especifico
 */
fun getRequest(
    resource: String,
    responseListener: Response.Listener<String>,
    errorListener: Response.ErrorListener
): StringRequest {
    return StringRequest(
        Request.Method.GET, GatewayUtil.BASE_URL + resource, responseListener, errorListener
    )
}

/**
 * Esta función se utiliza para realizar peticiones de tipo GET a un recurso especifico
 */

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

class GatewayUtil {

    companion object {
        const val BASE_URL = ""
    }

}