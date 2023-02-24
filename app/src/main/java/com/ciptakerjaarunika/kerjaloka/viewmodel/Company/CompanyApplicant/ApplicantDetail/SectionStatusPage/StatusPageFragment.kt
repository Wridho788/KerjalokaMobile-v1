package com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionStatusPage

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.api.companyApplicant.*
import com.ciptakerjaarunika.kerjaloka.config.config
import com.ciptakerjaarunika.kerjaloka.databinding.FragmentStatusPageBinding
import com.ciptakerjaarunika.kerjaloka.enum.ApplicanStatusType
import com.ciptakerjaarunika.kerjaloka.model.User.GoogleLoginRequest
import com.ciptakerjaarunika.kerjaloka.session.SessionManager
import com.ciptakerjaarunika.kerjaloka.viewmodel.Company.CompanyApplicant.ApplicantDetail.SectionStatusPage.BottomSheet.UbahStatusFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StatusPageFragment(
    private val applicantNo: Long,
    var applicantName: String,
    var applicantEmail: String,
    private var applicationStatusNo: Int
) : Fragment(), iStatusPage, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: FragmentStatusPageBinding
    lateinit var datePicker: DatePickerHelper
    lateinit var timePicker: TimePicker
    private var googleToken: String = ""
    var dateInterview = ""
    var emailAttendess = applicantEmail
    var startInterview = ""
    var endInterview = ""
    var expired = ""

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
        private var mAuth: FirebaseAuth? = null
        val Req_Code: Int = 123
        val firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatusPageBinding.inflate(layoutInflater)
        val view = binding.root

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestScopes(Scope(Scopes.DRIVE_APPFOLDER))
            .requestServerAuthCode(getString(R.string.default_web_client_id))
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

        mGoogleSignInClient = GoogleSignIn.getClient(
            context!!, gso
        )
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datePicker = DatePickerHelper(context!!)

        binding.btnBackStatus.setOnClickListener {
            activity?.onBackPressed()
        }

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.btnStatus.setOnClickListener {
            ubahStatusModal()
        }
        if (applicationStatusNo == ApplicanStatusType.ShortList.value) {
            binding.statusChange.text = "Terpilih"
        } else if (applicationStatusNo == ApplicanStatusType.Test.value) {
            binding.statusChange.text = "Dalam Test"
        } else if (applicationStatusNo == ApplicanStatusType.Interview.value) {
            binding.statusChange.text = "Interview"
        } else if (applicationStatusNo == ApplicanStatusType.Accepted.value) {
            binding.statusChange.text = "Diterima"
        } else if (applicationStatusNo == ApplicanStatusType.Rejected.value) {
            binding.statusChange.text = "Ditolak"
        } else {
            binding.statusChange.text = "CV Bank"
        }


    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance()
        val d = cal.get(Calendar.DAY_OF_MONTH)
        val m = cal.get(Calendar.MONTH)
        val y = cal.get(Calendar.YEAR)
        datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
            override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {

                val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "${dayofMonth}"
                var expiredOn = "${year}-${month}-${dayofMonth}"
                val mon = month + 1
                val monthStr = if (mon < 10) "0${mon}" else "${mon}"
                var datePick = "$dayStr-$monthStr-$year"
                dateInterview = expiredOn.toString()
                Log.d("dateInterview", dateInterview.toString())
                binding.textDate.text = "$datePick"
            }
        })
    }

    fun ubahStatusModal() {
        val sheet = UbahStatusFragment(this@StatusPageFragment)
        activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "StatusFragment") }
    }

    private fun signIn() {
        if (mGoogleSignInClient != null) {
            signInIntent()
        } else {
            Toast.makeText(context, "Membutuhkan Google SignIn", Toast.LENGTH_SHORT).show()
            signInIntent()
        }
    }

    fun signInIntent() {
        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            try {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val authCode = account!!.serverAuthCode
            Log.d("authCode", authCode.toString())
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            Log.d("err", "handleSignInResult:" + e.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val currentDate = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = currentDate
        cal.add(Calendar.HOUR, +1)
        val oneHourBack: Date = cal.time
        val dtfInput = DateTimeFormatter.ofPattern("E MMM d H:m:s O u", Locale.ENGLISH)
        val odt = OffsetDateTime.parse(oneHourBack.toString(), dtfInput)
        this.expired = odt.toString()

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val text = "${account.idToken}${config().authKey}${4}"
                val crypt = MessageDigest.getInstance("MD5")
                crypt.update(text.toByteArray())
                val hash = BigInteger(1, crypt.digest()).toString(16)
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }
                    val token = task.result
                    SessionManager(context).device_token = token

                })

                val googleRequest = GoogleLoginRequest(
                    account.idToken.toString(),
                    oneHourBack.toString(),
                    hash,
                    deviceToken = SessionManager(context).device_token
                )
                this.googleToken = googleRequest.token


            }
        }
    }

    override fun changeStatus(status: Int) {
        if (status == ApplicanStatusType.ShortList.value) {
            binding.statusChange.text = "Terpilih"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                ShortlistStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Test.value) {
            binding.statusChange.text = "Dalam Test"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                TestStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Interview.value) {
            binding.statusChange.text = "Interview"
            binding.sectionInterview.visibility = View.VISIBLE
            binding.btnChangeStatus.setOnClickListener {
                var locationInterview = binding.txtInputLocation.text.toString()
                val nameInterview = binding.txtInputInterviewer.text.toString()
                if (dateInterview.toString().isNullOrEmpty()) {
                    Toast.makeText(context, "Silahkan Atur Jadwal Interview", Toast.LENGTH_SHORT)
                        .show()
                } else if (dateInterview.toString().isNullOrEmpty()) {
                    Toast.makeText(
                        context, "Silahkan Atur Jadwal Interview", Toast.LENGTH_SHORT
                    ).show()
                } else if (nameInterview.toString().isNullOrEmpty()) {
                    Toast.makeText(context, "Silahkan Isi Nama Interviewer", Toast.LENGTH_SHORT)
                        .show()
                } else if (locationInterview.toString().isNullOrEmpty()) {
                    Toast.makeText(
                        context, "Silahkan Isi Lokasi Interview", Toast.LENGTH_SHORT
                    ).show()
                }
                InterviewStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Accepted.value) {
            binding.statusChange.text = "Diterima"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                AcceptedStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.Rejected.value) {
            binding.statusChange.text = "Ditolak"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                RejectedStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else if (status == ApplicanStatusType.CVBank.value) {
            binding.statusChange.text = "CV Bank"
            binding.sectionInterview.visibility = View.GONE
            binding.btnChangeStatus.setOnClickListener {
                CvbankStatus(context, applicantNo) {
                    if (it != null) {
                        if (it.code == 210) {
                            activity?.onBackPressed()
                        }
                    }
                }
            }
        } else {
            status
        }
        binding.btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        binding.btnTimePicker.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                var timeFormatter = SimpleDateFormat("HH:mm").format(cal.time)
                this.startInterview = timeFormatter
                Log.d("Interview Start", startInterview)
                binding.textBegin.text = timeFormatter.toString()

            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.btnTimePickerEnd.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                var endTimeFormatter = SimpleDateFormat("HH:mm").format(cal.time)
                this.endInterview = endTimeFormatter.toString()
                binding.textEnd.text = endTimeFormatter.toString()
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}

interface iStatusPage {
    fun changeStatus(status: Int)
}