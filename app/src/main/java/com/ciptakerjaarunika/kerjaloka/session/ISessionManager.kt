package com.ciptakerjaarunika.kerjaloka.session

import MessageListener
import android.content.Context
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_model
import com.ciptakerjaarunika.kerjaloka.model.User.*
import com.microsoft.signalr.HubConnection
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import java.util.concurrent.Flow

interface ISessionManager{
    var access_token : String?
    var user: User?
    var jobseeker: Jobseeker?
    var company: Company?
    var companyAdditional: CompanyAdditional?
    var jobseekerAdditional: JobseekerAdditional?
    var chatData : chat_data?
    var deviceId : String

    suspend fun clearData()
    fun refreshChat(hubConnection: HubConnection)
}