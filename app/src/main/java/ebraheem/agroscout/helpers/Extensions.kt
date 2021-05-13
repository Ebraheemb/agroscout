package ebraheem.agroscout.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import ebraheem.agroscout.R
import ebraheem.agroscout.ui.main.MainFragment


fun ViewGroup.inflate(resource:Int, attach:Boolean = false) : View {
    return LayoutInflater.from(context).inflate(resource,this,attach)
}

fun AppCompatActivity.withTransaction(transaction: FragmentTransaction.() -> FragmentTransaction) {
    val trans = supportFragmentManager.beginTransaction()
    trans.transaction()
    trans.commit()
}
