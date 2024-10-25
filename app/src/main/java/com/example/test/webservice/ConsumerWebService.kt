package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
import org.json.JSONObject

class ConsumerWebService {
    companion object {
        const val RESOURCE = "/consumers"
    }

    fun getByToken(
        token: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return getRequest(RESOURCE, token, responseListener, errorListener)
    }

}