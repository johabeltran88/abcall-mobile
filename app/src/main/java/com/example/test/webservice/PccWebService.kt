package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Pcc
import com.google.gson.Gson
import org.json.JSONObject

class PccWebService {
    fun create(token: String, companyId: String, consumerId: String, pcc: Pcc,
                       responseListener: Response.Listener<JSONObject>,
                       errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        val resource = "/companies/$companyId/consumers/$consumerId/pccs"
        return postRequest(resource, token, toJSONObject(pcc), responseListener, errorListener)
    }

    private fun toJSONObject(pcc: Pcc): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(pcc)
        return JSONObject(jsonString)
    }
}