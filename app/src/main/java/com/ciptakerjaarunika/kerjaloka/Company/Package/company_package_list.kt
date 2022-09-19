package com.ciptakerjaarunika.kerjaloka.Company.Package

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.historyListAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Adapter.myPackageAdapter
import com.ciptakerjaarunika.kerjaloka.Company.Package.Listener.ShowModalHistory
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.ProfilePage.ModalEdit.EditReligion

class company_package_list : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<myPackageAdapter.myPackage>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_company_package_list, container, false)

        val pckList = ArrayList<pack>()
        val order = ArrayList<order>()
        val pck = ArrayList<packages>()
        val mypck1 = pack(
            activatedOn = "2022-05-21T09:52:02",
            companyNo = 20211027141022,
            credit = 69420,
            expiredOn = "2023-05-21T00:00:00",
            order = order,
            orderNo = 167,
            packages = pck,
            period = 12,
            startOn = "2022-05-21T00:00:00",
            userPackageNo = 110
        )
        val ord1 = order(
            boughtOn = "2021-11-11T09:18:20",
            buyerNo = 20211027141022,
            companyNo = null,
            externalIdInvoice = "PAY-INV167",
            idInvoice = "618c7d6d59269dc3fb38207d",
            invoiceUrl = "https://checkout-staging.xendit.co/web/618c7d6d59269dc3fb38207d",
            itemNo = 27,
            itemTypeNo = 4,
            orderNo = 167,
            orderStatusNo = 1,
            paidOn = null,
            price = 200000,
            promoCode = null,
            quantity = 1,
            totalPaid = 0
        )
        order.add(ord1)
        val pack1 = packages(
            createdBy = 0,
            createdOn = "2021-10-23T14:25:49",
            expiredOn = null,
            isDiscoverable = true,
            isSuspended = false,
            isSystem = true,
            packageCredit = 5,
            packageDescription = "Cheap 5 Credit Package",
            packageDiscountedPrice = null,
            packageName = "Small Offering Package asdbkajshdk jahdkjahsd",
            packageNo = 30,
            packagePeriod = 1,
            packagePrice = 50000,
            packageTypeNo = 2,
            recurring = false,
            startOn = "2021-10-23T00:00:00",
            targetView = 0,
            updatedBy = null,
            updatedOn = null
        )
        pck.add(pack1)
        pckList.add(mypck1)

        val mypck2 = pack(
            activatedOn = "2022-05-21T09:52:02",
            companyNo = 20211027141022,
            credit = 69420,
            expiredOn = "2022-09-02T00:00:00",
            order = order,
            orderNo = 167,
            packages = pck,
            period = 12,
            startOn = "2022-08-02T00:00:00",
            userPackageNo = 110
        )
        val ord2 = order(
            boughtOn = "2021-11-11T09:18:20",
            buyerNo = 20211027141022,
            companyNo = null,
            externalIdInvoice = "PAY-INV167",
            idInvoice = "618c7d6d59269dc3fb38207d",
            invoiceUrl = "https://checkout-staging.xendit.co/web/618c7d6d59269dc3fb38207d",
            itemNo = 27,
            itemTypeNo = 4,
            orderNo = 167,
            orderStatusNo = 1,
            paidOn = null,
            price = 200000,
            promoCode = null,
            quantity = 1,
            totalPaid = 0
        )
        order.add(ord2)
        val pack2 = packages(
            createdBy = 0,
            createdOn = "2021-10-23T14:25:49",
            expiredOn = null,
            isDiscoverable = true,
            isSuspended = false,
            isSystem = true,
            packageCredit = 5,
            packageDescription = "Cheap 5 Credit Package",
            packageDiscountedPrice = null,
            packageName = "Job Promotion",
            packageNo = 30,
            packagePeriod = 1,
            packagePrice = 50000,
            packageTypeNo = 2,
            recurring = false,
            startOn = "2021-10-23T00:00:00",
            targetView = 0,
            updatedBy = null,
            updatedOn = null
        )
        pck.add(pack2)
        pckList.add(mypck2)

        val recyclerView = view.findViewById<RecyclerView>(R.id.myPackageRecycler)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        adapter = assignAdapter(pckList)
        recyclerView.adapter = adapter


        return view
    }

    internal fun assignAdapter(list: List<pack>): myPackageAdapter {
        return myPackageAdapter(requireContext(), list, object : ShowModalHistory {
            override fun showDetail(pack: pack) {
                val sheet = history_modal()
                Log.d("data", pack.orderNo.toString())
                activity?.let { it1 -> sheet.show(it1.supportFragmentManager, "DemoBottomSheetFragment") }
            }
        })
    }

    companion object {

    }
}