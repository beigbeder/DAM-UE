package com.example.vicecalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vicecalc.databinding.ActivityMainBinding
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.widget.Button
import kotlin.math.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    // Variables de la calculadora
    private var primerNumero: Double = 0.0
    private var operacion: String = ""
    private var nuevoNumero: Boolean = false
    private var grados: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar los datos al girar la pantalla
        if (savedInstanceState != null) {
            primerNumero = savedInstanceState.getDouble("primerNumero")
            operacion = savedInstanceState.getString("operacion", "")
            nuevoNumero = savedInstanceState.getBoolean("nuevoNumero")
            grados = savedInstanceState.getBoolean("grados", true)

            binding.textResultado.text =
                savedInstanceState.getString("resultado", "0")

            binding.textOperacion.text =
                savedInstanceState.getString("textoOperacion", "")
        }

        actualizarModoAngular()

        // Degradado del título
        binding.textTitulo.post {
            binding.textTitulo.paint.shader = LinearGradient(
                0f,
                0f,
                binding.textTitulo.width.toFloat(),
                0f,
                getColor(R.color.rosa_neon),
                getColor(R.color.naranja_atardecer),
                Shader.TileMode.CLAMP
            )
            binding.textTitulo.invalidate()
        }
    }

    override fun onStart() {
        super.onStart()

        // Botones numéricos
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)

        // Botones de funciones
        binding.btnAC.setOnClickListener(this)
        binding.btnBorrar.setOnClickListener(this)
        binding.btnDecimal.setOnClickListener(this)
        binding.btnSigno.setOnClickListener(this)
        binding.btnPorcentaje.setOnClickListener(this)

        // Operaciones básicas
        binding.btnSumar.setOnClickListener(this)
        binding.btnRestar.setOnClickListener(this)
        binding.btnMultiplicar.setOnClickListener(this)
        binding.btnDividir.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)

        // Botones científicos
        binding.btnGrados?.setOnClickListener(this)
        binding.btnSin?.setOnClickListener(this)
        binding.btnCos?.setOnClickListener(this)
        binding.btnTan?.setOnClickListener(this)

        binding.btnPi?.setOnClickListener(this)
        binding.btnAsin?.setOnClickListener(this)
        binding.btnAcos?.setOnClickListener(this)
        binding.btnAtan?.setOnClickListener(this)

        binding.btnE?.setOnClickListener(this)
        binding.btnCuadrado?.setOnClickListener(this)
        binding.btnCubo?.setOnClickListener(this)
        binding.btnPotencia?.setOnClickListener(this)

        binding.btnInverso?.setOnClickListener(this)
        binding.btnRaiz?.setOnClickListener(this)
        binding.btnRaizCubica?.setOnClickListener(this)
        binding.btnLog?.setOnClickListener(this)

        binding.btnFactorial?.setOnClickListener(this)
        binding.btnAbsoluto?.setOnClickListener(this)
        binding.btnExponencial?.setOnClickListener(this)
        binding.btnLn?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v == null) {
            return
        }

        when (v.id) {

            // BOTONES NUMÉRICOS

            binding.btn0.id,
            binding.btn1.id,
            binding.btn2.id,
            binding.btn3.id,
            binding.btn4.id,
            binding.btn5.id,
            binding.btn6.id,
            binding.btn7.id,
            binding.btn8.id,
            binding.btn9.id -> {

                val numero = (v as Button).text.toString()
                introducirNumero(numero)
            }

            // FUNCIONES BÁSICAS

            binding.btnAC.id -> {
                limpiarCalculadora()
            }

            binding.btnBorrar.id -> {
                borrarNumero()
            }

            binding.btnDecimal.id -> {
                introducirDecimal()
            }

            binding.btnSigno.id -> {
                cambiarSigno()
            }

            binding.btnPorcentaje.id -> {
                val numero = obtenerNumero()

                if (numero != null) {
                    mostrarResultado(numero / 100)
                }
            }

            // OPERACIONES BÁSICAS

            binding.btnSumar.id -> {
                seleccionarOperacion("+")
            }

            binding.btnRestar.id -> {
                seleccionarOperacion("−")
            }

            binding.btnMultiplicar.id -> {
                seleccionarOperacion("×")
            }

            binding.btnDividir.id -> {
                seleccionarOperacion("÷")
            }

            binding.btnIgual.id -> {
                calcularResultado()
            }

            // MODO GRADOS Y RADIANES

            binding.btnGrados?.id -> {
                grados = !grados
                actualizarModoAngular()
            }

            // CONSTANTES MATEMÁTICAS

            binding.btnPi?.id -> {
                mostrarResultado(PI)
                nuevoNumero = true
            }

            binding.btnE?.id -> {
                mostrarResultado(E)
                nuevoNumero = true
            }

            // POTENCIA DE DOS NÚMEROS

            binding.btnPotencia?.id -> {
                seleccionarOperacion("^")
            }

            // FUNCIONES CIENTÍFICAS

            binding.btnSin?.id,
            binding.btnCos?.id,
            binding.btnTan?.id,
            binding.btnAsin?.id,
            binding.btnAcos?.id,
            binding.btnAtan?.id,
            binding.btnCuadrado?.id,
            binding.btnCubo?.id,
            binding.btnRaiz?.id,
            binding.btnRaizCubica?.id,
            binding.btnInverso?.id,
            binding.btnLog?.id,
            binding.btnLn?.id,
            binding.btnFactorial?.id,
            binding.btnAbsoluto?.id,
            binding.btnExponencial?.id -> {

                calcularCientifica(v.id)
            }
        }
    }

    // INTRODUCCIÓN DE NÚMEROS

    private fun introducirNumero(numero: String) {

        if (nuevoNumero || binding.textResultado.text.toString() == "0") {

            if (operacion.isEmpty()) {
                binding.textOperacion.text = ""
            }

            binding.textResultado.text = numero
            nuevoNumero = false

        } else {
            binding.textResultado.append(numero)
        }
    }

    private fun introducirDecimal() {

        if (nuevoNumero) {

            if (operacion.isEmpty()) {
                binding.textOperacion.text = ""
            }

            binding.textResultado.text = "0."
            nuevoNumero = false

        } else if (!binding.textResultado.text.contains(".")) {
            binding.textResultado.append(".")
        }
    }

    private fun borrarNumero() {

        if (obtenerNumero() == null) {
            limpiarCalculadora()
            return
        }

        if (nuevoNumero) {
            return
        }

        val texto = binding.textResultado.text.toString()
        val nuevoTexto = texto.dropLast(1)

        if (nuevoTexto.isEmpty() || nuevoTexto == "-") {
            binding.textResultado.text = "0"
        } else if (nuevoTexto.toDoubleOrNull() == null) {
            binding.textResultado.text = "0"
        } else {
            binding.textResultado.text = nuevoTexto
        }
    }

    private fun cambiarSigno() {

        if (obtenerNumero() == null) {
            return
        }

        val texto = binding.textResultado.text.toString()

        if (texto.startsWith("-")) {
            binding.textResultado.text = texto.removePrefix("-")
        } else if (texto != "0") {
            binding.textResultado.text = "-$texto"
        }
    }

    private fun limpiarCalculadora() {

        primerNumero = 0.0
        operacion = ""
        nuevoNumero = false

        binding.textResultado.text = "0"
        binding.textOperacion.text = ""
    }

    // OPERACIONES BÁSICAS

    private fun seleccionarOperacion(simbolo: String) {

        // Resolver una operación pendiente
        if (operacion.isNotEmpty() && !nuevoNumero) {
            calcularResultado()
        }

        val numero = obtenerNumero() ?: return

        primerNumero = numero
        operacion = simbolo
        nuevoNumero = true

        binding.textOperacion.text =
            "${formatearNumero(primerNumero)} $operacion"
    }

    private fun calcularResultado() {

        if (operacion.isEmpty()) {
            return
        }

        val segundoNumero = obtenerNumero() ?: return

        val resultado = when (operacion) {

            "+" -> primerNumero + segundoNumero

            "−" -> primerNumero - segundoNumero

            "×" -> primerNumero * segundoNumero

            "÷" -> primerNumero / segundoNumero

            "^" -> primerNumero.pow(segundoNumero)

            else -> return
        }

        binding.textOperacion.text =
            "${formatearNumero(primerNumero)} $operacion " +
                    "${formatearNumero(segundoNumero)} ="

        mostrarResultado(resultado)

        operacion = ""
        nuevoNumero = true
    }

    // OPERACIONES CIENTÍFICAS

    private fun calcularCientifica(id: Int) {

        val numero = obtenerNumero() ?: return

        // Convertir grados a radianes si es necesario
        val angulo = if (grados) {
            Math.toRadians(numero)
        } else {
            numero
        }

        var nombreOperacion = ""

        val resultado = when (id) {

            binding.btnSin?.id -> {
                nombreOperacion = "sin"
                sin(angulo)
            }

            binding.btnCos?.id -> {
                nombreOperacion = "cos"
                cos(angulo)
            }

            binding.btnTan?.id -> {
                nombreOperacion = "tan"

                if (abs(cos(angulo)) < 0.000000000001) {
                    Double.NaN
                } else {
                    tan(angulo)
                }
            }

            binding.btnAsin?.id -> {
                nombreOperacion = "asin"

                if (grados) {
                    Math.toDegrees(asin(numero))
                } else {
                    asin(numero)
                }
            }

            binding.btnAcos?.id -> {
                nombreOperacion = "acos"

                if (grados) {
                    Math.toDegrees(acos(numero))
                } else {
                    acos(numero)
                }
            }

            binding.btnAtan?.id -> {
                nombreOperacion = "atan"

                if (grados) {
                    Math.toDegrees(atan(numero))
                } else {
                    atan(numero)
                }
            }

            binding.btnCuadrado?.id -> {
                nombreOperacion = "x²"
                numero.pow(2)
            }

            binding.btnCubo?.id -> {
                nombreOperacion = "x³"
                numero.pow(3)
            }

            binding.btnRaiz?.id -> {
                nombreOperacion = "√"
                sqrt(numero)
            }

            binding.btnRaizCubica?.id -> {
                nombreOperacion = "∛"
                Math.cbrt(numero)
            }

            binding.btnInverso?.id -> {
                nombreOperacion = "1/x"
                1 / numero
            }

            binding.btnLog?.id -> {
                nombreOperacion = "log"
                log10(numero)
            }

            binding.btnLn?.id -> {
                nombreOperacion = "ln"
                ln(numero)
            }

            binding.btnFactorial?.id -> {
                nombreOperacion = "!"
                calcularFactorial(numero)
            }

            binding.btnAbsoluto?.id -> {
                nombreOperacion = "abs"
                abs(numero)
            }

            binding.btnExponencial?.id -> {
                nombreOperacion = "exp"
                exp(numero)
            }

            else -> return
        }

        if (operacion.isEmpty()) {
            binding.textOperacion.text =
                "$nombreOperacion(${formatearNumero(numero)})"
        }

        mostrarResultado(resultado)

        if (resultado.isFinite()) {
            nuevoNumero = operacion.isEmpty()
        }
    }

    // FUNCIONES AUXILIARES

    private fun obtenerNumero(): Double? {
        return binding.textResultado.text.toString().toDoubleOrNull()
    }

    private fun mostrarResultado(resultado: Double) {

        if (resultado.isNaN() || resultado.isInfinite()) {

            binding.textResultado.text = getString(R.string.error_matematico)

            primerNumero = 0.0
            operacion = ""
            nuevoNumero = true

        } else {
            binding.textResultado.text = formatearNumero(resultado)
        }
    }

    private fun formatearNumero(numero: Double): String {

        if (numero == 0.0) {
            return "0"
        }

        // Evitar mostrar decimales innecesarios
        if (numero % 1 == 0.0 &&
            numero >= Long.MIN_VALUE.toDouble() &&
            numero < Long.MAX_VALUE.toDouble()
        ) {

            return numero.toLong().toString()
        }

        return numero.toString()
    }

    private fun calcularFactorial(numero: Double): Double {

        // El factorial solo admite enteros positivos y cero
        if (numero < 0 || numero % 1 != 0.0 || numero > 170) {
            return Double.NaN
        }

        var resultado = 1.0

        for (i in 1..numero.toInt()) {
            resultado *= i
        }

        return resultado
    }

    private fun actualizarModoAngular() {

        if (grados) {
            binding.btnGrados?.text = getString(R.string.btn_grados)
        } else {
            binding.btnGrados?.text = getString(R.string.btn_radianes)
        }
    }

    // CONSERVAR LOS DATOS AL GIRAR LA PANTALLA

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)

        outState.putDouble("primerNumero", primerNumero)
        outState.putString("operacion", operacion)
        outState.putBoolean("nuevoNumero", nuevoNumero)
        outState.putBoolean("grados", grados)

        outState.putString(
            "resultado",
            binding.textResultado.text.toString()
        )

        outState.putString(
            "textoOperacion",
            binding.textOperacion.text.toString()
        )
    }
}