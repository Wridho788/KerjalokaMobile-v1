package com.ciptakerjaarunika.kerjaloka.session

import com.ciptakerjaarunika.kerjaloka.model.Interview.chat_data
import com.ciptakerjaarunika.kerjaloka.model.User.*
import com.microsoft.signalr.HubConnection

interface ISessionManager {
    var device_token: String?
    var access_token: String?
    var user: User?
    var jobseeker: Jobseeker?
    var company: Company?
    var companyAdditional: CompanyAdditional?
    var jobseekerAdditional: JobseekerAdditional?
    var chatData: chat_data?
    var deviceId: String
    var latestSearchJob: List<Any>?
    var latestGeneralSearch: List<Any>?
    var latestCompanySearch: List<Any>?

    suspend fun clearData()
    fun refreshChat(hubConnection: HubConnection)
    fun readSectionMessage(hubConnection: HubConnection, sectionNo: Int?)
}