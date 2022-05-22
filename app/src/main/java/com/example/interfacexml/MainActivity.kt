package com.example.interfacexml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interfacexml.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    /*
    Nos codelabs anteriores, você viu o método onCreate() na classe MainActivity. Esse método é
    um dos primeiros a serem chamados quando o app é iniciado e a MainActivity é inicializada.
    Em vez de chamar findViewById() para cada View no seu app, você criará e inicializará um
    objeto de vinculação apenas uma vez.
    */

    /*
    Esta linha declara uma variável de nível superior na classe para o objeto de vinculação.
    Ela é definida nesse nível porque será usada em vários métodos da classe MainActivity.
    A palavra-chave lateinit é nova. Ela é uma promessa de que seu código inicializará a variável
    antes de usá-la. Se você não fizer isso, o app falhará.
    */
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* Essa linha inicializa o objeto binding que você usará para acessar as Views no
        layout activity_main.xml. */
        binding = ActivityMainBinding.inflate(layoutInflater)

        /* Defina a visualização de conteúdo da atividade. Em vez de transmitir o ID de
        recurso do layout, R.layout.activity_main, esse código especifica a raiz da
        hierarquia de visualizações no app, binding.root */
        setContentView(binding.root)

        // definindo listener de clique especificando o botao calculate
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    private fun calculateTip() {
        // acessando texto do custo do serviço
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        // convertendo o valor do textField para double
        val cost = stringInTextField.toDoubleOrNull()

        // se valor for null, nada é retornado
        if(cost == null) {
            binding.tipResult.text = ""
            return
        }

        // acessando valores radio checkedRadioButton
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // foi utilizado o var ao invés do val, pois o valor da variável será arredondado
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            // lib math do kotlin, utilizando função ceil passando como parametro o tip para ser arredondado
            tip = kotlin.math.ceil(tip)
        }

        // formatando a gorjeta para um valor de moeda
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        this.binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    /*
    // Old way with findViewById()
    val myButton: Button = findViewById(R.id.my_button)
    myButton.text = "A button"

    // Better way with view binding
    val myButton: Button = binding.myButton
    myButton.text = "A button"

    // Best way with view binding and no extra variable
    binding.myButton.text = "A button"
    */

}