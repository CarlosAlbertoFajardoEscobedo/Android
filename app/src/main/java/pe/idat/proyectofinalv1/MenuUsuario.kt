package pe.idat.proyectofinalv1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.idat.proyectofinalv1.databinding.ActivityMenuUsuarioBinding
import pe.idat.proyectofinalv1.view.cliente.activity.LoginActivity
import pe.idat.proyectofinalv1.viewmodel.ClienteViewModel
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel

class MenuUsuario : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuUsuarioBinding
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clienteViewModel=ViewModelProvider(this)[ClienteViewModel::class.java]
        itemViewModel=ViewModelProvider(this)[ItemViewModel::class.java]

        setSupportActionBar(binding.appBarMenuUsuario.toolbar)



        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu_usuario)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_productos, R.id.nav_carrito, R.id.nav_orden
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        mostrarInfoAuth()
        limpiarCarrito()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_usuario, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu_usuario)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun mostrarInfoAuth(){
        val tvNomUser: TextView =binding.navView.getHeaderView(0).findViewById(R.id.tvnavnomapll)
        val tvEmailUser: TextView =binding.navView.getHeaderView(0).findViewById(R.id.tvnavemail)

        clienteViewModel.obtener().observe(this,
            Observer{
                    cliente->cliente?.let {
                tvNomUser.text=cliente.nombres+" "+cliente.apellidos
                tvEmailUser.text=cliente.correo
            }
            })

    }

    private fun limpiarCarrito() {
        itemViewModel.eliminarTodo()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val idItem=item.itemId
        if (idItem==R.id.action_settings){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}