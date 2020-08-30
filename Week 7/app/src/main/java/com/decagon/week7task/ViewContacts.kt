package com.decagon.week7task

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.decagon.week7task.fragments.EditFragment
import com.decagon.week7task.fragments.ReadFragment
import kotlinx.android.synthetic.main.fragment_read.*

class ViewContacts : AppCompatActivity() {
    //delayed but promising to initialize fragmentTransaction
    private lateinit var fragmentTransaction : FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_contacts)


        val firstName = intent.getStringExtra("FIRST_NAME")
        val lastName = intent.getStringExtra("LAST_NAME")
        val phone = intent.getStringExtra("PHONE")


        val bundle = Bundle()
        bundle.putString("FIRST_NAME", firstName)
        bundle.putString("LAST_NAME", lastName)
        bundle.putString("PHONE", phone.toString())



        var fragment: Fragment
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragment = ReadFragment()
        // set Fragment class Arguments
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.read_frag, fragment)
        fragmentTransaction.commit()

//        private fun setCurrentFragment(fragment : Fragment) = supportFragmentManager.beginTransaction().apply {
//            replace(R.id.fl_wrapper, fragment)
//            commit()
//        }
    }


}