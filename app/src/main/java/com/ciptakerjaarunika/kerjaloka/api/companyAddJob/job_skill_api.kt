package com.ciptakerjaarunika.kerjaloka.api.companyAddJob

import android.content.Context
import com.ciptakerjaarunika.kerjaloka.model.Data.SkillFilter
import com.ciptakerjaarunika.kerjaloka.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class Skills {

    interface GetSkills {
        @GET("data/skills")
        fun GetData(): Call<List<SkillFilter>?>
    }

    fun GetSkill(context: Context?, onResult: (List<SkillFilter>?) -> Unit) {
        val retrofit = ServiceBuilder(context).GET(GetSkills::class.java)

        retrofit.GetData().enqueue(
            object : Callback<List<SkillFilter>?> {
                override fun onResponse(
                    call: Call<List<SkillFilter>?>, response: Response<List<SkillFilter>?>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<List<SkillFilter>?>, t: Throwable) {
                    onResult(null)
                }
            }
        )
    }

}