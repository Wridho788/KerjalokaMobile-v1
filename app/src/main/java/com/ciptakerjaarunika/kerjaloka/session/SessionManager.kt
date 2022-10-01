package com.ciptakerjaarunika.kerjaloka.session

import MessageListener
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.model.User.*
import com.google.gson.Gson
import com.microsoft.signalr.Action1
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionState
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import java.io.Serializable
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList


class SessionManager (context: Context?) : ISessionManager{
   private val appContext : Context? =  context?.applicationContext

    companion object{
        const val SHARED_PREF_NAME = "com.ciptakerjaarunika.kerjaloka"
        const val ACCESS_TOKEN = "access_token"
        const val USER = "user"
        const val CHAT_DATA = "chat_data"
        const val JOBSEEKER = "Jobseeker"
        const val COMPANY= "company"
        const val COMPANY_ADDITIONAL= "ocmpanyadditional"
        const val JOBSEEKER_ADDITIONAL = "jobseekeradditionl"
        const val LATESTSEARCHJOB = "latest_search_job"
    }

    override var access_token: String?
        get() = getData(ACCESS_TOKEN)
        set(value) {setData(ACCESS_TOKEN, value) }

    override var user: User?
        get() = Gson().fromJson(getData(USER), User::class.java)
        set(value) {setData(USER, Gson().toJson(value))}

    override var latestSearchJob: List<Any>?
        get() = (Gson().fromJson(getData(LATESTSEARCHJOB), ArrayList::class.java))
        set(value) {setData(LATESTSEARCHJOB, Gson().toJson(value))}

    override var deviceId: String = ""
        get() = Settings.Secure.getString(appContext?.contentResolver,
            Settings.Secure.ANDROID_ID);

    override var chatData: chat_data?
        get() = Gson().fromJson(getData(CHAT_DATA), chat_data::class.java)
        set(value) {
            setData(CHAT_DATA, Gson().toJson(value))}

    override var company: Company?
        get() = Gson().fromJson(getData(COMPANY), Company::class.java)
        set(value) {setData(COMPANY, Gson().toJson(value))}

    override var companyAdditional: CompanyAdditional?
        get() = Gson().fromJson(getData(COMPANY_ADDITIONAL), CompanyAdditional::class.java)
        set(value) {setData(COMPANY_ADDITIONAL, Gson().toJson(value))}

    override var jobseeker: Jobseeker?
        get() = Gson().fromJson(getData(JOBSEEKER), Jobseeker::class.java)
        set(value) {setData(JOBSEEKER, Gson().toJson(value))}

    override var jobseekerAdditional: JobseekerAdditional?
        get() = Gson().fromJson(getData(JOBSEEKER_ADDITIONAL), JobseekerAdditional::class.java)
        set(value) {setData(JOBSEEKER_ADDITIONAL, Gson().toJson(value))}

    private fun getSharedPreference(): SharedPreferences? {
        return appContext?.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
    private fun getData(key: String) : String?{
        return getSharedPreference()?.getString(key, null)
    }
    private fun setData(key : String, value:String?){
        getSharedPreference()?.edit()?.putString(key,value)?.apply()
    }

    override suspend fun clearData() {
        getSharedPreference()?.edit()?.clear()?.apply()
    }

    override fun refreshChat(hubConnection: HubConnection) {
        if(SessionManager(appContext).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED){
            Log.d("HubConnection", "ReConnect")
            hubConnection.start().doOnComplete {
                Log.d("HubConnection", "Refresh Message")

                hubConnection.send("RefreshMessage", SessionManager(appContext).user!!.userNo.toString())
            }
        }
        else {
            Log.d("HubConnection", "Refresh Message")

            hubConnection.send("RefreshMessage", SessionManager(appContext).user!!.userNo.toString())
        }
    }
    override fun readSectionMessage(hubConnection: HubConnection, sectionNo : Int?) {
        if(sectionNo != null) {
            if (SessionManager(appContext).user != null && hubConnection.connectionState != HubConnectionState.CONNECTED) {
                Log.d("HubConnection", "ReConnect")
                hubConnection.start().doOnComplete { hubConnection.send("ReadSectionMessage",sectionNo.toString())}
            } else {
                hubConnection.send("ReadSectionMessage",sectionNo.toString())
            }
        }
    }
}

