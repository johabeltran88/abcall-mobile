package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
import com.example.test.common.postRequest
import com.example.test.model.Pcc
import com.google.gson.Gson
import org.json.JSONObject

class NotificationWebService {
    fun getByPcc(
        token: String,
        pccId: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        val resource = "/pccs/$pccId"
        return getRequest(resource, token, responseListener, errorListener)
    }
}